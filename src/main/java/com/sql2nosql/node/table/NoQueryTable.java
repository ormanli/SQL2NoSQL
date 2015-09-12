package com.sql2nosql.node.table;

import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NoQueryTable implements QueryTable {
	private final BiMap<String, String> tables = HashBiMap.<String, String> create();

	@Override
	public void addTable(String tableName, String alias) {
		tables.put(tableName, alias);
	}

	@Override
	public void addTable(String tableName) {
		addTable(tableName, tableName);
	}

	@Override
	public String getAlias(String tableName) {
		return tables.get(tableName);
	}

	@Override
	public String getTableName(String alias) {
		return tables.inverse().get(alias);
	}

	@Override
	public Set<String> getTableNames() {
		return tables.keySet();
	}

	@Override
	public Set<String> getAliases() {
		return tables.inverse().keySet();
	}

	@Override
	public String toString() {
		return tables.toString();
	}
}
