package adapters;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Vector;

import ru.spuf.timetable.GroupList.Group;
import ru.spuf.timetable.R;
import ru.spuf.timetable.Timetable;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TimetableAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private static final int TYPE_MAX_COUNT = TYPE_SEPARATOR + 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private ArrayList<String> mAddData = new ArrayList<String>();
    private LayoutInflater mInflater;

    private TreeSet<Integer> mSeparatorsSet = new TreeSet<Integer>();

    public TimetableAdapter(Context ctxt) {
        mInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item,final String addItem) {
        mData.add(item);
        mAddData.add(addItem);
        notifyDataSetChanged();
    }

    public void addSeparatorItem(final String item) {
        mData.add(item);
        mAddData.add("");
        // save separator position
        mSeparatorsSet.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mSeparatorsSet.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    public int getCount() {
        return mData.size();
    }

    public String getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.pair, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.text);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.day, null);
                    holder.textView = (TextView)convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.textView.setText(mData.get(position));
        if(type==TYPE_ITEM){
            ((TextView)convertView.findViewById(R.id.pairNum)).setText(mAddData.get(position));
        }
        return convertView;
    }
    public static class ViewHolder {
        public TextView textView;
    }
}

