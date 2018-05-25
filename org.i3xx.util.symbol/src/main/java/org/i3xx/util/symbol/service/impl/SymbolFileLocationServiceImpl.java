package org.i3xx.util.symbol.service.impl;

/*
 * #%L
 * NordApp OfficeBase :: uno
 * %%
 * Copyright (C) 2014 - 2015 I.D.S. DialogSysteme GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.io.File;
import java.io.IOException;

import org.i3xx.util.basic.io.FilePath;
import org.i3xx.util.basic.platform.Platform;
import org.i3xx.util.basic.platform.ServiceContext;
import org.i3xx.util.general.setup.impl.Setup;
import org.i3xx.util.general.setup.model.SetupService;
import org.i3xx.util.platform.impl.AvailableKeys;
import org.i3xx.util.symbol.service.model.SymbolFileLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SymbolFileLocationServiceImpl implements SymbolFileLocationService {
	
	private static final Logger logger = LoggerFactory.getLogger(SymbolFileLocationService.class);
	
	private final File location;
	
	public SymbolFileLocationServiceImpl(ServiceContext serviceContext, SetupService setupService, Platform platform) throws IOException {
		
		Setup setup = setupService.getGeneralSetup();
		String _root = Setup.setCurrentId(setup.getObroot(), "");
		FilePath resLoc = null;
		logger.debug("Root entry of the general setup: {}", _root);
		if(_root.startsWith(".")){
			String home = (String)platform.getObject(AvailableKeys.SERVER_HOME);
			resLoc = FilePath.get(home).add(_root.substring(1));
		}else{
			resLoc = FilePath.get(_root);
		}
		logger.debug("Path of the general setup: {}", resLoc.getPath());
		File parent = resLoc.toFile();
		if( ! parent.exists() ) {
			parent.mkdirs();
		}
		
		//create file
		File file = new File(parent, "symbol.dat");
		if( ! file.exists() )
			file.createNewFile();
		
		logger.info("Locate symbol file {}", file);
		location = file;
	}

	public File getLocation() {
		return location;
	}
	
	public void setLocation(File location) {
		throw new UnsupportedOperationException("The property 'location' is final and cannot be set.");
		//this.location = location;
	}

}
