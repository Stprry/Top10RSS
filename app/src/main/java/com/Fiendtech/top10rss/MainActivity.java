package com.Fiendtech.top10rss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listSongs = (ListView)  findViewById(R.id.xmlListView);// xml list view is a view not a button so we have to cast a view thats returned by FindViewbyID

        Log.d(TAG, "onCreate: starting AsyncTask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml");
        Log.d(TAG, "onCreate: Done");
    }

    private class DownloadData extends AsyncTask<String,Void,String>{
        private static final String TAG = "DownloadData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: param is "+s);
            ParseSongs parseSongs = new ParseSongs();
            parseSongs.parse(s);

           // ArrayAdapter<FeedEntry> arrayAdapter = new ArrayAdapter<>( // create new array adpater to provide our listview with a populated data to display
          //          MainActivity.this,R.layout.list_item,parseSongs.getSongs());// we use the 3 paramaters Context(main activity),Rescource containing the texxt view that the array adpater puts the data in(list_item),List of objects (getSongs)
           // listSongs.setAdapter(arrayAdapter);// use listviews setadapter to tell adapter what it should use to get its data.
            FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this,R.layout.list_record,parseSongs.getSongs());
            listSongs.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Starts With"+strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null){
                Log.e(TAG, "doInBackground: Error Downloading");// error catching if rss returns nothing
            }
            return rssFeed; //
        }

        private String downloadXML (String urlPath){
            StringBuilder xmlResult = new StringBuilder(); // use string builder bc appending to string alot from input stream, more efficent that concat

            try { // try catch b/c if errors/execptions will be handled appropriately
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // opens the stream of data once connection is made
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: Response Code = "+ response);// logs response code for debugging purpose
                InputStream inputStream = connection.getInputStream(); // creates inputstream from the connection url
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);// use inputstream to create inputstreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);// block of data read in to buffer memory so program can use it's buffer

                int charRead;
                char[] inputBuffer = new char[500];//tcp packets are only around 500 char
                while (true){// will loop till end of stream is reached
                    charRead= reader.read(inputBuffer);
                    if (charRead<0){
                        break; // ends loop beacuse nothing else in buffer
                    }
                    if (charRead>0){
                        xmlResult.append(String.copyValueOf(inputBuffer,0,charRead));
                    }
                }
                reader.close();// closes  reader
                return xmlResult.toString();
            }catch (MalformedURLException e){
                Log.e(TAG, "downloadXML: Invalid URL "+e.getMessage());
            }catch (IOException e){
                Log.e(TAG, "downloadXML: IO Execption reading data"+e.getMessage()); // error logging any IO Exceptions (Input/output)
            }catch (SecurityException e){
                Log.e(TAG, "downloadXML: Security Exception Permission Needed"+e.getMessage());
            }

            return null; // returns null so rss dodsn't error.
        }
    }
}
