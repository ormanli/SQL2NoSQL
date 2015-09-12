package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public interface QueryCondition {

	public String getTableName();

	public void setTableName(String tableName);

	public String getColumnName();

	public void setColumnName(String columnName);

	public NoQueryConditionOperator getOperator();

	public void setOperator(NoQueryConditionOperator operator);

	public Object getValue();

	public void setValue(Object value);

	public NoQueryConditionChain getChain();

	public void setChain(NoQueryConditionChain chain);

	public Bson getBson();

}
