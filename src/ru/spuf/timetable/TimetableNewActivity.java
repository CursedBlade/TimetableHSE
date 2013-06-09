package ru.spuf.timetable;

import adapters.TimetableAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TimetableNewActivity extends ExtendActivity {
	/**
	 * Выбранная группа
	 */
	Long groupId;
	/**
	 * Объект с расписанием
	 */
	public Timetable timetable;
	/**
	 * Асинхронная загрузка расписания
	 * @author Арт
	 *
	 */
	public class timetableLoadTask extends AsyncTask<Object, Void, Exception>{

		@Override
		/**
		 * Фоновое выполнение. Было лень заморачиваться, поэтмоу в случае ошибки, возвращается Exception. Быдлостайл.
		 */
		protected Exception doInBackground(Object... params) {
			// TODO Auto-generated method stub
			try {
				Boolean ignoreCache=false;
				if(params.length>0)
					ignoreCache=(Boolean)params[0];
				timetable=new Timetable(groupId.intValue(),ignoreCache);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e;
			}
			return null;
		}
		@Override
		/**
		 * Выполнение по завершению загрузки, принимается быдло эксепшн=)
		 */
		protected void onPostExecute(Exception result){
			if(result!=null){
				Toast.makeText(getApplicationContext(), result.getMessage(), Toast.LENGTH_LONG).show();
			}
			else{
				TextView groupName=(TextView)findViewById(R.id.groupName);
				groupName.setText(timetable.groupName);
				
		        ListView table=(ListView)findViewById(R.id.table);
		        //Timetable timetable= new Timetable((int)groupId);
		        
		        mAdapter = new TimetableAdapter(getApplicationContext());
		        for (int i = 0; i < timetable.days.size(); i++) {
		        	mAdapter.addSeparatorItem((timetable.days.get(i).dow+" ("+timetable.days.get(i).date+")").toUpperCase());
		           	for(int j=0; j< timetable.days.get(i).pairs.size(); j++){
		           		mAdapter.addItem(timetable.days.get(i).pairs.get(j).title,(j+1)+"\n"+timetable.days.get(i).pairs.get(j).time);
		           	}
		           	
		        }
		        table.setAdapter(mAdapter);
		        
		        findViewById(R.id.progressBar).setVisibility(View.GONE);
			}
		}
		
	}
	/**
	 * адаптер для вывода в ListVIew
	 */
    private TimetableAdapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    @Override
    public void onResume(){
    	super.onResume();
    	groupId=settings.getParamLong("groupId");
    	refreshAction();
    }
    /**
     * Обновление расписания.
     */
    public void refreshAction(){

    	if(groupId>0){
    		findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    		//передается true, чтобы игнорировать кэш
    		new timetableLoadTask().execute(true);
    	}
    	else{
	        findViewById(R.id.progressBar).setVisibility(View.GONE);
    	}
    }
    /**
     * Нажание на кнопку обновления.
     * @param v
     */
    public void refresh(View v){
    	refreshAction();
    }
    /**
     * Нажатие на кнопку перехода в браузер. Переход в браузер. Если группа не выбрана, переход осуществляется на главную.
     * @param v
     */
    public void openSite(View v){
    	String url;
    	
    	if(groupId>0 && timetable!=null){
    		url = timetable.link;
    	}
    	else{

    		url = "http://timetable.spuf.ru/";
    	}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
    }
    /**
     * Нажатие на кнопку выбора группы. Переход в Activity с выбором группы.
     * @param v
     */
    public void openGroupListActivity(View v){
    	Intent groupListActivity=new Intent(this,GroupListActivity.class);
    	startActivity(groupListActivity);
    }
}