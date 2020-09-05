package in.alertmeu.a4b.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import in.alertmeu.a4b.R;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Constant;
import in.alertmeu.a4b.utils.GMailSender;

public class OTPForRegisterActivity extends AppCompatActivity {
    EditText edtCode;
    Button btnNext, reSend;
    String id;
    TextView txtId;
    String code = "", mobile = "", emailId = "";
    TextView txtSMS, txtMAIL;
    private static final String username = "email-verification@alertmeu.com";
    private static String password = "ZSAM@2020";
    GMailSender sender;
    TimerTask task;
    TextView timer;
    long time = 60;
    Timer t;
    Resources res;
    private static final String FILE_NAME = "file_lang";
    private static final String KEY_LANG = "key_lang";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        res = getResources();
        setContentView(R.layout.activity_otpfor_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sender = new GMailSender(username, password);
        edtCode = (EditText) findViewById(R.id.edtCode);
        btnNext = (Button) findViewById(R.id.btnNext);
        reSend = (Button) findViewById(R.id.reSend);
        txtId = (TextView) findViewById(R.id.txtId);
        txtSMS = (TextView) findViewById(R.id.txtSMS);
        txtMAIL = (TextView) findViewById(R.id.txtMAIL);
        timer = (TextView) findViewById(R.id.timer);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        emailId = intent.getStringExtra("emailId");
        Random random = new Random();
        id = String.format("%06d", random.nextInt(1000000));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    code = edtCode.getText().toString().trim();
                    if (code.equals(id)) {
                        Intent intent = new Intent(OTPForRegisterActivity.this, CreatePassActivity.class);
                        intent.putExtra("mobile", mobile);
                        intent.putExtra("emailId", emailId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), res.getString(R.string.jcodemis), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }
            }
        });

        reSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSend.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                id = "";
                Random random = new Random();
                id = String.format("%06d", random.nextInt(1000000));
                new MyAsyncClass().execute();
            }
        });
        if (!emailId.equals("")) {
            // txtId.setText(id);

            txtSMS.setVisibility(View.GONE);
            txtMAIL.setVisibility(View.VISIBLE);
            new MyAsyncClass().execute();

        } else {
            txtId.setText(id);
            txtMAIL.setVisibility(View.GONE);
            txtSMS.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);

        }
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(OTPForRegisterActivity.this);
            pDialog.setMessage(res.getString(R.string.jpw));
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Toast.makeText(getActivity(), "mail id is"+preferences.getString("sendingmailid", ""), Toast.LENGTH_SHORT).show();
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("Please verify your email address for AlertMeU", "To finish verifying your email with AlertMeU, please enter the following security code:\n\n" + id + "\n\nWe hope to see you again soon.\n" + Html.fromHtml("<b>AlertMeU.com Technical Support</b>") + "\nTechnical-Support@AlertMeU.com", username, emailId);


            } catch (Exception ex) {
                Log.d("Error", ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(OTPForRegisterActivity.this, res.getString(R.string.jes), Toast.LENGTH_LONG).show();
            btnNext.setVisibility(View.VISIBLE);
            // timer.setVisibility(View.VISIBLE);
              time =60;
            startTimer();
        }


    }

    public void startTimer() {
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView tv1 = (TextView) findViewById(R.id.timer);
                        // long Minutes = time / (60 * 1000) % 60;
                        //  long Seconds = time / 1000 % 60;
                        //   tv1.setText(String.format("%02d", Minutes) + " " + String.format("%02d", Seconds));
                       // tv1.setText(time + "");
                        if (time > 0)
                            time -= 1;
                        else {
                          //  timer.setText("Welcome");
                            // sendCodeButton.setVisibility(View.VISIBLE);
                            btnNext.setVisibility(View.GONE);
                            timer.setVisibility(View.VISIBLE);
                            reSend.setVisibility(View.VISIBLE);
                            // finish();
                            t.cancel();
                            t.purge();
                        }
                    }
                });
            }
        };
        t.scheduleAtFixedRate(task, 0, 1000);


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
