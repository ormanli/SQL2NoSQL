package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

public interface ConditionalChain {
	Bson merge(Bson... bsons);
}
