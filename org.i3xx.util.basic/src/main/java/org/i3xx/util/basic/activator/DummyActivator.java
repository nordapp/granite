package org.i3xx.util.basic.activator;

/*
 * #%L
 * NordApp OfficeBase :: util :: basic
 * %%
 * Copyright (C) 2015 I.D.S. DialogSysteme GmbH
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


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyActivator implements BundleActivator {
	
	static Logger logger = LoggerFactory.getLogger(DummyActivator.class);
			
	public DummyActivator() {
	}

	public void start(BundleContext context) throws Exception {
		logger.info("Start activator.");
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Stop activator.");
	}

}
