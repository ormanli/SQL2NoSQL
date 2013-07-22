package com.sql2nosql.node;

import com.akiban.sql.parser.FromBaseTable;
import com.akiban.sql.parser.NodeTypes;
import com.akiban.sql.parser.QueryTreeNode;
import com.google.common.collect.HashBiMap;
import com.sql2nosql.Constants;

import java.util.HashMap;

public class TableAction implements NodeAction, Constants {
    @Override
    public void action(QueryTreeNode node, HashMap<String, Object> list) {

        if (node.getNodeType() == NodeTypes.FROM_BASE_TABLE) {

            FromBaseTable ref = (FromBaseTable) node;

            if (list.containsKey(TABLE)) {
                HashBiMap<String, String> subList = (HashBiMap) list.get(TABLE);
                subList.forcePut(ref.getOrigTableName().toString().toUpperCase(), ref.getExposedName().toUpperCase());
                list.put(TABLE, subList);
            } else {
                HashBiMap<String, String> subList = HashBiMap.create();
                subList.forcePut(ref.getOrigTableName().toString().toUpperCase(), ref.getExposedName().toUpperCase());
                list.put(TABLE, subList);
            }
        }
    }
}
