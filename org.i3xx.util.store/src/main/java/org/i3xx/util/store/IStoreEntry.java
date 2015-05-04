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
	 * @param key
	 * @param lifetime
	 * @param persistent
	 * @param sort
	 * @return
	 * @throws IOException
	 */
	StoreEntry nextEntry(BigInteger key, long lifetime, boolean persistent, Object sort) throws IOException;
	
	/**
	 * @param key
	 * @param id
	 * @return
	 * @throws IOException
	 */
	boolean existStoreEntry(BigInteger key, BigInteger id) throws IOException;

	/**
	 * @param key
	 * @param oldId
	 * @param newId
	 * @throws IOException
	 */
	void reorganizeStoreEntry(BigInteger key, BigInteger oldId, BigInteger newId) throws IOException;
	
	/**
	 * @param key
	 * @param id
	 * @throws IOException
	 */
	void cleanupStoreEntry(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * @param key
	 * @param id
	 * @return
	 * @throws IOException
	 */
	InputStream read(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * @param key
	 * @param id
	 * @return
	 * @throws IOException
	 */
	String readString(BigInteger key, BigInteger id) throws IOException;
	
	/**
	 * @param entry
	 * @return
	 * @throws IOException
	 */
	File getFile(StoreEntry entry) throws IOException;
	
	/**
	 * @param entry
	 * @return
	 * @throws IOException
	 */
	OutputStream write(StoreEntry entry) throws IOException;
	
	/**
	 * @param data
	 * @param entry
	 * @throws IOException
	 */
	void writeString(String data, StoreEntry entry) throws IOException;
}
