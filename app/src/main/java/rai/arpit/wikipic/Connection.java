package rai.arpit.wikipic;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;


/**
 * Created by Rai_Sahab on 6/5/2016.
 */
public class Connection {

        /**
         * Endpoints for WikiPic API to be consumed by the Connection
         */

        //Base URL of server
        public static final String BASE_URL = "http://en.wikipedia.org/w/api.php";

        //Search Query
        public static final String QUERY = "?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=300&pilimit=50"
                + "&generator=prefixsearch&gpssearch=";


        /**
         * Check if the given activity has internet access or not
         */
        public static boolean canConnect(Activity activity){
            ConnectivityManager connMgr = (ConnectivityManager)
                    activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                Toast.makeText(activity,"NO INTERNET CONNECTION FOUND, SORRY",Toast.LENGTH_LONG);
                return false;
            }
        }

        /*
         * Get String data from HttpEntity
         */

        protected String getTextFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n>0) {
                byte[] b = new byte[4096];
                n =  in.read(b);
                if (n>0) out.append(new String(b, 0, n));
            }
            return out.toString();
        }

        /**
         * Method for performing GET requests from the Wiki Server
         */
        public String getRequest(String url){
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(url);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getTextFromEntity(entity);
            } catch (Exception e) {
                Log.e("Connection", e.getLocalizedMessage());
                return "failure";
            }


            return text;
        }
    }


