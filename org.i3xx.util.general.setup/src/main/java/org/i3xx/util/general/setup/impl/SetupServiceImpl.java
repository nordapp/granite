package org.i3xx.util.general.setup.impl;

/*
 * #%L
 * NordApp OfficeBase :: zero
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


import org.i3xx.util.general.setup.model.SetupService;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a blueprint defined service.
 * 
 * @author Stefan
 *
 */
public class SetupServiceImpl implements SetupService {
	
	static Logger logger = LoggerFactory.getLogger(SetupServiceImpl.class);
	
	/** The mandator setup service */
	private Setup generalSetup;
	
	/** The osgi bundle context */
	private BundleContext bundleContext;
	
	public SetupServiceImpl() {
		generalSetup = null;
		bundleContext = null;
	}
	
	/**
	 * @return the generalSetup
	 */
	public Setup getGeneralSetup() {
		return generalSetup;
	}

	/**
	 * @param generalSetup the generalSetup to set
	 */
	public void setGeneralSetup(Setup generalSetup) {
		this.generalSetup = generalSetup;
	}

	/**
	 * @return the bundleContext
	 */
	public BundleContext getBundleContext() {
		return bundleContext;
	}

	/**
	 * @param bundleContext the bundleContext to set
	 */
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	public void startUp() {
		//does nothing but logging
		logger.info("Service start up '{}' '{}' '{}'.", generalSetup.getTitle(), generalSetup.getId(), generalSetup.getRoot());
	}
	
}
