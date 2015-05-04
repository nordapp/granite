package org.i3xx.test.workspace;

/*
 * #%L
 * workspace location
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


import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

public class WorkspaceTest extends TestCase {

	@Test
	public void test() throws IOException {
		
		String loc = Workspace.location();
		//
		assertNotNull(loc, "The location should not be null.");
		assertEquals( loc, "Unit::Test::String" );
	}
}
