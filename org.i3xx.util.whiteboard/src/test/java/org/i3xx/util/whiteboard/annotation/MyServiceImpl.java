package org.i3xx.util.whiteboard.annotation;

/*
 * #%L
 * NordApp OfficeBase :: Whiteboard
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


import java.util.HashMap;
import java.util.Map;

public class MyServiceImpl {
	
	private Map<String, String> store;
	
	public MyServiceImpl() {
		store = new HashMap<String, String>();
	}
	
	@BindIf(method="add")
	void test1(String key, String value) {
		store.put(key, value);
	}
	
	@BindIf(method="get")
	String test2(String key) {
		return store.get(key);
	}
	
	@BindIf(method="list")
	String[] test3() {
		return store.keySet().toArray(new String[store.size()]);
	}

}
