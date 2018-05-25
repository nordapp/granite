package org.i3xx.util.platform.impl;

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


import java.util.NoSuchElementException;

import org.i3xx.util.basic.platform.Platform;
import org.i3xx.util.basic.platform.ServiceContext;
import org.i3xx.util.basic.platform.ServiceReference;

public class PlatformImpl {
	
	/**
	 * Gets the current installed platform service
	 * 
	 * @return The platform object of the current platform
	 */
	public static final Platform getPlatformService(ServiceContext context) {
		ServiceReference<Platform> psrv = context.getServiceReference(Platform.class);
		if(psrv==null)
			throw new NoSuchElementException("The requested service reference 'Platform' is not available.");
		Platform plaf = context.getService(psrv);
		if(plaf==null)
			throw new NoSuchElementException("The requested service 'Platform' is not available.");
		
		return plaf;
	}
}
