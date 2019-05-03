package com.Fiendtech.top10rss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

// arrayAdtapter is our Superclass
public class FeedAdapter extends ArrayAdapter {
    private static final String TAG = "FeedAdapter";
    private final int layoutRescource;//these are final beacuse we dont want to change them in our code
    private final LayoutInflater layoutInflater; //these are final beacuse we dont want to change them in our code
    private List<FeedEntry> songs;

    public FeedAdapter(Context context, int resource, List<FeedEntry> songs) {
        super(context, resource);
        this.layoutRescource = resource; // declaring finals here and below
        this.layoutInflater = LayoutInflater.from(context);// context is used with the layout inflator to exsantiate the xmlfile to corrisponding view objects (Creates view objects described in xml)
        this.songs = songs;
    }

    @Override
    public int getCount() { // has to be public beacuse its called from seperate package
        return songs.size();// returns number of enteries in the songs list have to overrride to be able to display records.
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {// has to be public beacuse its called from seperate package
        View view = layoutInflater.inflate(layoutRescource,parent,false); // create a view by inflating the layout recourse and finds our text view rescourses, Parent is a param
        TextView TVName = (TextView) view.findViewById(R.id.TVName);
        TextView TVCatagory = (TextView) view.findViewById(R.id.TVCategory); //TV short hand for TextView
        TextView TVTitle = (TextView) view.findViewById(R.id.TVTitle);
        TextView TVRelease= (TextView) view.findViewById(R.id.TVRelease);
        ImageView IVImg = (ImageView) view.findViewById(R.id.IVImg); // IV shorthand for Image View

        FeedEntry currentSong = songs.get(position); // using our getters and setters to fill textviews
        TVName.setText(currentSong.getName());
        TVCatagory.setText(currentSong.getCategory());
        TVTitle.setText(currentSong.getTitle());
        TVRelease.setText(currentSong.getReleasedate());
        //IVImg.setImageResource(R.drawable.get); couldnt figure out how to get image to show
        return view;
    }
}
