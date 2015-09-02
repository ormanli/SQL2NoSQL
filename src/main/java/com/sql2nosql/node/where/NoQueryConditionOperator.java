package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public enum NoQueryConditionOperator {
	GREATER_THAN(Filters::gt, ">"), LESS_THAN(Filters::lt, "<"), NOT_EQUALS(Filters::ne, "!="), LESS_THAN_EQUALS(Filters::lte, "<="), GREATER_THAN_EQUALS(Filters::gte, ">="), EQUALS(Filters::eq, "==");

	private ConditionalOperator mongoOperator;
	private String symbol;

	private NoQueryConditionOperator(ConditionalOperator mongoOperator, String symbol) {
		this.mongoOperator = mongoOperator;
		this.symbol = symbol;
	}

	public Bson getClause(String fieldName, Object value) {
		return this.mongoOperator.merge(fieldName, value);
	}

	@Override
	public String toString() {
		return symbol;
	}
}
