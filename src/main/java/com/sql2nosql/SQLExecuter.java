/*******************************************************************************
 * Copyright (c) 2013 Serdar Ormanlı.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Serdar Ormanlı - initial API and implementation
 ******************************************************************************/
package com.sql2nosql;

import com.akiban.sql.parser.SQLParser;
import com.akiban.sql.parser.StatementNode;
import com.mongodb.*;
import com.sql2nosql.util.Settings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ormanli
 */
public class SQLExecuter implements Constants {

	public static String execute(String SQL) throws Exception {

		Settings settings = SettingsImporter.importSettings("sql2nosql.settings.xml");

		MongoClient mongo = new MongoClient(settings.getHost(), settings.getPort().intValue());

		DB db = mongo.getDB(settings.getDbname());

		HashMap<String, LinkedHashMap<String, Object>> list = new HashMap<String, LinkedHashMap<String, Object>>();
		SQLParser parser = new SQLParser();
		StatementNode node = parser.parseStatement(SQL);

		QueryTreeVisitor fdg = new QueryTreeVisitor(list);
		node.accept(fdg);

		String string = null;
		LinkedHashMap<String, Object> tableList = list.get(TABLE);
		for (Map.Entry<String, Object> entry : tableList.entrySet()) {
			string = entry.getKey();
			Object object = entry.getValue();

		}
		DBCollection table = db.getCollection(string.toLowerCase());

		LinkedHashMap<String, Object> whereList = list.get(WHERE);
		String string1 = null;
		Object object = null;
		for (Map.Entry<String, Object> entry : whereList.entrySet()) {
			string1 = entry.getKey();
			object = entry.getValue();

		}

		BasicDBObject searchQuery = new BasicDBObject();

		searchQuery.put(string1.toLowerCase(), object.toString());

		DBCursor cursor = table.find(searchQuery);

		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}

		return null;
	}
}
