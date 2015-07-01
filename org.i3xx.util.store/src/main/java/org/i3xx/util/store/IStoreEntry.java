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


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

public interface IStoreEntry {
	
	/**
	 * Creates a new store entry
	 * 
	 * @param key The key of the store 
	 * @param lifetime The time to live until the entry exceed
	 * @param persistent True to mark an entry as persistent (not to be garbage collected).
	 * @param sort The sort object
	 * @return The store entry
	 */
	StoreEntry nextEntry(BigInteger key, long lifetime, boolean persistent, Object sort) throws IOException;
	
	/**
	 * Tests whether a store entry exists
	 * 
	 * @param key The key of a single store
	 * @param id The key of a entry
	 * @return True if the store entry exists, false otherwise.
	 */
	boolean existStoreEntry(BigInteger key, BigInteger id) throws IOException;

	/**
	 * Reorganizes a store entry.
	 * 
	 * @param key The key of the store.
	 * @param oldId The current id of the entry
	 * @param newId The new id of the entry
	 * @throws IOException
	 */
	void reorganizeStoreEntry(BigInteger key, BigInteger oldId, BigInteger newId) throws IOException;
	
	/**
	 * Cleanup (removes) a store entry and all it's content.
	 * 
	 * @param key The key of the store
	 * @param id The id of the entry
	 * @throws IOException
	 */
	void cleanupStoreEntry(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * Gets the store entry and returns an InputStream to read the content
	 * 
	 * @param key The key of the store
	 * @param id The id of the object
	 * @return The input stream
	 * @throws IOException
	 */
	InputStream read(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * Gets the store entry and return the content as a String.
	 * 
	 * @param key The key of the store
	 * @param id The id of the object
	 * @return The content as a String
	 * @throws IOException
	 */
	String readString(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * Creates a new file using a StoreEntry created outside the store
	 * It is recommended to use the nextEntry mechanism to create a
	 * store entry.
	 * 
	 * @param entry The store entry
	 * @return The file
	 * @throws IOException
	 */
	File getFile(StoreEntry entry) throws IOException;
	
	/**
	 * Gets an OutputStream to write to a store entry.
	 * 
	 * @param entry The store entry
	 * @return The output stream
	 * @throws IOException
	 */
	OutputStream write(StoreEntry entry) throws IOException;
	
	/**
	 * Write string data to a store.
	 * 
	 * @param data The data to write
	 * @param entry The store entry
	 * @throws IOException
	 */
	void writeString(String data, StoreEntry entry) throws IOException;
}
