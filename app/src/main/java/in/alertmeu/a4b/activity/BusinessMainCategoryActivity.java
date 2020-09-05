package in.alertmeu.a4b.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.alertmeu.a4b.JsonUtils.JsonHelper;
import in.alertmeu.a4b.R;
import in.alertmeu.a4b.adapter.MainCatListAdpter;
import in.alertmeu.a4b.models.MainCatModeDAO;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Config;
import in.alertmeu.a4b.utils.Constant;
import in.alertmeu.a4b.utils.WebClient;

public class BusinessMainCategoryActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    RecyclerView mainCatList;
    JSONObject jsonLeadObj;
    JSONArray jsonArray;
    String myPlaceListResponse = "";
    List<MainCatModeDAO> data;
    MainCatListAdpter mainCatListAdpter;
    LinearLayout showhide;
    ProgressDialog mProgressDialog;
    LinearLayout btnNext;
    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_main_category);
        res = getResources();
        mainCatList = (RecyclerView) findViewById(R.id.mainCatList);
        btnNext = (LinearLayout) findViewById(R.id.btnNext);
        preferences = getSharedPreferences("Prefrence", MODE_PRIVATE);
        prefEditor = preferences.edit();
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            new getMyPlaceList().execute();
        } else {

            Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(BusinessMainCategoryActivity.this, BusinessSubCategoryActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class getMyPlaceList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BusinessMainCategoryActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle(res.getString(R.string.jpw));
            // Set progressdialog message
            mProgressDialog.setMessage(res.getString(R.string.jsql));
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            jsonLeadObj = new JSONObject() {
                {
                    try {
                        put("business_user_id", preferences.getString("business_user_id", ""));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            Log.i("json", "json" + jsonLeadObj);
            myPlaceListResponse = serviceAccess.SendHttpPost(Config.URL_GETALLMAINCATEGORY, jsonLeadObj);
            Log.i("resp", "myPlaceListResponse" + myPlaceListResponse);
            if (myPlaceListResponse.compareTo("") != 0) {
                if (isJSONValid(myPlaceListResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data = new ArrayList<>();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseMyPlaceList(myPlaceListResponse);
                                jsonArray = new JSONArray(myPlaceListResponse);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplication(), res.getString(R.string.jpcnc), Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplication(), res.getString(R.string.jpcnc), Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (data.size() > 0) {

                mainCatListAdpter = new MainCatListAdpter(getApplication(), data);
                mainCatList.setAdapter(mainCatListAdpter);
                mainCatList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                mainCatListAdpter.notifyDataSetChanged();
                mProgressDialog.dismiss();
            } else {


                mProgressDialog.dismiss();

            }
        }
    }

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
