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
package com.sql2nosql.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.reflect.ClassPath;
import com.sql2nosql.node.NodeAction;

public class NodeLoader implements Constants {
	private static final Logger logger = Logger.getLogger(NodeLoader.class);

	private final List<NodeAction> nodes = new ArrayList<NodeAction>();

	private NodeLoader() {
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			ClassPath classpath = ClassPath.from(classloader);
			for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(NODE_PACKAGE)) {
				nodes.add((NodeAction) Class.forName(classInfo.getName()).newInstance());
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			logger.error("Error occured during initializing nodes", e);
		}

	}

	public static NodeLoader getInstance() {
		return Holder.INSTANCE;
	}

	private static class Holder {
		private static final NodeLoader INSTANCE = new NodeLoader();
	}

	public List<NodeAction> getNodes() {
		return nodes;
	}

}
