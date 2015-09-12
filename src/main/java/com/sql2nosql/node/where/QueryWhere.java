package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public interface QueryWhere {

	public void addCondition(NoQueryCondition condition);

	public NoQueryCondition peekCondition();

	public Bson getFilter();

}
