package com.sql2nosql.node.column;

import org.bson.conversions.Bson;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.client.model.Projections;

public class NoQueryColumn {
	private final Multimap<String, String> columns = HashMultimap.<String, String> create();

	public void addColumn(String table, String column) {
		columns.put(table, column);
	}

	public Bson getColumns(String table) {
		Bson result = null;

		if (!columns.containsEntry(table, "*")) {
			result = Projections.include(columns.get(table).toArray(new String[columns.get(table).size()]));
		}

		return result;
	}

	@Override
	public String toString() {
		return columns.toString();
	}
}
