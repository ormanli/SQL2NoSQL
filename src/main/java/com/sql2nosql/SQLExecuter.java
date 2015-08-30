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

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sql2nosql.util.Constants;
import com.sql2nosql.util.settings.Settings;
import com.sql2nosql.visitor.SQLVisitor;

/**
 * @author ormanli
 */
public class SQLExecuter implements Constants {
	private MongoClient mongo;
	private MongoDatabase db;

	public SQLExecuter(Settings settings) {
		this(settings.getHost(), settings.getPort(), settings.getDbname());
	}

	public SQLExecuter(String host, Integer port, String dbName) {
		mongo = new MongoClient(host, port);
		db = mongo.getDatabase(dbName);
	}

	public FindIterable<Document> execute(String SQL) throws Exception {
		Statement statement = CCJSqlParserUtil.parse(SQL);

		SQLVisitor visitor = new SQLVisitor();
		statement.accept(visitor);

		MongoCollection<Document> collection = db.getCollection(visitor.getTable());

		return collection.find(visitor.getWhere().getFilter());
	}
}
