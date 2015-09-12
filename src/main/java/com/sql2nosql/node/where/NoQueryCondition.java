package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public class NoQueryCondition implements QueryCondition {
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

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public NoQueryConditionOperator getOperator() {
		return operator;
	}

	@Override
	public void setOperator(NoQueryConditionOperator operator) {
		this.operator = operator;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public NoQueryConditionChain getChain() {
		return chain;
	}

	@Override
	public void setChain(NoQueryConditionChain chain) {
		this.chain = chain;
	}

	@Override
	public Bson getBson() {
		return this.operator.getClause(this.columnName, this.value);
	}

	@Override
	public String toString() {
		return chain != null && chain != NoQueryConditionChain.NOOP ? chain.toString() + " " : "" + tableName + "." + columnName + " " + operator + " " + value;
	}

}
