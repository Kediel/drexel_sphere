package com.example.kdl.drexelsphere;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EventListItem extends ArrayAdapter<EventContent> {

    private Activity context;
    private List<EventContent> eventContentList;

    public EventListItem(Activity context, List<EventContent> eventContentList) {
        super(context, R.layout.list_event_framelayout_content, eventContentList);
        this.context = context;
        this.eventContentList = eventContentList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_event_framelayout_content, null, true);

        TextView name_listevent = (TextView) listViewItem.findViewById(R.id.name_listevent);
        TextView content_listevent = (TextView) listViewItem.findViewById(R.id.content_listevent);
        TextView time_listevent = (TextView) listViewItem.findViewById(R.id.time_listevent);

        EventContent eventContent = eventContentList.get(position);

        name_listevent.setText(eventContent.geteventName());
        content_listevent.setText(eventContent.geteventDetail());
        time_listevent.setText(eventContent.geteventTime());

        return listViewItem;
    }
}