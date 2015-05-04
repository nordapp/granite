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


import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AnnotationF1Test {
	
	static MyServiceImpl service = null;
	
	@BeforeClass
	public static void setUp() throws Exception {
		service = new MyServiceImpl();
	}

	@AfterClass
	public static void tearDown() throws Exception {
		service = null;
	}

	@Test
	public void testA() {
		
		MyInterface mi = new MyInterfaceImpl(service);
		mi.add("key-1", "Some text");
		mi.add("key-2", "More text");
		String[] list = mi.list();
		
		assertEquals("Some text", mi.get("key-1"));
		assertEquals(2, list.length);
	}

	@Test
	public void testB() {
		
		MyInterface mi = new MyInterfaceImpl(service);
		String[] list = mi.list();
		
		assertEquals(2, list.length);
	}

}
