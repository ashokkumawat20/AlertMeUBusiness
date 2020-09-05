package in.alertmeu.a4b.utils;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;


@SuppressWarnings({"deprecation", "deprecation"})
public class WebClient {
    Context context;

    String TAG = "ServiceAccess";
    String response = "";
    String baseURL = "";

    @SuppressWarnings({"deprecation", "resource"})
    public String SendHttpPost(String URL, JSONObject jsonObjSend) {

        try {
            HttpClient client = getNewHttpClient();
            //HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(URL);
            // HttpGet post = new HttpGet(URL);
            post.setHeader("Content-type", "application/json; charset=UTF-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(jsonObjSend.toString(), "UTF-8"));
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10 * 1000);
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10 * 1000);
            HttpResponse response = client.execute(post);
            Log.i(TAG, "resoponse" + response);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);

        } catch (Exception e) {
            // TODO: handle exception
            Log.i(TAG, "exception" + e);
        }
        Log.i(TAG, "response" + response);
        return response;
    }

    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static String convertArrayListToStringWithComma(ArrayList<String> arrayList){

        //The string builder used to construct the string
        StringBuilder commaSepValueBuilder = new StringBuilder();

        //Looping through the list
        for ( int i = 0; i< arrayList.size(); i++){
            //append the value into the builder
            commaSepValueBuilder.append(arrayList.get(i));

            //if the value is not the last element of the list
            //then append the hash(H) as well
            if ( i != arrayList.size()-1){
                commaSepValueBuilder.append(",");
            }
        }

        return String.valueOf(commaSepValueBuilder);
    }
    public static ArrayList<String> convertStringwithCommaToArrayList(String stringValue){
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(stringValue.split(",")));
        return myList;
    }
}


