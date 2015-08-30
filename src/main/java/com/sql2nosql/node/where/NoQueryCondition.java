package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public class NoQueryCondition {
	private String columnName;
	private NoQueryConditionOperator operator;
	private Object value;
	private NoQueryConditionChain chain;

	public NoQueryCondition(NoQueryConditionChain chain, String columnName, NoQueryConditionOperator operator, Object value) {
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
		this.chain = chain;
	}

	public NoQueryCondition() {
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public NoQueryConditionOperator getOperator() {
		return operator;
	}

	public void setOperator(NoQueryConditionOperator operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public NoQueryConditionChain getChain() {
		return chain;
	}

	public void setChain(NoQueryConditionChain chain) {
		this.chain = chain;
	}

	public Bson getBson() {
		return this.operator.getClause(this.columnName, this.value);
	}

}
