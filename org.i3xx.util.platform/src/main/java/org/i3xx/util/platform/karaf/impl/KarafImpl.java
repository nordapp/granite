package org.i3xx.util.platform.karaf.impl;

/*
 * #%L
 * NordApp OfficeBase :: util :: platform
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


import org.i3xx.util.basic.platform.Platform;
import org.i3xx.util.platform.impl.AvailableKeys;
import org.osgi.framework.BundleContext;

public class KarafImpl implements Platform {
	
	BundleContext bundleContext;
	
	public KarafImpl() {
		bundleContext = null;
	}

	/* (non-Javadoc)
	 * @see org.i3xx.util.basic.platform.Platform#keys()
	 */
	public String[] keys() {
		return AvailableKeys.getKeys();
	}

	/* (non-Javadoc)
	 * @see org.i3xx.util.basic.platform.Platform#getObject(java.lang.String)
	 */
	public Object getObject(String key) {
		if (key.equals( AvailableKeys.SERVER_HOME)) {
			return bundleContext.getProperty("karaf.home");
		}
		else
			throw new IllegalArgumentException("The key '"+key+"' is not supported yet.");
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

}
