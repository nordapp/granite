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
import java.net.URISyntaxException;

public interface IStoreData {
	
	/**
	 * Creates a new store entry
     * @param key The id of the transaction (or session)
     * @param id The id of the brick (or a similar number)
     * @param lifetime Time to live in millis from now
     * @param persistent If false the element will be destroyed after the first read or during the next cleanup
     * @param sort An object to sort the list (getStoreListing)
     * @throws IOException
	 */
	void newEntry(BigInteger key, BigInteger id, long lifetime, boolean persistent, String sort) throws IOException;
	
    /**
     * Creates a new entry with the next id
     * @param key The id of the transaction (or session)
     * @param lifetime Time to live in millis from now
     * @param persistent If false the element will be destroyed after the first read or during the next cleanup
     * @param sort An object to sort the list (getStoreListing)
     * @throws IOException
     */
	void nextEntry(BigInteger key, long lifetime, boolean persistent, String sort) throws IOException;
	
	/**
	 * Resets the current store entry
	 */
	void resetCurrentEntry();
	
	/**
	 * Reads a resource from the store and returns an InputStream
	 * @param key
	 * @param id
	 * @return
	 * @throws IOException
	 */
	InputStream read(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * Reads a resource from the store into a string
	 * @param key
	 * @param id
	 * @return
	 * @throws IOException
	 */
	String readString(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * Exports the resource of the store to an extern file
	 * @param fileURL
	 * @param key
	 * @param id
	 * @throws Exception
	 */
	void exportFile(String fileURL, String key, String id) throws IOException, URISyntaxException;
	
	/**
	 * Gets the file of the resource of the store
	 * @return
	 * @throws IOException
	 */
	File getFile() throws IOException;
	
	/**
	 * Writes the content of the stream to the resource of the store
	 * @param entry
	 * @return
	 * @throws IOException
	 */
	OutputStream write() throws IOException;
	
	/**
	 * Writes the content of the string to the resource of the store
	 * @param data
	 * @param entry
	 * @throws IOException
	 */
	void writeString(String data) throws IOException;
	
	/**
	 * Imports an external file as resource into the store
	 * @param fileURL
	 * @throws Exception
	 */
	void importFile(String fileURL) throws IOException, URISyntaxException;
}
