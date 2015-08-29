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

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class SettingsImporter {

	private static Settings appSettings;

	public static Settings importSettings(String path) throws Exception {
		if (appSettings == null) {
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			appSettings = (Settings) jaxbUnmarshaller.unmarshal(file);
		}
		return appSettings;
	}
}
