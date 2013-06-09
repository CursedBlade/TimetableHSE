package ru.spuf.timetable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Класс для работы с API
 * @author Арт
 *
 */
public class API {
	/**
	 * Адрес для конкатенирования с requestURI
	 */
	private String preparedURL;
	/**
	 * Конструктор класса по умолчанию
	 * host="http://timetable.spuf.ru"
	 * apiVersion=2
	 */
	public API(){
		this.preparedURL="http://timetable.spuf.ru/api.php?api=3&";
	}
	/**
	 * Расширенный конструктор класса, позволяет задать host и версию API
	 * @param host
	 * @param version
	 */
	public API(String host,int version){
		this.preparedURL=host+"/api.php?api="+version+"&";
		
	}

	/**
	 * Метод для загрузки JSON объекта с сервера по запросу requestURI
	 * @param requestURI
	 * @return
	 */
	public JSONObject loadJSONByRequest(String requestURI){
		String responseString=new String();
	    try
	    {
	    	URL connectURL = new URL(this.preparedURL+requestURI);
	    	HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
            //conn.setUseCaches(true);
            conn.setConnectTimeout(10000);
            BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder=new StringBuilder();
            while(in.ready()){
                responseBuilder.append(in.readLine()).append("\n");
            }
            responseString=responseBuilder.toString();
	    }
	    catch(ClientProtocolException cexc){
		    cexc.printStackTrace();
	    }
	    catch(IOException ioex){
		    ioex.printStackTrace();
	    }
	    JSONObject jsonObject=null;
	    try {
			jsonObject=new JSONObject(responseString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    return jsonObject;
	}
	/**
	 * Метод для получения JSON объекта по запросу requestURI
	 * @param requestURI
	 * @return
	 */
	public JSONObject getJSONByRequest(String requestURI){
		
	    return loadJSONByRequest(requestURI);
	}
}
