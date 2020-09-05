package in.alertmeu.a4b.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import in.alertmeu.a4b.FirebaseNotification.SharedPrefManager;
import in.alertmeu.a4b.R;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Config;
import in.alertmeu.a4b.utils.Constant;
import in.alertmeu.a4b.utils.WebClient;

public class PostLoginActivity extends AppCompatActivity {
    TextView username, notusername, forGotPass;
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    EditText u_pass;
    Button login;
    String userPassword = "", loginResponse = "", msg = "", userId = "", userMobile = "";
    boolean status;
    ProgressDialog mProgressDialog;
    JSONObject jsonObj, jsonObject;
    Resources res;
    private static final String FILE_NAME = "file_lang";
    private static final String KEY_LANG = "key_lang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        res = getResources();
        setContentView(R.layout.activity_post_login);
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        username = (TextView) findViewById(R.id.username);
        notusername = (TextView) findViewById(R.id.notusername);
        forGotPass = (TextView) findViewById(R.id.forGotPass);
        u_pass = (EditText) findViewById(R.id.u_pass);
        login = (Button) findViewById(R.id.login);
        username.setText(res.getString(R.string.jwb) +" "+ preferences.getString("user_name", "") + "!");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {


                    userPassword = u_pass.getText().toString().trim();
                    if (validate(userPassword)) {
                        if (preferences.getString("flag", "").equals("email")) {
                            userId = preferences.getString("userEmail", "");
                            new userEmailLogin().execute();
                        }
                        if (preferences.getString("flag", "").equals("mobile")) {
                            userMobile = preferences.getString("userMobile", "");
                            new userMobileLogin().execute();
                        }
                    }
                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }

            }
        });
        notusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PostLoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        forGotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    userMobile = preferences.getString("userMobile", "");
                    Intent intent = new Intent(PostLoginActivity.this, OTPForChangePasswordActivity.class);
                    intent.putExtra("mobile", userMobile);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean validate(String userPassword) {
        boolean isValidate = false;
        if (userPassword.trim().equals("")) {
            Toast.makeText(getApplicationContext(), res.getString(R.string.jpep), Toast.LENGTH_LONG).show();
            isValidate = false;

        } else {
            isValidate = true;
        }
        return isValidate;
    }

    private class userEmailLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PostLoginActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle(res.getString(R.string.jpw));
            // Set progressdialog message
            mProgressDialog.setMessage(res.getString(R.string.jlogin));
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            jsonObj = new JSONObject() {
                {
                    try {


                        put("email_users", userId);
                        put("password_users", userPassword);
                        put("fcm_id", token);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            loginResponse = serviceAccess.SendHttpPost(Config.URL_MAILBUSINESSLOGIN, jsonObj);
            Log.i("resp", "loginResponse" + loginResponse);


            if (loginResponse.compareTo("") != 0) {
                if (isJSONValid(loginResponse)) {


                    try {

                        jsonObject = new JSONObject(loginResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                        if (status) {
                            if (!jsonObject.isNull("user_id")) {
                                JSONArray ujsonArray = jsonObject.getJSONArray("user_id");
                                for (int i = 0; i < ujsonArray.length(); i++) {
                                    JSONObject UJsonObject = ujsonArray.getJSONObject(i);
                                    prefEditor.putString("business_user_id", UJsonObject.getString("id"));
                                    prefEditor.putString("business_referral_code", UJsonObject.getString("referral_code"));
                                    prefEditor.putString("user_name", UJsonObject.getString("business_name"));
                                    prefEditor.putString("user_mobile", UJsonObject.getString("mobile_no"));
                                    prefEditor.putString("user_mail", UJsonObject.getString("email_id"));
                                    prefEditor.putString("primary_login", UJsonObject.getString("primary_login"));
                                    prefEditor.putString("create_at", UJsonObject.getString("create_at"));
                                    prefEditor.commit();
                                }
                            }

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                // Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(PostLoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                LoginActivity.fa.finish();
                finish();


                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                Toast.makeText(getApplicationContext(), res.getString(R.string.jcpmsg), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();

            }
        }
    }

    private class userMobileLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PostLoginActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle(res.getString(R.string.jpw));
            // Set progressdialog message
            mProgressDialog.setMessage(res.getString(R.string.jlogin));
            //mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            final String token = SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
            jsonObj = new JSONObject() {
                {
                    try {


                        put("userMobile", userMobile);
                        put("password_users", userPassword);
                        put("fcm_id", token);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            WebClient serviceAccess = new WebClient();
            Log.i("json", "json" + jsonObj);
            loginResponse = serviceAccess.SendHttpPost(Config.URL_MOBILEBUSINESSLOGIN, jsonObj);
            Log.i("resp", "loginResponse" + loginResponse);


            if (loginResponse.compareTo("") != 0) {
                if (isJSONValid(loginResponse)) {


                    try {

                        jsonObject = new JSONObject(loginResponse);
                        status = jsonObject.getBoolean("status");
                        msg = jsonObject.getString("message");
                        if (status) {
                            if (!jsonObject.isNull("user_id")) {
                                JSONArray ujsonArray = jsonObject.getJSONArray("user_id");
                                for (int i = 0; i < ujsonArray.length(); i++) {
                                    JSONObject UJsonObject = ujsonArray.getJSONObject(i);
                                    prefEditor.putString("business_user_id", UJsonObject.getString("id"));
                                    prefEditor.putString("business_referral_code", UJsonObject.getString("referral_code"));
                                    prefEditor.putString("user_name", UJsonObject.getString("business_name"));
                                    prefEditor.putString("user_mobile", UJsonObject.getString("mobile_no"));
                                    prefEditor.putString("user_mail", UJsonObject.getString("email_id"));
                                    prefEditor.putString("primary_login", UJsonObject.getString("primary_login"));
                                    prefEditor.putString("create_at", UJsonObject.getString("create_at"));
                                    prefEditor.commit();
                                }
                            }

                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                } else {
                    // Toast.makeText(getApplicationContext(), "Please check your webservice", Toast.LENGTH_LONG).show();
                }
            } else {

                // Toast.makeText(getApplicationContext(), "Please check your network connection.", Toast.LENGTH_LONG).show();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (status) {
                // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(PostLoginActivity.this, HomePageActivity.class);
                startActivity(intent);
                LoginActivity.fa.finish();
                finish();


                // Close the progressdialog
                mProgressDialog.dismiss();
            } else {
                // Close the progressdialog
                Toast.makeText(getApplicationContext(),  res.getString(R.string.jcpmsg), Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();

            }
        }
    }

    protected boolean isJSONValid(String registrationResponse) {
        // TODO Auto-generated method stub
        try {
            new JSONObject(registrationResponse);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(registrationResponse);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
    private void loadLanguage() {
        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private String getLangCode() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, "en");
        return langCode;
    }
}
