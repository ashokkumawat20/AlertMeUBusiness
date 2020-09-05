package in.alertmeu.a4b.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.github.javiersantos.appupdater.AppUpdater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.alertmeu.a4b.Fragments.TabsFragmentActivity;
import in.alertmeu.a4b.JsonUtils.JsonHelper;
import in.alertmeu.a4b.R;
import in.alertmeu.a4b.adapter.CurrentUsersCountListAdpter;
import in.alertmeu.a4b.adapter.MainCatListAdpter;
import in.alertmeu.a4b.models.CurrentUserLocationAdvertisementDAO;
import in.alertmeu.a4b.models.MainCatModeDAO;
import in.alertmeu.a4b.utils.AppStatus;
import in.alertmeu.a4b.utils.Config;
import in.alertmeu.a4b.utils.Constant;
import in.alertmeu.a4b.utils.WebClient;

public class HomePageActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor prefEditor;
    private ImageView naviBtn;
    Button postAds;
    ImageView btnHome, btnBusinessList, btnscanbar, areaUnit, refsettings;
    RecyclerView showDemandsList;
    ProgressDialog mProgressDialog;
    JSONObject jsonLeadObj, jsonObj1, jsonSchedule;
    JSONArray jsonArray;
    boolean statusv;
    String myCurrentUsersResponse = "", checkCatByBIdResponse = "", imagePathResponse = "";
    List<CurrentUserLocationAdvertisementDAO> data;
    CurrentUsersCountListAdpter currentUsersCountListAdpter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> mainCatArrayList;
    ArrayList<String> subCatArrayList;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private String TAG = "tag";
    LinearLayout proTag, btnHomeC, huh, sphs, btnshopPrec;
    RelativeLayout footer;
    boolean status;
    Resources res;
    private static final String FILE_NAME = "file_lang";
    private static final String KEY_LANG = "key_lang";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguage();
        res = getResources();
        setContentView(R.layout.activity_home_page);
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();
        preferences = getSharedPreferences("Prefrence", Context.MODE_PRIVATE);
        prefEditor = preferences.edit();
        naviBtn = (ImageView) findViewById(R.id.naviBtn);
        postAds = (Button) findViewById(R.id.postAds);
        btnHome = (ImageView) findViewById(R.id.btnHome);
        btnBusinessList = (ImageView) findViewById(R.id.btnBusinessList);
        proTag = (LinearLayout) findViewById(R.id.proTag);
        btnHomeC = (LinearLayout) findViewById(R.id.btnHomeC);
        huh = (LinearLayout) findViewById(R.id.huh);
        sphs = (LinearLayout) findViewById(R.id.sphs);
        btnshopPrec = (LinearLayout) findViewById(R.id.btnshopPrec);
        btnscanbar = (ImageView) findViewById(R.id.btnscanbar);
        showDemandsList = (RecyclerView) findViewById(R.id.showDemandsList);
        areaUnit = (ImageView) findViewById(R.id.areaUnit);
        refsettings = (ImageView) findViewById(R.id.refsettings);
        footer = (RelativeLayout) findViewById(R.id.footer);
        //  mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        //Initializing viewPager
        btnHomeC.setBackgroundColor(Color.parseColor("#809E9E9E"));
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String dateToStr = format.format(today);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToStr1 = format1.format(today);
        String createdate = formateDateFromstring("yyyy-MM-dd hh:mm:ss", "yyyy-MM-dd", preferences.getString("create_at", ""));

        Date d1 = null;
        Date d2 = null;
        Date d3 = null;
        try {
            d1 = format1.parse(createdate);
            d2 = format1.parse(dateToStr1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d1); // Now use today date.
        c.add(Calendar.DATE, 30); // Adding 5 days
        String output = format1.format(c.getTime());
        // Toast.makeText(HomePageActivity.this, dateToStr1 + "  " + output, Toast.LENGTH_SHORT).show();
        try {
            d3 = format1.parse(output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (d2.compareTo(d3) > 0) {
            proTag.setVisibility(View.GONE);
            prefEditor.putInt("provalid", 0);
            prefEditor.commit();
            //   Toast.makeText(HomePageActivity.this, "Date 2 occurs after Date 3", Toast.LENGTH_SHORT).show();
        } else if (d2.compareTo(d3) < 0) {
            proTag.setVisibility(View.VISIBLE);
            prefEditor.putInt("provalid", 1);
            prefEditor.commit();
            //  Toast.makeText(HomePageActivity.this, "Date 2 occurs before Date 3", Toast.LENGTH_SHORT).show();
        } else if (d2.compareTo(d3) == 0) {
            proTag.setVisibility(View.VISIBLE);
            prefEditor.putInt("provalid", 1);
            prefEditor.commit();
            // Toast.makeText(HomePageActivity.this, "Both dates are equal", Toast.LENGTH_SHORT).show();

        }
        data = new ArrayList<>();
        currentUsersCountListAdpter = new CurrentUsersCountListAdpter(getApplicationContext(), data);
        naviBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(HomePageActivity.this, BusinessProfileSettingActivity.class);
                    startActivity(intent);
                    finish();


                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        postAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCatByBId();
            }
        });

        btnBusinessList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, TabsFragmentActivity.class);
                intent.putExtra("active", 0);
                startActivity(intent);
            }
        });
        btnscanbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndRequestPermissions()) {
                    if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                        Intent intent = new Intent(HomePageActivity.this, ScanActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            new getCurrentUserCountList().execute();
            getImagePath();

        } else {

            Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
        }

        btnshopPrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    Intent intent = new Intent(HomePageActivity.this, BusinessExpandableListViewActivity.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
        areaUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePageActivity.this);
                alertDialog.setMessage(res.getString(R.string.jdfds));
                final EditText input = new EditText(HomePageActivity.this);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                input.setText("" + preferences.getInt("units_for_area", 0));
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                alertDialog.setView(input);
                // alertDialog.setIcon(R.drawable.msg_img);

                alertDialog.setPositiveButton(res.getString(R.string.jconfirm),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String entity = input.getText().toString();
                                prefEditor.putInt("units_for_area", Integer.parseInt(entity));
                                prefEditor.commit();
                                if (data.size() > 0) {
                                    data.clear(); // this list which you hava passed in Adapter for your listview
                                    currentUsersCountListAdpter.notifyDataSetChanged(); // notify to listview for refresh
                                }
                                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                                    new getCurrentUserCountList().execute();
                                } else {

                                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                alertDialog.setNegativeButton(res.getString(R.string.helpCan),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alertDialog.show();
            }
        });

        update();
       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    new getCurrentUserCountList().execute();
                } else {

                    Toast.makeText(getApplicationContext(), Constant.INTERNET_MSG, Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });*/
        refsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jrepre), Toast.LENGTH_LONG).show();
                    new getCurrentUserCountList().execute();
                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
            getImagePath();

        } else {

            Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
        }
    }

    private void update() {
        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 50000);
                if (AppStatus.getInstance(getApplicationContext()).isOnline()) {
                    new getCurrentUserCountList().execute();
                } else {

                    Toast.makeText(getApplicationContext(), res.getString(R.string.jpcnc), Toast.LENGTH_SHORT).show();
                }

            }
        };
        handler.postDelayed(r, 50000);

    }


    private class getCurrentUserCountList extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            //  mProgressDialog = new ProgressDialog(HomePageActivity.this);
            // Set progressdialog title
            //    mProgressDialog.setTitle("Please Wait...");
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
                        put("business_user_id", preferences.getString("business_user_id", ""));
                        put("distance", preferences.getInt("units_for_area", 0));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("json exception", "json exception" + e);
                    }
                }
            };
            WebClient serviceAccess = new WebClient();

            Log.i("json", "json" + jsonLeadObj);
            myCurrentUsersResponse = serviceAccess.SendHttpPost(Config.URL_GETALLUSERCOUNTFORBUSINESSLOCATIONDATA, jsonLeadObj);
            Log.i("resp", "myPlaceListResponse" + myCurrentUsersResponse);
            if (myCurrentUsersResponse.compareTo("") != 0) {
                if (isJSONValid(myCurrentUsersResponse)) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                data.clear();
                                JsonHelper jsonHelper = new JsonHelper();
                                data = jsonHelper.parseCurrentUsersList(myCurrentUsersResponse);
                                jsonArray = new JSONArray(myCurrentUsersResponse);

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
                            //  Toast.makeText(getApplication(), "Please check your webservice", Toast.LENGTH_LONG).show();
                        }
                    });

                    return null;
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getApplication(), "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                });

                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            if (data.size() > 0) {
                int c = 0;

                currentUsersCountListAdpter = new CurrentUsersCountListAdpter(getApplicationContext(), data);
                showDemandsList.setAdapter(currentUsersCountListAdpter);
                showDemandsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                currentUsersCountListAdpter.notifyDataSetChanged();
                List<CurrentUserLocationAdvertisementDAO> stList = ((CurrentUsersCountListAdpter) currentUsersCountListAdpter).getSservicelist();
                for (int i = 0; i < stList.size(); i++) {
                    CurrentUserLocationAdvertisementDAO serviceListDAO = stList.get(i);
                    c = (c + Integer.parseInt(serviceListDAO.getUser_count()));
                }
                // Toast.makeText(getApplicationContext(), "Total uers" + c, Toast.LENGTH_SHORT).show();
                // setBadge(HomePageActivity.this, c);
                //  addNotification(c);
                // mProgressDialog.dismiss();
                //  footer.setVisibility(View.VISIBLE);
            } else {
                currentUsersCountListAdpter = new CurrentUsersCountListAdpter(getApplicationContext(), data);
                showDemandsList.setAdapter(currentUsersCountListAdpter);
                showDemandsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                currentUsersCountListAdpter.notifyDataSetChanged();
                //  footer.setVisibility(View.GONE);
                // mProgressDialog.dismiss();

            }
        }
    }


    private void addNotification(int c) {

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID", "YOUR_CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle("Near by users") // title for notification
                .setContentText("Total count is " + c)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());


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

    //
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);

    }

    public static void setBadge(Context context, int count) {
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        context.sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public void checkCatByBId() {


        jsonObj1 = new JSONObject() {
            {
                try {
                    put("business_user_id", preferences.getString("business_user_id", ""));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread objectThread = new Thread(new Runnable() {
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                checkCatByBIdResponse = serviceAccess.SendHttpPost(Config.URL_GETATLEATONECATBUSINESS, jsonObj1);
                Log.i("checkCatByBIdResponse", checkCatByBIdResponse);
                final Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() { // This thread runs in the UI
                            @Override
                            public void run() {
                                if (checkCatByBIdResponse.compareTo("") == 0) {

                                } else {

                                    try {
                                        JSONObject jObject = new JSONObject(checkCatByBIdResponse);
                                        statusv = jObject.getBoolean("status");

                                        if (statusv) {

                                            if (!((CurrentUsersCountListAdpter) currentUsersCountListAdpter).getSservicelist().isEmpty()) {
                                                List<CurrentUserLocationAdvertisementDAO> stList = ((CurrentUsersCountListAdpter) currentUsersCountListAdpter).getSservicelist();
                                                String data1 = "";
                                                String data2 = "";
                                                mainCatArrayList = new ArrayList<>();
                                                subCatArrayList = new ArrayList<>();
                                                for (int i = 0; i < stList.size(); i++) {
                                                    CurrentUserLocationAdvertisementDAO serviceListDAO = stList.get(i);
                                                    if (serviceListDAO.isSelected() == true) {
                                                        if (preferences.getString("ulang", "").equals("en")) {
                                                            data1 = serviceListDAO.getCategory_name().toString();
                                                            data2 = serviceListDAO.getSubcategory_name().toString();
                                                        } else if (preferences.getString("ulang", "").equals("hi")) {
                                                            data1 = serviceListDAO.getCategory_name_hindi().toString();
                                                            data2 = serviceListDAO.getSubcategory_name_hindi().toString();
                                                        }

                                                        mainCatArrayList.add(data1);
                                                        subCatArrayList.add(data2);

                                                    } else {
                                                        System.out.println("not selected");
                                                    }
                                                }
                                                Intent intent = new Intent(HomePageActivity.this, TabsFragmentActivity.class);
                                                Bundle args = new Bundle();
                                                args.putSerializable("mainCat", (Serializable) mainCatArrayList);
                                                args.putSerializable("subCat", (Serializable) subCatArrayList);
                                                intent.putExtra("BUNDLE", args);
                                                intent.putExtra("active", 2);
                                                prefEditor.putString("editflag", "1");
                                                prefEditor.commit();
                                                startActivity(intent);
                                            } else {
                                                Intent intent = new Intent(HomePageActivity.this, TabsFragmentActivity.class);
                                                intent.putExtra("active", 2);
                                                startActivity(intent);
                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), res.getString(R.string.jpsaoc), Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                };

                new Thread(runnable).start();
            }
        });
        objectThread.start();
    }

    private boolean checkAndRequestPermissions() {


        int permissionLocation = ContextCompat.checkSelfPermission(HomePageActivity.this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(HomePageActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions


                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);


                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //finish();
                        Intent intent = new Intent(HomePageActivity.this, ScanActivity.class);
                        startActivity(intent);
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            showDialogOK("Service Permissions are required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?");
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void explain(String msg) {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        //  permissionsclass.requestPermission(type,code);
                        startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:in.alertmeu.a4b")));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    public void getImagePath() {

        jsonSchedule = new JSONObject() {
            {
                try {
                    put("business_user_id", preferences.getString("business_user_id", ""));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("json exception", "json exception" + e);
                }
            }
        };


        Thread objectThread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                WebClient serviceAccess = new WebClient();
                Log.i("json", "json" + jsonSchedule);
                imagePathResponse = serviceAccess.SendHttpPost(Config.URL_CUCBID, jsonSchedule);
                Log.i("resp", "imagePathResponse" + imagePathResponse);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(imagePathResponse);
                            status = jsonObject.getBoolean("status");

                            if (status) {
                                sphs.setVisibility(View.GONE);
                                huh.setVisibility(View.VISIBLE);
                                footer.setVisibility(View.VISIBLE);


                            } else {
                                footer.setVisibility(View.GONE);
                                huh.setVisibility(View.GONE);
                                sphs.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });


            }
        });

        objectThread.start();

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            //LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }

    private String getLangCode() {
        SharedPreferences preferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String langCode = preferences.getString(KEY_LANG, "");
        return langCode;
    }

    private void loadLanguage() {
        Locale locale = new Locale(getLangCode());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}