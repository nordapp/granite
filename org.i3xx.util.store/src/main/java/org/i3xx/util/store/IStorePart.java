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


import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface IStorePart {
	
	/**
	 * Creates a new store
	 * 
	 * @return The key id of the new store
	 * @throws IOException
	 */
	BigInteger createStore(BigInteger key) throws IOException;

	/**
	 * Tests whether a store exists
	 * 
	 * @param key The key of a single store
	 * @return True if the store exists, false otherwise.
	 */
	boolean existStore(BigInteger key);
	
	/**
	 * Returns a map containing all entries of the store. The sort object as a key
	 * and the id of the entry as a value.
	 * 
	 * @param key The key of the store
	 * @return The map
	 * @throws IOException
	 */
	Map<Object, BigInteger> getSortMap(BigInteger key) throws IOException;
	
	/**
	 * @param key The key of the store.
	 * @return The list of id's
	 * @throws IOException
	 */
	List<BigInteger> getStoreListing(BigInteger key) throws IOException;
	
	/**
	 * Reorganizes a store.
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	void reorganizeStore(BigInteger key) throws IOException;
	
	/**
	 * @param key
	 * @param order
	 * @throws IOException
	 */
	void reorganizeStore(BigInteger key, Map<Object, BigInteger> order) throws IOException;
	
	/**
	 * Verifies the store and remove outdated or lost files.
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	void verifyStore(BigInteger key) throws IOException;
	
	/**
	 * Cleanup a store
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	void cleanupStore(BigInteger key) throws IOException;
}
