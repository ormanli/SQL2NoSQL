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
package com.sql2nosql.util.settings;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class SettingsImporter {

	private final String path;
	private Settings appSettings;

	public SettingsImporter(String path) {
		this.path = path;
	}

	public Settings importSettings() throws JAXBException {
		if (appSettings == null) {
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Settings.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			appSettings = (Settings) jaxbUnmarshaller.unmarshal(file);
		}

		return appSettings;
	}
}
