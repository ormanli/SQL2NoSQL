package com.sql2nosql.node.table;

import java.util.Set;

public interface QueryTable {

	public void addTable(String tableName, String alias);

	public void addTable(String tableName);

	public String getAlias(String tableName);

	public String getTableName(String alias);

	public Set<String> getTableNames();

	public Set<String> getAliases();
}
