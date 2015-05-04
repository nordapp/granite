package org.i3xx.util.store;

/*
 * #%L
 * NordApp OfficeBase :: util :: store
 * %%
 * Copyright (C) 2013 - 2015 I.D.S. DialogSysteme GmbH
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

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.PropertyConfigurator;
import org.i3xx.test.workspace.Workspace;
import org.i3xx.util.basic.io.FilePath;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class T01FunctionTest {
	
	static FilePath RES_LOC = null;
	
	@Before
	public void setUp() throws Exception {
		//
		String loc = Workspace.location().replace(File.separatorChar, '/');
		RES_LOC = FilePath.get(loc).add("/src/test/resources");
		
		//
		String logcfg = RES_LOC.add("properties/Log4j.properties").toString();
		
		//Log4j configuration and setup
		PropertyConfigurator.configure(logcfg);
		
	}
	
	@Test
	public void createData() throws IOException {
		String home = RES_LOC.add("store/sdb").getPath();
		
		Store store = new Store("file://"+home);
		store.loadData();
		store.gc();
		
		BigInteger key = store.createStore(BigInteger.valueOf(0));
		
		for(int i=0;i<100;i++){
			StoreEntry entry = store.nextEntry(key, 1000, false, null);
			store.writeString("Test-", entry);
		}
		
		store.unloadData();
		store.gc();
		
		//do not run the test asynchronous, use the annotation Ignore.
		testData();
	}
	
	@Ignore
	public void testData() throws IOException {
		//try {
		//	Thread.sleep(50);
		//} catch (InterruptedException e) {}
		
		String home = RES_LOC.add("store/sdb").getPath();
		
		Store store = new Store("file://"+home);
		store.loadData();
		
		List<BigInteger> list = store.getStoreKeys();
		if(list.isEmpty())
			Assert.fail("Unable to test an empty store. An unexpected cleanup destroys the data.");
		
		BigInteger key = list.get(0);
		
		List<BigInteger> elems = store.getStoreListing( key );
		
		StringBuffer buf = new StringBuffer();
		
		for(BigInteger bi : elems) {
			buf.append( store.readString(key, bi) );
		}
		
		//'Test-' has 5 chars; 100 x 5 = 500
		//All write, all read has 500 chars
		assertEquals( buf.length(), 500 );
		
		//clears the store if necessary (deletes the empty info file)
		store.cleanupStore(key);
		//unload remaining data (no remaining data in this test)
		store.unloadData();
		//runs the gc (nothing to clean in this test))
		store.gc();
		
	}

}
