package ru.spuf.timetable;

import android.app.Activity;
import android.os.Bundle;

abstract public class ExtendActivity extends Activity{
	protected Settings settings;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings=new Settings(getApplicationContext());
		JSONCache.setContext(getApplicationContext());
	}
}
