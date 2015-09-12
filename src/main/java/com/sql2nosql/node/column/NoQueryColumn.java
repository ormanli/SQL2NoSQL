package com.sql2nosql.node.column;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mongodb.client.model.Projections;

public class NoQueryColumn implements QueryColumn {
	private final Multimap<String, String> columns = HashMultimap.<String, String> create();

	private static final Bson NULL_BSON = new Bson() {
		@Override
		public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
			return null;
		}
	};

	@Override
	public void addColumn(String table, String column) {
		columns.put(table, column);
	}

	@Override
	public Bson getColumns(String table) {
		Bson result = NULL_BSON;

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
