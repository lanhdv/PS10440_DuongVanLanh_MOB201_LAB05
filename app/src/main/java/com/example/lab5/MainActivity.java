package com.example.lab5;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvNews = findViewById(R.id.lvNews);
        NewsSeedAsyncTask newsSeedAsyncTask = new NewsSeedAsyncTask();
        newsSeedAsyncTask.execute();

        intent = new Intent(this,Main2Activity.class);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = News.get(position);
                intent.putExtra("openlink",link);
                startActivity(intent);

            }
        });


    }

    class NewsSeedAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://vnexpress.net/rss/suc-khoe.rss");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                List newses = NewsReader.listNews(inputStream);
                final ListAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.select_dialog_item, newses);
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