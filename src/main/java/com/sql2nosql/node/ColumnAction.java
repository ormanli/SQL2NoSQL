package com.sql2nosql.node;

import com.akiban.sql.parser.QueryTreeNode;

public class ColumnAction implements NodeAction {
    @Override
    public void action(QueryTreeNode node) {
        System.out.println("Column");
    }
}
