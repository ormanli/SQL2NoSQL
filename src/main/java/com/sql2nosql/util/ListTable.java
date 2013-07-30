package com.sql2nosql.util;

import java.util.ArrayList;

/**
 * The Class ListTable.
 *
 * @author ormanli
 */
public class ListTable {

    private ArrayList<String[]> table = new ArrayList<String[]>();

    /**
     * Put alias.
     *
     * @param alias Alias
     */
    public synchronized void putAlias(String alias) {
        if (table.size() > 0) {
            String[] temp = table.get(table.size() - 1);

            if (temp[0] != null) {
                table.add(new String[]{alias, null, null});
            }
        } else {
            table.add(new String[]{alias, null, null});
        }
    }

    /**
     * Put table name and column name to table.
     * If last row contains alias and other two rows are
     * empty puts table name and column name next to it.
     * Else puts table name and column name to new row.
     *
     * @param tableName  Table Name
     * @param columnName Column Name
     */
    public synchronized void putContent(String tableName, String columnName) {
        int index = table.size() - 1;
        String[] temp = table.get(index);

        if (temp[0] != null && temp[1] == null && temp[2] == null) {
            temp = new String[]{temp[0], tableName, columnName};
            table.set(index, temp);
        } else if (temp[0] != null && temp[1] != null && temp[2] != null) {
            temp = new String[]{null, tableName, columnName};
            table.add(temp);
        }
    }

    /**
     * Length.
     *
     * @return Length of Table
     */
    public int length() {
        return table.size();
    }

    /**
     * Gets the row.
     *
     * @param index
     * @return Row
     */
    public String[] get(int index) {
        return table.get(index);
    }

}
