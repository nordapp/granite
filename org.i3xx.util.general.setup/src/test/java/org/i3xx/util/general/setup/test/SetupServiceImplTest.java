package org.i3xx.util.general.setup.test;

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


import static org.junit.Assert.*;

import org.i3xx.util.general.setup.impl.Setup;
import org.junit.Test;

public class SetupServiceImplTest {

	@Test
	public void test() {
		
		assertEquals( "ID", Setup.setCurrentId(".", "ID") );
		assertEquals( "Title - ID", Setup.setCurrentId("Title - .", "ID") );
		assertEquals( "./var/ID", Setup.setCurrentId("\\./var/.", "ID") );
		assertEquals( "./var/XX", Setup.setCurrentId("\\./var/XX", "ID") );
		assertEquals( "Title - ID.", Setup.setCurrentId("Title - .\\.", "ID") );
		
		//fail("Not yet implemented"); // TODO
	}

}
