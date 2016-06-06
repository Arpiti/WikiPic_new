package rai.arpit.wikipic;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity  {

private Timer timer=new Timer();
    private final long DELAY=900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MainActivity actvty = this;
        final EditText editText = (EditText) findViewById(R.id.searchText);
        Drawable loginActivityBackground = findViewById(R.id.main_container).getBackground();
        loginActivityBackground.setAlpha(75);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(timer!=null)
            timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String str = s.toString();
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        String input = str;
                        if (input.contains(" ")) {
                            String search_param = "";
                            for (int i = 0; i < input.length(); i++) {
                                if (input.charAt(i) == ' ')
                                    search_param += "%20";
                                else
                                    search_param += input.charAt(i);
                            }
                            input = search_param;
                        }

                        //Fetch images with this search query
                        try {
                                new FetchImagesTask(actvty).execute(input);
                        } catch (Exception e) {
                            Log.e("main", " error : " + e.getMessage());
                        }

                    }
                },DELAY);
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks if the orientation has changed, then re-run once:
        EditText editText=(EditText)(findViewById(R.id.searchText));
        Log.e("main","cnfg chnged : "+editText.getText().toString());
        new FetchImagesTask(this).execute(editText.getText().toString());
    }
}