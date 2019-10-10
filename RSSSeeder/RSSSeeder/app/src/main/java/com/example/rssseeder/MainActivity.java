package com.example.rssseeder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maina);


        lvNews = findViewById(R.id.lvNews);
        NewsSeedAsyncTask newsSeedAsyncTask = new NewsSeedAsyncTask();
        newsSeedAsyncTask.execute();


    }

    class NewsSeedAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://vnexpress.net/rss/giao-duc.rss ");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                List newses = NewsReader.listNews(inputStream);
                final ListAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, newses);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvNews.setAdapter(adapter);
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
