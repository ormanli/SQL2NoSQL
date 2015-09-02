package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public class NoQueryCondition {
	private String tableName;
	private String columnName;
	private NoQueryConditionOperator operator;
	private Object value;
	private NoQueryConditionChain chain;

	public NoQueryCondition(NoQueryConditionChain chain, String tableName, String columnName, NoQueryConditionOperator operator, Object value) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
		this.chain = chain;
	}

	public NoQueryCondition() {
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
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

	@Override
	public String toString() {
		return chain != null && chain != NoQueryConditionChain.NOOP ? chain.toString() + " " : "" + tableName + "." + columnName + " " + operator + " " + value;
	}

}
