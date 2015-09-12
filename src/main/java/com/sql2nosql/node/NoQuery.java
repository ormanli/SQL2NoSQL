package com.sql2nosql.node;

import com.sql2nosql.node.column.QueryColumn;
import com.sql2nosql.node.table.QueryTable;
import com.sql2nosql.node.where.QueryWhere;

public class NoQuery implements Query {

	private final QueryColumn columns;
	private final QueryTable tables;
	private final QueryWhere where;

	public NoQuery(QueryColumn columns, QueryTable tables, QueryWhere where) {
		this.columns = columns;
		this.tables = tables;
		this.where = where;
	}

	@Override
	public QueryColumn getColumns() {
		return columns;
	}

	@Override
	public QueryTable getTables() {
		return tables;
	}

	@Override
	public QueryWhere getWhere() {
		return where;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (columns != null) {
			builder.append("COLUMNS: ").append(columns).append(", ");
		}
		if (tables != null) {
			builder.append("TABLES: ").append(tables).append(", ");
		}
		if (where != null) {
			builder.append("CONDITION: ").append(where);
		}
		return builder.toString();
	}

}
