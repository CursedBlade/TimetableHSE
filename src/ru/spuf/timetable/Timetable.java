package ru.spuf.timetable;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Класс "Расписание"
 * @author Арт
 *
 */
public class Timetable {
    /**
     * Класс "Пара"
     * @author Арт
     */
    public static class Pair{
    	/**
    	 * Номер пары
    	 */
        int number;
        /**
         * Время пары.
         * TODO: сделать объект класса время,а не строку.
         */
        public String time;
        /**
         * Название пары
         */
        String title;
        /**
         * HTML стиль пары
         */
        String style;
        /**
         * Группы, с которыми эта пара общая
         */
        String with;

        /**
         * Только обязательные параметры
         * @param number номер пары
         * @param time время пары
         * @param title название пары
         */
        Pair(int number, String time, String title){
            this.number=number;
            this.time=time;
            this.title=title;
        }

        /**
         * Все параметры
         * @param number номер пары
         * @param time время пары
         * @param title название пары
         * @param style html стиль пары
         * @param with группы, с которыми эта пара общая
         */
        Pair(int number, String time, String title,String style, String with){
            this.number=number;
            this.time=time;
            this.title=title;
            this.style=style;
            this.with=with;
        }
    }
    /**
     * Класс "День"
     * @author Арт
     *
     */
    public static class Day{
    	/**
    	 * Дата. 
    	 * TODO: сделать объект класс даты, а не строку
    	 */
    	public String date;
    	/**
    	 * День недели
    	 */
    	public String dow;
    	/**
    	 * Список пар
    	 */
        public Vector<Pair> pairs=new Vector<Pair>();
    	Day(String date,String dow){
    		this.date=date;
    		this.dow=dow;
    	}
    	/**
    	 * Назначение пар на день.
    	 * @param pairsArray json массив пар
    	 */
    	public void setPairs(JSONArray pairsArray){
    		for(int i=0; i<pairsArray.length(); i++){
				JSONObject pairJSONObject=(JSONObject) pairsArray.opt(i);
				Pair pairObject=new Pair(pairJSONObject.optInt("number"),pairJSONObject.optString("time"),pairJSONObject.optString("title"),pairJSONObject.optString("style"),pairJSONObject.optString("with"));
				pairs.add(pairObject);
    		}
    	}
    }

    /**
     * Имя группы.
     */
    public String groupName="";
    /**
     * Ссылка на расписание группы.
     */
    public String link="";
    /**
     * Список дней
     */
    public Vector<Day> days=new Vector<Day>();
    
    /**
     * Конструктор расписания. Передается номер группы и флажок, отвечающий за кэш (если ignoreCache=true, то кэш будет проигнорирован и данные будут загружены из Интернета, а кэш перезаписан.
     * Данные приходят за 7 дней.
     * TODO: Можно сделать возможность передачи кол-ва дней, но наверно смысла нет. 7 дней достаточно вроде. Да и кэш, чтоб 1 был, а то плодить не ок. Надо решить.
     * @param groupId номер группы
     * @param ignoreCache игнорирование кэша. Если true, кэш будет проигнорирован
     * @throws Exception
     */
    public Timetable(int groupId,boolean ignoreCache) throws Exception{
    	API api=new API();
    	
    	String requestUri="query=latest&group="+groupId+"&days=7";
    	JSONObject timetableJSONObject=null;
		if(!ignoreCache) 
			timetableJSONObject=JSONCache.getCache(requestUri);
		if(timetableJSONObject==null){
			timetableJSONObject=api.getJSONByRequest(requestUri);
			if(timetableJSONObject!=null)
				JSONCache.putCache(requestUri, timetableJSONObject);
		}
		if(timetableJSONObject==null)
			timetableJSONObject=JSONCache.getCache(requestUri,true);
		
		if(timetableJSONObject==null)
			throw new Exception("Нет соединения");
		
    	groupName=timetableJSONObject.optString("group");
    	link=timetableJSONObject.optString("link");
    	
		JSONArray timetableJSONArray=timetableJSONObject.optJSONArray("timetable");
		for(int i=0; i<timetableJSONArray.length(); i++){
			JSONObject dayJSONObject=(JSONObject) timetableJSONArray.opt(i);
			Day dayObject=new Day(dayJSONObject.optString("date"),dayJSONObject.optString("dow"));
			dayObject.setPairs(dayJSONObject.optJSONArray("pairs"));
			days.add(dayObject);
		}
    }
}
