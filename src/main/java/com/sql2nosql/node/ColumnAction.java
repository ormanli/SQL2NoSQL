/*******************************************************************************
 * Copyright (c) 2013 Serdar Ormanlı.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Serdar Ormanlı - initial API and implementation
 ******************************************************************************/
package com.sql2nosql.node;

import com.akiban.sql.parser.ColumnReference;
import com.akiban.sql.parser.NodeTypes;
import com.akiban.sql.parser.QueryTreeNode;
import com.google.common.collect.HashBiMap;
import com.sql2nosql.util.Constants;

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
