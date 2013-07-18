package com.sql2nosql.node;

import com.akiban.sql.parser.QueryTreeNode;

public interface NodeAction {

    void action(QueryTreeNode node);

}
