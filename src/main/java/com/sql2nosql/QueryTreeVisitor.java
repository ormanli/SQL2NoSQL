/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sql2nosql;

/**
 *
 * @author ormanli
 */

import com.akiban.sql.StandardException;
import com.akiban.sql.parser.*;
import com.sql2nosql.node.NodeAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class QueryTreeVisitor implements Visitor, Constants {

    private boolean onFirst = true;
    private boolean didPrint = false;
    private ArrayList<String> aaa = null;
    private HashMap<String, LinkedHashMap<String, Object>> list = null;

    private static String qualify(String s) {
        return (s != null) ? s + "." : "";
    }

    public ArrayList<String> getAaa() {
        return aaa;
    }

    public void setAaa(ArrayList<String> aaa) {
        this.aaa = aaa;
    }

    public QueryTreeVisitor(ArrayList<String> aaa) {
        this.aaa = aaa;
    }

    public HashMap<String, LinkedHashMap<String, Object>> getList() {
        return list;
    }

    public void setList(HashMap<String, LinkedHashMap<String, Object>> list) {
        this.list = list;
    }

    public QueryTreeVisitor(HashMap<String, LinkedHashMap<String, Object>> list) {
        this.list = list;
    }

    @Override
    public Visitable visit(Visitable visitable) throws StandardException {
        QueryTreeNode node = (QueryTreeNode) visitable;

        List nodeList = NodeLoader.getNodes();
        for (int i = 0; i < nodeList.size(); i++) {
            NodeAction action = (NodeAction) nodeList.get(i);
            action.action(node);
        }
        if (node.getNodeType() == NodeTypes.COLUMN_REFERENCE) {
            ColumnReference ref = (ColumnReference) node;

            if (list.containsKey(COLUMN)) {
                LinkedHashMap<String, Object> subList = list.get(COLUMN);
                subList.put(ref.getColumnName().toUpperCase(), ref.getTableName().toUpperCase());
                list.put(COLUMN, subList);
            } else {
                LinkedHashMap<String, Object> subList = new LinkedHashMap<String, Object>();
                subList.put(ref.getColumnName().toUpperCase(), ref.getTableName().toUpperCase());
                list.put(COLUMN, subList);
            }

        } else if (node.getNodeType() == NodeTypes.FROM_BASE_TABLE) {
            FromBaseTable ref = (FromBaseTable) node;
            if (list.containsKey(TABLE)) {
                LinkedHashMap<String, Object> subList = list.get(TABLE);
                subList.put(ref.getOrigTableName().toString().toUpperCase(), ref.getExposedName().toUpperCase());
                list.put(TABLE, subList);
            } else {
                LinkedHashMap<String, Object> subList = new LinkedHashMap<String, Object>();
                subList.put(ref.getOrigTableName().toString().toUpperCase(), ref.getExposedName().toUpperCase());
                list.put(TABLE, subList);
            }
        } else if (node.getNodeType() == NodeTypes.BINARY_EQUALS_OPERATOR_NODE) {
            BinaryOperatorNode ref = (BinaryOperatorNode) node;
        }


        return visitable;
    }

    @Override
    public boolean visitChildrenFirst(Visitable node) {
        return false;
    }

    @Override
    public boolean stopTraversal() {
        return false;
    }

    @Override
    public boolean skipChildren(Visitable node) throws StandardException {
        return false;
    }
}