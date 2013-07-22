package com.sql2nosql.node;

import com.akiban.sql.parser.ColumnReference;
import com.akiban.sql.parser.NodeTypes;
import com.akiban.sql.parser.QueryTreeNode;
import com.google.common.collect.HashBiMap;
import com.sql2nosql.Constants;

import java.util.HashMap;

public class ColumnAction implements NodeAction, Constants {
    @Override
    public void action(QueryTreeNode node, HashMap<String, Object> list) {

        if (node.getNodeType() == NodeTypes.COLUMN_REFERENCE) {

            ColumnReference ref = (ColumnReference) node;

            if (list.containsKey(COLUMN)) {
                HashBiMap<String, String> subList = (HashBiMap) list.get(COLUMN);
                subList.put(ref.getColumnName().toUpperCase(), ref.getTableName().toUpperCase());
                list.put(COLUMN, subList);
            } else {
                HashBiMap<String, String> subList = HashBiMap.create();
                subList.put(ref.getColumnName().toUpperCase(), ref.getTableName().toUpperCase());
                list.put(COLUMN, subList);
            }

        }
    }
}
