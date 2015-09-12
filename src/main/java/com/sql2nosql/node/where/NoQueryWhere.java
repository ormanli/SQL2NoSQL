package com.sql2nosql.node.where;

import java.util.Stack;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

public class NoQueryWhere implements QueryWhere {
	private final Stack<NoQueryCondition> conditions = new Stack<NoQueryCondition>();

	@Override
	public void addCondition(NoQueryCondition condition) {
		conditions.push(condition);
	}

	@Override
	public NoQueryCondition peekCondition() {
		return conditions.peek();
	}

	@Override
	public Bson getFilter() {
		Bson result;

		if (!conditions.empty()) {
			NoQueryCondition condition = conditions.pop();
			result = condition.getBson();
			if (condition.getChain() != NoQueryConditionChain.NOOP) {
				while (!conditions.empty()) {
					NoQueryCondition condition2 = conditions.pop();
					result = condition.getChain().getChain(condition2.getBson(), result);
				}
			}
		} else {
			result = new Bson() {
				@Override
				public <TDocument> BsonDocument toBsonDocument(Class<TDocument> documentClass, CodecRegistry codecRegistry) {
					return null;
				}
			};
		}

		return result;
	}

	@Override
	public String toString() {
		return conditions.toString();
	}
}
