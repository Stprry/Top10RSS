package com.Fiendtech.top10rss;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;

public class ParseSongs {
    private static final String TAG = "ParseSongs";
    private ArrayList<FeedEntry> songs;

    public ParseSongs() {
        this.songs = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getSongs() {

        return songs; // builds up our list of songs to store in songs returns our list so can be used with adapter
    }

    public boolean parse(String xmlData){
        boolean status=true;
        FeedEntry currentRecord=null;// each time we get a new entery we need to create new object to store details
        boolean inEntery = false; // makes sure we are processing data in entery tag in xml
        String textValue="";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();// creates a class factory
            xpp.setInput(new StringReader(xmlData));
            int eventType= xpp.getEventType(); // lines abouve set up java xml parser (xmlpull.org)
            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName= xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parse: starting tag for"+tagName);
                        if ("entry".equalsIgnoreCase(tagName)){
                            inEntery = true;
                            currentRecord = new FeedEntry();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue=xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: ending tag "+tagName);
                       if(inEntery){
                           if ("entry".equalsIgnoreCase(tagName)){
                               songs.add(currentRecord);
                               inEntery=false;
                           }else if ("name".equalsIgnoreCase(tagName)){
                               currentRecord.setName(textValue);
                           }else if("category".equalsIgnoreCase(tagName)){
                               currentRecord.setCategory(textValue);
                           }else if("title".equalsIgnoreCase(tagName)){
                               currentRecord.setTitle(textValue);
                           }else if("releasedate".equalsIgnoreCase(tagName)) {
                               currentRecord.setReleasedate(textValue);
                           }else if ("image".equalsIgnoreCase(tagName)){
                               currentRecord.setImgUrl(textValue);
                           }
                       }
                       break;
                       default:
                           // nothing else
                }
                eventType=xpp.next();
            }
          //  for (FeedEntry song: songs){ //go trough feedenetry objects that are stored in our arraylist. **just some error checking***
         //       Log.d(TAG, "parse: *************");
       //         Log.d(TAG, song.toString());
         //   }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
