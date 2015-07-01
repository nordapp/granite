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


public class AvailableKeys {

	public static final String SERVER_HOME = "server.home";
	
	/**
	 * Return the implemented keys
	 * 
	 * @return The array with the keys
	 */
	public static final String[] getKeys() {
		return new String[]{
			SERVER_HOME	
		};
	}
}
