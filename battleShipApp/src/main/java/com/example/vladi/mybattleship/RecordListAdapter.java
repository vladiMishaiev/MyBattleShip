package com.example.vladi.mybattleship;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vladi.mybattleship.Logic.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vladi on 1/13/2018.
 */

public class RecordListAdapter extends ArrayAdapter<Record> {

    private Context mContext;
    private int mResource;


    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public RecordListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        Double birthday = getItem(position).getScore();

        //Create the person object with the information
        Record record = new Record(name,birthday);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, null);

        TextView playerName = (TextView) convertView.findViewById(R.id.playerNameText);
        TextView playerScore= (TextView) convertView.findViewById(R.id.playerScoreText);


        playerName.setText(record.getName());
        playerScore.setText(record.getScore().toString());



        return convertView;
    }
}
