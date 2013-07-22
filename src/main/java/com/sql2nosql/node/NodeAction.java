package com.sql2nosql.node;

import com.akiban.sql.parser.QueryTreeNode;

import java.util.HashMap;

public interface NodeAction {

    void action(QueryTreeNode node, HashMap<String, Object> list);

}
