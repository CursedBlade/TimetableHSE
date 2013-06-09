package ru.spuf.timetable;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * Работа с настройками
 * @author Арт
 *
 */
public class Settings {
	/**
	 * Контекст
	 */
	Context context;
	/**
	 * Настройки Android
	 */
	SharedPreferences settings;
	/**
	 * Конструктор. Передается только контекст. Секция настроек по умолчанию timetable.
	 * @param ctxt
	 */
	public Settings(Context ctxt){

		context=ctxt;
		settings=context.getSharedPreferences("timetable", 0);
	}
	/**
	 * Конструктор. Передается контекст и секция настроек.
	 * @param ctxt
	 * @param section
	 */
	public Settings(Context ctxt,String section){
		context=ctxt;
		settings=context.getSharedPreferences(section, 0);
	}
	/**
	 * Получение строки по ключу.
	 * @param key
	 * @return
	 */
    public String getParamString(String key){
		return settings.getString(key, null);
	}
    /**
     * Сеттинг строки по ключу.
     * @param key
     * @param value
     */
    public void setParamString(String key, String value){
    	settings.edit().putString(key, value).commit();
    }
    /**
     * Получение int по ключу.
     * @param key
     * @return
     */
    public int getParamInt(String key){
		return settings.getInt(key, 0);
	}
    /**
     * Сеттинг int по ключу.
     * @param key
     * @param value
     */
    public void setParamInt(String key, int value){
    	settings.edit().putInt(key, value).commit();
    }
    /**
     * Получение long по ключу.
     * @param key
     * @return
     */
    public long getParamLong(String key){
		return settings.getLong(key, 0);
	}
    /**
     * Сеттинг long по ключу.
     * @param key
     * @param value
     */
    public void setParamLong(String key, long value){
    	settings.edit().putLong(key, value).commit();
    }
}
