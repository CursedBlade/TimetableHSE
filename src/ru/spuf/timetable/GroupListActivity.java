package ru.spuf.timetable;

import java.util.Vector;

import ru.spuf.timetable.GroupList.Group;
import adapters.GroupAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class GroupListActivity extends ExtendActivity{
	private Vector<Group> groupList;
	private Long groupId;
	
	public class groupListLoadTask extends AsyncTask<Void, Void, Exception>{

		@Override
		protected Exception doInBackground(Void... params) {
	        try {
				groupList=GroupList.getList();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e;
			}
			return null;
		}
		@Override
		protected void onPostExecute(Exception result){
			
			if(result!=null){
				Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
			}
			else{
				ListView groupListView=(ListView)findViewById(R.id.groupListView);
		        
		        groupListView.setAdapter(new GroupAdapter(getApplicationContext(),groupList,groupId));
		        
		        groupListView.setOnItemClickListener(new OnItemClickListener() {
	
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int index,
							long groupId) {
						settings.setParamLong("groupId", groupId);
						finish();
					}
				});
		        
		        findViewById(R.id.progressBar).setVisibility(View.GONE);
			}
		}
		
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        
        groupId=settings.getParamLong("groupId");
        if(groupId!=null){
        	new groupListLoadTask().execute();
        }
    }
}
