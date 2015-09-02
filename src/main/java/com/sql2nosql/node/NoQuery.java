package com.sql2nosql.node;

import com.google.common.collect.BiMap;
import com.sql2nosql.node.column.NoQueryColumn;
import com.sql2nosql.node.where.NoQueryWhere;

public class NoQuery {

	private final NoQueryColumn columns;
	private final BiMap<String, String> tables;
	private final NoQueryWhere where;

	public NoQuery(NoQueryColumn columns, BiMap<String, String> tables, NoQueryWhere where) {
		this.columns = columns;
		this.tables = tables;
		this.where = where;
	}

	public NoQueryColumn getColumns() {
		return columns;
	}

	public BiMap<String, String> getTables() {
		return tables;
	}

	public NoQueryWhere getWhere() {
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
