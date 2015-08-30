package com.sql2nosql.node;

import com.sql2nosql.node.where.NoQueryWhere;

public class NoQuery {

	private final String table;
	private final NoQueryWhere where;

	public NoQuery(String table, NoQueryWhere where) {
		this.table = table;
		this.where = where;
	}

	public String getTable() {
		return table;
	}

	public NoQueryWhere getWhere() {
		return where;
	}

}
