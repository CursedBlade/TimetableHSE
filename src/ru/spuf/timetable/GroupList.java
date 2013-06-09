package ru.spuf.timetable;

import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Класс "Список групп"
 * 
 * @author Арт
 * 
 */
public class GroupList {
	/**
	 * Класс "Группа"
	 * 
	 * @author Арт
	 * 
	 */
	public static class Group {
		private int id;
		private String name;

		/**
		 * Конструктор класса
		 * 
		 * @param id
		 * @param name
		 */
		Group(int id, String name) {
			this.id = id;
			this.name = name;
		}

		/**
		 * Получение идентификатора группы
		 * 
		 * @return
		 */
		public int getId() {
			return id;
		}

		/**
		 * Получение имени группы
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * Получение списка групп
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Vector<Group> getList() throws Exception {
		API api = new API();
		Vector<Group> list = new Vector<Group>();
		String requestUri = "query=groups";

		JSONObject groupsJSONObject = JSONCache.getCache(requestUri);
		if (groupsJSONObject == null) {
			groupsJSONObject = api.getJSONByRequest(requestUri);
			if (groupsJSONObject != null)
				JSONCache.putCache(requestUri, groupsJSONObject);
		}
		if (groupsJSONObject == null)
			groupsJSONObject = JSONCache.getCache(requestUri, true);
		
		if (groupsJSONObject == null)
			throw new Exception("Нет соединения");

		try {
			JSONArray groupsJSONArray = groupsJSONObject.getJSONArray("groups");
			for (int i = 0; i < groupsJSONArray.length(); i++) {
				JSONObject groupJSONObject = (JSONObject) groupsJSONArray
						.opt(i);
				list.add(new Group(groupJSONObject.optInt("id"),
						groupJSONObject.optString("name")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
		// Group[] groupList=new Group[];

	}

}
