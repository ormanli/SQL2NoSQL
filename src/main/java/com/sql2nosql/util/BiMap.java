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

import java.util.HashMap;

/**
 * Basic bi-directional HashMap.
 *
 * @author ormanli
 */
public class BiMap {

    private HashMap<String, String> map1 = new HashMap<String, String>();
    private HashMap<String, String> map2 = new HashMap<String, String>();

    /**
     * Put key and value.
     *
     * @param key   Key
     * @param value Value
     */
    public void put(String key, String value) {
        map1.put(key, value);
        map2.put(value, key);
    }

    /**
     * Gets value by key.
     *
     * @param key Key
     * @return the Value
     */
    public String getValue(String key) {
        return map1.get(key);
    }

    /**
     * Gets key by value.
     *
     * @param value Value
     * @return Key
     */
    public String getKey(String value) {
        return map2.get(value);
    }

    /**
     * Removes the element by Key.
     *
     * @param key Key
     */
    public void remove(String key) {
        map2.remove(map1.get(key));
        map1.remove(key);
    }
}
