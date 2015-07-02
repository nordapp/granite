package org.i3xx.util.basic.platform;

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


public interface Platform {

	/**
	 * Get an array with available keys to platform dependent resources.
	 * 
	 * @return The String array containing the keys.
	 */
	String[] keys();
	
	/**
	 * Get the platform dependent resource by key.
	 * 
	 * @param key The key of the value to get.
	 * @return The resource bound to the key.
	 */
	Object getObject(String key);
}
