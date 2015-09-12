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

import java.util.ArrayList;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sql2nosql.node.Query;
import com.sql2nosql.node.column.QueryColumn;
import com.sql2nosql.node.where.QueryWhere;
import com.sql2nosql.util.settings.Settings;
import com.sql2nosql.visitor.SQLVisitor;

/**
 * @author ormanli
 */
public class SQLExecuter {
	private final static Logger logger = Logger.getLogger(SQLExecuter.class);

	private final MongoClient mongo;
	private final MongoDatabase db;
	private boolean outputQuery;

	public SQLExecuter(Settings settings, boolean outputQuery) {
		this(settings.getHost(), settings.getPort(), settings.getDbname(), outputQuery);
	}

	public SQLExecuter(Settings settings) {
		this(settings, false);
	}

	public SQLExecuter(String host, Integer port, String dbName) {
		this(host, port, dbName, false);
	}

	public SQLExecuter(String host, Integer port, String dbName, boolean outputQuery) {
		mongo = new MongoClient(host, port);
		db = mongo.getDatabase(dbName);
		this.outputQuery = outputQuery;
	}

	public FindIterable<Document> execute(String SQL) throws JSQLParserException {
		Statement statement = CCJSqlParserUtil.parse(SQL);

		SQLVisitor visitor = new SQLVisitor();
		statement.accept(visitor);

		Query noQuery = visitor.getNoQuery();

		if (logger.isDebugEnabled() && outputQuery) {
			logger.debug(noQuery);
		}

		ArrayList<String> tables = Lists.newArrayList(noQuery.getTables().getTableNames());

		MongoCollection<Document> collection = db.getCollection(tables.get(0));

		QueryWhere where = noQuery.getWhere();

		QueryColumn columns = noQuery.getColumns();

		return collection.find(where.getFilter()).projection(columns.getColumns(tables.get(0)));
	}
}
