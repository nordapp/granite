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

public interface IControlStore {
	
	/**
	 * Creates a new Store
	 * 
	 * @return The store uid (transaction id)
	 * @throws IOException
	 */
	BigInteger createStore() throws IOException;
	
	/**
	 * Get a list of all available store keys
	 * @return
	 */
	List<BigInteger> getStoreKeys();
	
	/**
	 * Loads the data (index files) into the store
	 * @throws IOException
	 */
	void loadData() throws IOException;
	
	/**
	 * Merges the data of the store to the data of the directory
	 * (similar to a transaction commit)
	 * 
	 * @throws IOException
	 */
	void mergeData() throws IOException;
	
	/**
	 * Writes the data to the index files
	 * @throws IOException
	 */
	void unloadData() throws IOException;
	
	/**
	 * Verifies the data of the store. Ensure valid files are available in the
	 * store and delete files that are marked as unused. 
	 * @throws IOException
	 */
	void verify() throws IOException;
	
	/**
	 * Runs the garbage collector to free unused resources
	 * @throws IOException
	 */
	void gc() throws IOException;
	
	/**
	 * Cleans the store and removes all data (files, directories)
	 * @throws IOException
	 */
	void cleanup() throws IOException;
}
