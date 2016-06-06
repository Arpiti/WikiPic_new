package rai.arpit.wikipic;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Rai_Sahab on 6/5/2016.
 */

public class FetchImagesTask extends AsyncTask<String, Integer, Boolean>  {
    private Connection connection;
    private String remoteData;
    private MainActivity activity;
    private ArrayList<Image_POJO> image;
    private static final String TAG = "FetchTASK";
    private CustomImageViewAdapter customImageViewAdapter;

    public FetchImagesTask(MainActivity activity) {
        connection = new Connection();
        remoteData = "";
        this.activity=activity;
    }

    @Override
    protected Boolean doInBackground(String... params){
        if(params[0]!=null) {
            remoteData = params[0];

            return update(activity);
        }
        return false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        //This is the grid view for images to be displayed
        GridView gridView=(GridView)activity.findViewById(R.id.gridView);
        customImageViewAdapter=new CustomImageViewAdapter(activity,image);
        gridView.setAdapter(customImageViewAdapter);


 /*
 //TO EXPAND THE IMAGE SELECTED

       //Whenever a image is clicked, it expands
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenImage openImage = OpenImage.newInstance((Image_POJO) customImageViewAdapter.getItem(position));
                activity.getFragmentManager().
                        beginTransaction().
                        replace(R.id.main_container, openImage).
                        addToBackStack(null).commit();
            }
        });

   */
//        Log.i(TAG, "Done : "+remoteData);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public boolean update(Activity activity) throws IllegalArgumentException{

        if(!Connection.canConnect(activity)){
            return false;
        }

        //Find and return JSON String with data in it
        try {
            remoteData = connection.getRequest(connection.BASE_URL + connection.QUERY + remoteData);
            Log.e(TAG, remoteData);
        }
        catch (IllegalArgumentException iae)
        {
            Toast.makeText(activity,"INVALID CHARACTER ENTERED, PLEASE CHECK AND RETYPE",Toast.LENGTH_LONG);
            iae.printStackTrace();
        }

            if (remoteData.equals("failure")) {
            Log.e(TAG, "failure"+remoteData);
            return false;
        }
        image=new ArrayList<>();
        //parsing the JSON object
        try {
            JSONObject jsonObject = new JSONObject(remoteData);
            JSONObject query=jsonObject.getJSONObject("query");
            JSONObject pages=query.getJSONObject("pages");
            JSONArray iter_pages=pages.names();
            for (int i = 0; i < iter_pages.length(); i++) {
                JSONObject page_id=pages.getJSONObject(iter_pages.getString(i));

                String title="",source="NO_IMAGE";
                int width=0,height=0;

                try {
                    title=page_id.getString("title");
                    JSONObject thumbnails = page_id.getJSONObject("thumbnail");
                     source=thumbnails.getString("source");
                     width=thumbnails.getInt("width");
                     height=thumbnails.getInt("height");
//                    Log.e(TAG, "title : "+title+"src : "+source+"wid : "+width);
                }
                catch (org.json.JSONException je)
                {
                    Log.e(TAG, "No thumbnail in this page retreived");
                }
                image.add(new Image_POJO(title,source,width,height));
                Log.e(TAG, "title : "+title+" src : "+source+" wid : "+width);
            }
        }
        catch (org.json.JSONException je)
        {
            Log.e(TAG, "errored");
            je.printStackTrace();
        }
        return true;
    }
}
