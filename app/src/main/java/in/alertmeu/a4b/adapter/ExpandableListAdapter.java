package in.alertmeu.a4b.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import in.alertmeu.a4b.R;
import in.alertmeu.a4b.imageUtils.ImageLoader;
import in.alertmeu.a4b.models.MainCatModeDAO;
import in.alertmeu.a4b.models.SubCatModeDAO;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Config;
import in.alertmeu.a4b.utils.Constant;
import in.alertmeu.a4b.utils.Listener;
import in.alertmeu.a4b.utils.WebClient;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<MainCatModeDAO> deptList;
    String main_id, sub_id;
    private ArrayList<SubCatModeDAO> list = new ArrayList<SubCatModeDAO>();
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    boolean status;
    String message = "";
    String msg = "";
    String deleteResponse = "", businessCatResponse = "", activeads = "";
    private static Listener mListener;
    Resources res;
    private static final String FILE_NAME = "file_lang";
    private static final String KEY_LANG = "key_lang";

    public ExpandableListAdapter(Context context, ArrayList<MainCatModeDAO> deptList) {
        this.context = context;
        this.deptList = deptList;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        loadLanguage(context);
        res = context.getResources();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<SubCatModeDAO> productList = deptList.get(groupPosition).getList();
        return productList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        final int pos = childPosition;
        final SubCatModeDAO detailInfo = (SubCatModeDAO) getChild(groupPosition, childPosition);
        final ViewHolderChild viewHolderChild;

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_items, null);
            viewHolderChild = new ViewHolderChild();
            viewHolderChild.childItem = view.findViewById(R.id.childItem);
            viewHolderChild.cbSubCategory = view.findViewById(R.id.cbSubCategory);
            viewHolderChild.subimage = view.findViewById(R.id.subimage);
            view.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) view.getTag();
        }
        viewHolderChild.subimage.setTag(childPosition);
        if (!detailInfo.getImage_path().equals("")) {
            ImageLoader imageLoader = new ImageLoader(context);
            imageLoader.DisplayImage(detailInfo.getImage_path(), viewHolderChild.subimage);
        } else {
            // viewHolderChild.subimage.setImageResource(R.drawable.default_sub_category);
            viewHolderChild.subimage.setImageDrawable(context.getResources().getDrawable(R.drawable.default_sub_category));

        }
        if (detailInfo.getChecked_status().equals("1")) {
            viewHolderChild.cbSubCategory.setChecked(true);
        } else {
            viewHolderChild.cbSubCategory.setChecked(false);
        }
        if (preferences.getString("ulang", "").equals("en")) {
            viewHolderChild.childItem.setText(detailInfo.getSubcategory_name().trim());
        } else if (preferences.getString("ulang", "").equals("hi")) {
            viewHolderChild.childItem.setText(detailInfo.getSubcategory_name_hindi().trim());
        }
        viewHolderChild.cbSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                SubCatModeDAO contact = (SubCatModeDAO) cb.getTag();

                //contact.setSelected(cb.isChecked());
                //  deptList.get(pos).setSelected(cb.isChecked());
                list = deptList.get(groupPosition).getList();
                activeads = list.get(childPosition).getActiveads();

                if (cb.isChecked()) {
                    // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + list.get(childPosition).getSubcategory_name() + "id=" + list.get(childPosition).getId() + "," + list.get(childPosition).getBc_id(), Toast.LENGTH_LONG).show();
                    // Config.VALUE.add(deptList.get(pos).getId());
                    main_id = list.get(childPosition).getBc_id();
                    sub_id = list.get(childPosition).getId();

                    viewHolderChild.cbSubCategory.setChecked(true);
                    list.get(childPosition).setChecked_status("1");
                    if (AppStatus.getInstance(context).isOnline()) {
                        new submitData().execute();
                    } else {
                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                } else if (!cb.isChecked()) {
                    // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + list.get(childPosition).getSubcategory_name() + "id=" + list.get(childPosition).getId() + "," + list.get(childPosition).getBc_id(), Toast.LENGTH_LONG).show();
                    main_id = list.get(childPosition).getBc_id();
                    sub_id = list.get(childPosition).getId();
                    viewHolderChild.cbSubCategory.setChecked(false);
                    list.get(childPosition).setChecked_status("0");
                    //  Config.VALUE.remove(deptList.get(pos).getId());
                    if (AppStatus.getInstance(context).isOnline()) {
                        new deleteSale().execute();
                    } else {
                        Toast.makeText(context, Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                }
               /* if (activeads.equals("0")) {
                } else {
                    viewHolderChild.cbSubCategory.setChecked(true);
                    Toast.makeText(v.getContext(), "Already have Active/Add Ads ", Toast.LENGTH_LONG).show();

                }*/

            }
        });
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        ArrayList<SubCatModeDAO> productList = deptList.get(groupPosition).getList();
        return productList.size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return deptList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return deptList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view,
                             ViewGroup parent) {

        MainCatModeDAO headerInfo = (MainCatModeDAO) getGroup(groupPosition);
        final ViewHolderMain viewHolderMain;
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.group_items, null);
            viewHolderMain = new ViewHolderMain();
            viewHolderMain.heading = view.findViewById(R.id.heading);
            viewHolderMain.mimage = view.findViewById(R.id.mimage);
            view.setTag(viewHolderMain);
        } else {
            viewHolderMain = (ViewHolderMain) view.getTag();
        }
        viewHolderMain.heading.setTag(groupPosition);
        viewHolderMain.mimage.setTag(groupPosition);
        if (!headerInfo.getImage_path().equals("")) {
            ImageLoader imageLoader = new ImageLoader(context);
            imageLoader.DisplayImage(headerInfo.getImage_path(), viewHolderMain.mimage);

            /* ImageloaderNew imageLoader = new ImageloaderNew(context);
            mimage.setTag(headerInfo.getImage_path());
            imageLoader.DisplayImage(headerInfo.getImage_path(), context, mimage);*/
        } else {
            viewHolderMain.mimage.setImageDrawable(context.getResources().getDrawable(R.drawable.default_category));


        }
        // TextView heading = (TextView) view.findViewById(R.id.heading);
        if (preferences.getString("ulang", "").equals("en")) {
            if (headerInfo.getChecked_status().equals("0")) {
                viewHolderMain.heading.setText(headerInfo.getCategory_name().trim());
                viewHolderMain.heading.setTextColor(Color.parseColor("#000000"));

            } else {
                viewHolderMain.heading.setText(headerInfo.getCategory_name().trim() + "(" + headerInfo.getChecked_status() + ")");
                viewHolderMain.heading.setTextColor(Color.parseColor("#23A566"));
            }
        } else if (preferences.getString("ulang", "").equals("hi")) {
            if (headerInfo.getChecked_status().equals("0")) {
                viewHolderMain.heading.setText(headerInfo.getCategory_name_hindi().trim());
                viewHolderMain.heading.setTextColor(Color.parseColor("#000000"));

            } else {
                viewHolderMain.heading.setText(headerInfo.getCategory_name_hindi().trim() + "(" + headerInfo.getChecked_status() + ")");
                viewHolderMain.heading.setTextColor(Color.parseColor("#23A566"));
            }
        }
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class ViewHolderChild {

        TextView childItem;
        ImageView subimage;
        CheckBox cbSubCategory;

    }

    private class ViewHolderMain {

        TextView heading;
        ImageView mimage;

    }

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle(res.getString(R.string.jpw));
            // Set progressdialog message
            mProgressDialog.setMessage(res.getString(R.string.jisc));
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("subbc_id", sub_id);
                        put("maincat_id", main_id);
                        put("business_user_id", preferences.getString("business_user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            businessCatResponse = serviceAccess.SendHttpPost(Config.URL_SAVESUBCAT, jsonLeadObj);
            Log.i("resp", "businessCatResponse" + businessCatResponse);


            if (businessCatResponse.compareTo("") != 0) {
                if (isJSONValid(businessCatResponse)) {


                    try {

                        JSONObject jsonObject = new JSONObject(businessCatResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {


//            Toast.makeText(context, "Please check your webservice", Toast.LENGTH_LONG).show();


                }
            } else {

                // Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            mProgressDialog.dismiss();
            if (status) {
                mListener.messageReceived(message);
            }
        }
    }

    //
    private class deleteSale extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle(res.getString(R.string.jpw));
            // Set progressdialog message
            mProgressDialog.setMessage(res.getString(R.string.jigsc));
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("subbc_id", sub_id);
                        put("business_user_id", preferences.getString("business_user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            deleteResponse = serviceAccess.SendHttpPost(Config.URL_DELETESUBCATEGORY, jsonLeadObj);
            Log.i("resp", "leadListResponse" + deleteResponse);


            if (deleteResponse.compareTo("") != 0) {
                if (isJSONValid(deleteResponse)) {

                    try {

                        JSONObject jObject = new JSONObject(deleteResponse);
                        status = jObject.getBoolean("status");
                        message = jObject.getString("message");
                        jsonArray = new JSONArray(deleteResponse);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {

                    //Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                // Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            // Close the progressdialog
            mProgressDialog.dismiss();
            if (status) {
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                mListener.messageReceived(message);
            }

        }
    }

    public static void bindListener(Listener listener) {
        mListener = listener;
    }

    private String getLangCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, context.MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, "");
        return langCode;
    }

    private void loadLanguage(Context context) {
        Locale locale = new Locale(getLangCode(context));
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    //
    protected boolean isJSONValid(String callReoprtResponse2) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(callReoprtResponse2);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(callReoprtResponse2);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
