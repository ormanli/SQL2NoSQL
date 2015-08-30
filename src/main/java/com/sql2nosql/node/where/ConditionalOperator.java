package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public interface ConditionalOperator<TItem> {
	Bson merge(String fieldName, TItem value);
}
