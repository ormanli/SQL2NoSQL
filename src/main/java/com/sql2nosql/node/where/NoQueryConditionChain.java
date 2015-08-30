package com.sql2nosql.node.where;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

public enum NoQueryConditionChain {
	AND(Filters::and), OR(Filters::or), NOOP(getNoOp());

	private ConditionalChain mongoChain;

	private NoQueryConditionChain(ConditionalChain mongoChain) {
		this.mongoChain = mongoChain;
	}

	public Bson getChain(Bson... bsons) {
		return this.mongoChain.merge(bsons);
	}

	public static ConditionalChain getNoOp() {
		return NO_OP;
	}

	private final static ConditionalChain NO_OP = new ConditionalChain() {
		@Override
		public Bson merge(Bson... bsons) {
			return bsons[0];
		}
	};

}
