package com.sql2nosql;

import com.akiban.sql.parser.SQLParser;
import com.akiban.sql.parser.StatementNode;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ormanli
 */
public class SQLExecuter implements Constants {

    public static String execute(String SQL) throws Exception {

        HashMap<String, Object> settings = SettingsImporter.importSettings("sql2nosql.settings.xml");

        MongoClient mongo = new MongoClient(settings.get("host").toString(), Integer.parseInt(settings.get("port").toString()));

        DB db = mongo.getDB(settings.get("dbname").toString());

        ArrayList<String> bfds = new ArrayList<String>();

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
