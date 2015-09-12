package com.sql2nosql.node;

import com.sql2nosql.node.column.QueryColumn;
import com.sql2nosql.node.table.QueryTable;
import com.sql2nosql.node.where.QueryWhere;

public interface Query {
	public QueryColumn getColumns();

	public QueryTable getTables();

	public QueryWhere getWhere();
}
