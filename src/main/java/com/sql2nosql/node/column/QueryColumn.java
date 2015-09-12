package com.sql2nosql.node.column;

import org.bson.conversions.Bson;

public interface QueryColumn {
	public void addColumn(String table, String column);

	public Bson getColumns(String table);

}
