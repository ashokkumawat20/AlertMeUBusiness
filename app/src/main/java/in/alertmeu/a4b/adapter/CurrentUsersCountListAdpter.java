package in.alertmeu.a4b.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import in.alertmeu.a4b.R;
import in.alertmeu.a4b.models.CurrentUserLocationAdvertisementDAO;
import in.alertmeu.a4b.models.MainCatModeDAO;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Config;
import in.alertmeu.a4b.utils.MyApp;
import in.alertmeu.a4b.utils.WebClient;


public class CurrentUsersCountListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<CurrentUserLocationAdvertisementDAO> data;
    CurrentUserLocationAdvertisementDAO current;
    String id, id1;
    int ID;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    ProgressDialog mProgressDialog;
    private JSONObject jsonLeadObj;
    JSONArray jsonArray;
    boolean status;
    String message = "";
    String msg = "";
    String deleteResponse = "", businessCatResponse = "";
    String onefc = "";
    int cf = 0, cfc = 0;
    Resources res;
    private static final String FILE_NAME = "file_lang";
    private static final String KEY_LANG = "key_lang";

    // create constructor to innitilize context and data sent from MainActivity
    public CurrentUsersCountListAdpter(Context context, List<CurrentUserLocationAdvertisementDAO> data) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        preferences = context.getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        res = context.getResources();
        loadLanguage(context);

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_current_users_counts_details, parent, false);
        MyHolder holder = new MyHolder(view);

        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final int pos = position;
        // Get current position of item in recyclerview to bind data and assign values from list
        final MyHolder myHolder = (MyHolder) holder;
        current = data.get(position);
        if (preferences.getString("ulang", "").equals("en")) {
            myHolder.notes.setText(current.getUser_count() + "+ " + res.getString(R.string.jfor) + " " + current.getCategory_name() + "/" + current.getSubcategory_name());
        } else if (preferences.getString("ulang", "").equals("hi")) {
            myHolder.notes.setText(current.getUser_count() + "+ " + res.getString(R.string.jfor) + " " + current.getCategory_name_hindi() + "/" + current.getSubcategory_name_hindi());

        }
        myHolder.notes.setTag(position);

        myHolder.chkBox.setTag(position);
        myHolder.chkBox.setChecked(data.get(position).isSelected());
        myHolder.chkBox.setTag(data.get(position));

        myHolder.chkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                CurrentUserLocationAdvertisementDAO contact = (CurrentUserLocationAdvertisementDAO) cb.getTag();


                if (cb.isChecked()) {
                    myHolder.chkBox.setChecked(true);
                    if (cf == 0) {
                        onefc = data.get(pos).getCategory_name();
                    }

                    if (onefc.equals(data.get(pos).getCategory_name())) {
                        data.get(pos).setSelected(cb.isChecked());
                        contact.setSelected(cb.isChecked());
                        cf++;
                    } else {
                        Toast.makeText(v.getContext(), res.getString(R.string.jdadsh), Toast.LENGTH_LONG).show();
                        myHolder.chkBox.setChecked(false);
                    }


                    // new submitData().execute();
                } else if (!cb.isChecked()) {
                    // Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked() + data.get(pos).getSubbc_id(), Toast.LENGTH_LONG).show();
                    myHolder.chkBox.setChecked(false);
                    data.get(pos).setSelected(cb.isChecked());
                    contact.setSelected(cb.isChecked());
                    // new deleteSale().execute();
                    cf--;

                }


            }
        });
    }


    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_date, notes, id;
        CheckBox chkBox;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            txt_date = (TextView) itemView.findViewById(R.id.txt_date);
            notes = (TextView) itemView.findViewById(R.id.comments);
            chkBox = (CheckBox) itemView.findViewById(R.id.chkBox);


        }

    }

    // method to access in activity after updating selection
    public List<CurrentUserLocationAdvertisementDAO> getSservicelist() {
        return data;
    }

    private class submitData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            //   mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //   mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //   mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("bc_id", id);
                        put("business_user_id", preferences.getString("business_user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonLeadObj);
            businessCatResponse = serviceAccess.SendHttpPost(Config.URL_SAVEMAINCAT, jsonLeadObj);
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


                    Toast.makeText(context, res.getString(R.string.jpcnc), Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(context, res.getString(R.string.jpcnc), Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                // mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                //  mProgressDialog.dismiss();

            }
        }
    }

    //
    private class deleteSale extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            // mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            //  mProgressDialog.setTitle("Please Wait...");
            // Set progressdialog message
            //mProgressDialog.setMessage("Loading...");
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("bc_id", id1);
                        put("business_user_id", preferences.getString("business_user_id", ""));


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };

            WebClient serviceAccess = new WebClient();


            Log.i("json", "json" + jsonLeadObj);
            deleteResponse = serviceAccess.SendHttpPost(Config.URL_DELETEMAINCATEGORY, jsonLeadObj);
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

                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(context, "Please check your network connection.", Toast.LENGTH_LONG).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }
            // Close the progressdialog
            // mProgressDialog.dismiss();
        }
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

}
