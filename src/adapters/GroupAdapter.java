package adapters;

import java.util.Vector;

import ru.spuf.timetable.R;
import ru.spuf.timetable.GroupList.Group;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private Long currentGroupId;
	
	protected Vector<Group> groupList=new Vector<Group>();
	protected Context context;
	
	
	public GroupAdapter(Context ctxt,Vector<Group> groupList, Long currentGroupId){
		this.context=ctxt;
		this.groupList=groupList;
		this.currentGroupId=currentGroupId;
        this.mInflater = (LayoutInflater)ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return groupList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return groupList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return groupList.get(arg0).getId();
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		int id=R.layout.group;
		if(groupList.get(arg0).getId()==currentGroupId)
			id=R.layout.group_active;
		View convertView = mInflater.inflate(id, null);
        TextView txtView=(TextView)convertView.findViewById(R.id.group);
		txtView.setText(groupList.get(arg0).getName());
		// TODO Auto-generated method stub
		return convertView;
	}
	
}
