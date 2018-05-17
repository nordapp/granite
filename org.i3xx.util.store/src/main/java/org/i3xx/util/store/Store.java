/*
 * Created on 02.08.2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
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


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.i3xx.util.basic.io.FilePath;
import org.i3xx.util.basic.core.ITimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The store policy deletes entries if the entry is running out of time (lifetime)
 * in both cases, GC and read. The read deletes every entry that is not persistent.
 * 
 * @author Stefan
 *
 */
public class Store implements ITimer, IControlStore, IStorePart, IStoreEntry {
	
	private static final Logger logger = LoggerFactory.getLogger(Store.class);
	
	private static final String BYTE_ENC = "utf-8";
	
	public final static String infodir = "info";
	
	protected final FilePath path;
	protected final Map<BigInteger, Map<BigInteger, StoreEntry>> trans;
	
	/**
	 * 
	 */
	public Store(String path) {
		super();
		
		if(path.startsWith("file://")){
			path = path.substring(7);
		}else{
			path = path.replace(File.separatorChar, '/');
		}
		
		logger.debug("Initialize the store at the path '{}'.", path);
		
		this.path = new FilePath(path);
		this.trans = new LinkedHashMap<BigInteger, Map<BigInteger, StoreEntry>>();
	}
	
	/**
	 * Loads the data from the info file to the global store.
	 * 
	 * @throws IOException
	 */
	public synchronized void loadData() throws IOException {
		loadData(trans);
	}
	
	/**
	 * Loads the data of a single store from the info file.
	 * 
	 * @param key The key of the store to load the data
	 * @throws IOException
	 */
	public synchronized void loadData(BigInteger key) throws IOException {
		readInfoFile(key, trans);
	}
	
	/**
	 * @param map The map to load the data from the info file to
	 * @throws IOException
	 */
	private void loadData(Map<BigInteger, Map<BigInteger, StoreEntry>> map) throws IOException {
		
		File info = path.add(infodir).toFile();
		if( !(info.exists() && info.isDirectory()) ) {
			
			logger.debug("Load data skipped because the file '{}' is missing.", info.getAbsolutePath());
			return;
		}
		logger.debug("Load data read {} entries from {}.", info.list().length,info.getAbsolutePath());
		
		for(String name : info.list()){
			BigInteger key = new BigInteger(name);
			readInfoFile(key, map);
		}
	}
	
	/**
	 * @param sKey The key of the store to read the info
	 * @param map The map to put the data to
	 */
	private void readInfoFile(BigInteger sKey, Map<BigInteger, Map<BigInteger, StoreEntry>> map) throws IOException {
		
		File file = iStore(sKey);
		if(!file.exists() || file.length()==0){
			
			logger.debug("Load data skipped because the file '{}' is missing.", file.getAbsolutePath());
			return;
		}
		logger.debug("Load data read entry from {} at '{}'.", sKey, file.getAbsolutePath());
		
		ObjectInputStream in = new ObjectInputStream(
				new FileInputStream(file));
		
		try{
			int count = 0;
			for(;;){
				try {
					StoreEntry entry = (StoreEntry)in.readObject();
					
					BigInteger key = entry.getTrans();
					Map<BigInteger, StoreEntry> m = map.get(key);
					
					if(m == null){
						m = new LinkedHashMap<BigInteger, StoreEntry>();
						map.put(key, m);
					}
					
					m.put(entry.getId(), entry);
				} catch (EOFException ee) {
					//end of stream reached
					logger.debug("Load data reads {} entries from the file '{}'.", count, file.getAbsolutePath());
					break;
				} catch (OptionalDataException ee) {
					reportError(ee);
				} catch (IOException ee) {
					reportError(ee, "The file '"+file.getName()+"' has an invalid entry ("+ee.getMessage()+").");
				} catch (ClassNotFoundException ee) {
					reportError(ee);
				} catch (Exception ee) {
					reportError(ee);
				}
				count++;
			}
		}catch(IOException e){
			throw e;
		}finally{
			in.close();
		}
	}
	
	/**
	 * Writes the data from a store to the info file
	 * 
	 * @param key The key of the store to write
	 * @throws IOException
	 */
	private void writeInfoFile(BigInteger key, Map<BigInteger, Map<BigInteger, StoreEntry>> map) throws IOException {
		
		File file = iStore(key);
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream( file ));
		
		int count = 0;
		try{
			Map<BigInteger, StoreEntry> store = map.get(key);
			for(BigInteger id:store.keySet()){
				StoreEntry entry = store.get(id);
				out.writeObject(entry);
				
				count++;
			}
			
			out.flush();
		}catch(IOException e){
			reportError(e);
		}catch(Exception e){
			reportError(e);
		}finally{
			logger.debug("Write data writes {} entries to the file '{}'.", count, file.getAbsolutePath());
			out.close();
		}
	}
	
	/**
	 * @throws IOException
	 */
	public synchronized void unloadData() throws IOException {
		unloadData(trans);
	}
	
	/**
	 * @param key The key of the store to write
	 * @throws IOException
	 */
	public synchronized void unloadData(BigInteger key) throws IOException {
		writeInfoFile(key, trans);
	}
	
	/**
	 * @param map The global store map to write to the info file
	 * 
	 * @throws IOException
	 */
	private void unloadData(Map<BigInteger, Map<BigInteger, StoreEntry>> map) throws IOException {
		
		try{
			if(map.isEmpty()){
				logger.debug("Write data writes no entry to the file.");
			}
			for(BigInteger key:map.keySet()){
				writeInfoFile(key, map);
			}
		}catch(IOException e){
			reportError(e);
		}catch(Exception e){
			reportError(e);
		}
	}
	
	/**
	 * @param file The file to print the data to
	 * @throws IOException
	 */
	public synchronized void printData(File file) throws IOException {
		
		PrintStream out = new PrintStream(
				new FileOutputStream(file));
		
		try{
			for(BigInteger key : trans.keySet()){
				Map<BigInteger, StoreEntry> store = trans.get(key);
				for(BigInteger id:store.keySet()){
					StoreEntry entry = store.get(id);
					
					out.print(entry.getTrans());
					out.print(", ");
					out.print(entry.getId());
					out.print(", ");
					out.print(entry.getLifetime());
					out.print(", ");
					out.print(entry.isPersistent());
					out.print(", ");
					out.print(entry.getSort());
					out.println("");
				}
			}
			
			out.flush();
		}catch(Exception e){
			reportError(e);
		}finally{
			out.close();
		}
	}
	
	/**
	 * This is a kind of merge (the store could be updated by another store instance.
	 * 
	 * @throws IOException
	 */
	public synchronized void mergeData() throws IOException {
		Map<BigInteger, Map<BigInteger, StoreEntry>> re = new LinkedHashMap<BigInteger, Map<BigInteger, StoreEntry>>();
		loadData(re);
		copyData(re, trans);
		verify();
		unloadData(trans);
	}
	
	/**
	 * Copies every missing StoreEntry from mapA to mapB
	 * 
	 * @param mapA The source map (global store)
	 * @param mapB The destination map (global store)
	 * @throws IOException
	 */
	private void copyData(Map<BigInteger, Map<BigInteger, StoreEntry>> mapA, Map<BigInteger, Map<BigInteger, StoreEntry>> mapB) throws IOException {
		for(BigInteger keyA:mapA.keySet()){
			Map<BigInteger, StoreEntry> storeA = mapA.get(keyA);
			Map<BigInteger, StoreEntry> storeB = mapB.get(keyA);
			if(storeB == null){
				storeB = new LinkedHashMap<BigInteger, StoreEntry>();
				mapB.put(keyA, storeB);
			}
			for(BigInteger idA:storeA.keySet()){
				if(storeB.containsKey(idA)){
					//does nothing (ok)
				}else{
					StoreEntry entry = storeA.get(idA);
					storeB.put(idA, entry);
				}
			}
		}
	}
	
	/**
	 * Verifies a store
	 * 
	 * @throws IOException
	 */
	public synchronized void verify() throws IOException {
		
		for(BigInteger key:trans.keySet()){
			verifyStore(key);
		}
	}
	
	/**
	 * Runs the garbage collector on the global store.
	 * 
	 * @throws IOException
	 */
	public synchronized void gc() throws IOException {
		
		Vector<StoreEntry> v = new Vector<StoreEntry>();
		
		//collect
		for(BigInteger key:trans.keySet()){
			Map<BigInteger, StoreEntry> store = trans.get(key);
			for(BigInteger id:store.keySet()){
				StoreEntry entry = store.get(id);
				
				if( entry.getLifetime() > System.currentTimeMillis() ){
					logger.debug("{} - {} : {}", entry.getLifetime(),System.currentTimeMillis(),(entry.getLifetime()-System.currentTimeMillis()));
					//does nothing
				}else{
					v.add(entry);
				}
			}
		}
		
		//remove
		logger.debug("The store cleans {} entries.", v.size());
		for(StoreEntry entry:v){
			try{
				cleanupStoreEntry(entry.getTrans(), entry.getId());
			}catch(IOException e){}
		}
		
		//
		gcEmptyInffiles();		
	}
	
	/**
	 * Cleanup the store and deletes everything with the exception
	 * of the root directory.
	 * 
	 * @throws IOException
	 */
	public synchronized void cleanup() throws IOException {
		trans.clear();
		cleanupDirectory(path.toFile(), false);
		cleanupInffiles();
	}
	
	/**
	 * Tests whether a store exists
	 * 
	 * @param key The key of a single store
	 * @return True if the store exists, false otherwise.
	 */
	public synchronized boolean existStore(BigInteger key) {
		return trans.containsKey(key);
	}
	
	/**
	 * Tests whether a store entry exists
	 * 
	 * @param key The key of a single store
	 * @param id The key of a entry
	 * @return True if the store entry exists, false otherwise.
	 */
	public synchronized boolean existStoreEntry(BigInteger key, BigInteger id) throws IOException {
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		StoreEntry o = store.get(id);
		
		return (o != null);
	}
	
	/**
	 * Creates a new store
	 * 
	 * @return The key id of the new store
	 * @throws IOException
	 */
	public synchronized BigInteger createStore() throws IOException {
		BigInteger key = null;
		
		for(long i=0; i<=trans.size(); i++){
			if( ! trans.containsKey(BigInteger.valueOf(i)) ) {
				key = BigInteger.valueOf(i);
				break;
			}
		}
		
		return createStore(key);
	}
	
	/**
	 * @return A list of all store keys available in the global store.
	 */
	public synchronized List<BigInteger> getStoreKeys() {
		List<BigInteger> list = new ArrayList<BigInteger>();
		list.addAll(trans.keySet());
		
		return list;
	}
	
	/**
	 * Creates a new store with the key if the store doesn't exists.
	 * 
	 * @param key The key of the store
	 * @return The key of the store
	 * @throws IOException
	 */
	public synchronized BigInteger createStore(BigInteger key) throws IOException {
		if(key == null)
			throw new IOException("Invalid store key 'null'.");
		
		if( trans.containsKey(key) )
			return key;
		
		Map<BigInteger, StoreEntry> m = new LinkedHashMap<BigInteger, StoreEntry>();
		trans.put(key, m);
		
		ensureInffile(key);
		ensureDirectory(key);
		
		logger.debug("The store {} has been successfully created.", key);
		
		return key;
	}
	
	/**
	 * Returns a map containing all entries of the store. The sort object as a key
	 * and the id of the entry as a value.
	 * 
	 * @param key The key of the store
	 * @return The map
	 * @throws IOException
	 */
	public synchronized Map<Object, BigInteger> getSortMap(BigInteger key) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		Map<Object, BigInteger> result = new TreeMap<Object, BigInteger>();
		
		//collect entries
		for(StoreEntry entry:store.values()){
			result.put(entry.getSort(), entry.getId());
		}
		
		return result;
	}
	
	/**
	 * @param key The key of the store
	 * @return The list of all id's available in the store.
	 * @throws IOException
	 */
	public synchronized List<BigInteger> getStoreListing(BigInteger key) throws IOException {

		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		List<BigInteger> result = new LinkedList<BigInteger>();
		
		//collect entries
		for(BigInteger id:store.keySet()){
			result.add(id);
		}
		
		return result;
	}
	
	/**
	 * Reorganizes a store.
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	public synchronized void reorganizeStore(BigInteger key) throws IOException {
		reorganizeStore(key, getSortMap(key));
	}
	
	/**
	 * Reorganizes a store.
	 * 
	 * @param key The key of the store.
	 * @param order as Object-ID-map 
	 * @throws IOException
	 */
	public synchronized void reorganizeStore(BigInteger key, Map<Object, BigInteger> order) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		//do not reorganize an empty store (there may be no directory - do not create one)
		if(store.size()==0)
			return;
		
		Iterator<Object> iter = order.keySet().iterator();
		int index = 0;
		BigInteger[] oldId = new BigInteger[order.size()];
		BigInteger[] newId = new BigInteger[order.size()];
		
		//create list
		while(iter.hasNext()) {
			Object ikey = iter.next();
			BigInteger id = order.get(ikey);
			oldId[index] = id;
			newId[index] = id;
			
			index++;
		}
		
		//free space to avoid conflicts
		for(int i=0; i<oldId.length; i++) {
			if( existStoreEntry(key, newId[i]) ) {
				BigInteger tmp = getFreeFile(key);
				//newId is the really oldId ;-)
				reorganizeStoreEntry(key, newId[i], tmp);
				oldId[i] = tmp;
			}
		}
		
		//sort
		for(int i=0; i<oldId.length; i++) {
			reorganizeStoreEntry(key, oldId[i], newId[i]);
		}	
	}
	
	/**
	 * Reorganizes a store entry.
	 * 
	 * @param key The key of the store.
	 * @param oldId The current id of the entry
	 * @param newId The new id of the entry
	 * @throws IOException
	 */
	public synchronized void reorganizeStoreEntry(BigInteger key, BigInteger oldId, BigInteger newId) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		StoreEntry entry = store.get(oldId);
		if(entry == null)
			throw new IOException("The entry does't exist ("+key.toString()+"/"+oldId.toString()+").");
		
		entry.setId(newId);
		store.remove(oldId);
		store.put(entry.getId(), entry);
		
		//String pstore = path+File.separator+key.toString();
		//String fstore = pstore+File.separator+oldId.toString();
		//String nstore = pstore+File.separator+newId.toString();
		
		//File file = new File(fstore);
		//if(file.exists())
		//	file.renameTo(new File(nstore));
		
		File file = fStore(key, oldId);
		if(file.exists())
			file.renameTo(fStore(key, newId));
	}
	
	/**
	 * Verifies the store and remove outdated or lost files.
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	public synchronized void verifyStore(BigInteger key) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		
		//String pstore = path+File.separator+key.toString();
		//File dir = new File(pstore);
		File dir = pStore(key);
		
		//cleanup empty stores
		if(store == null){
			cleanupInffile(key);
			cleanupDirectory(dir, true);
			return;
		}
		
		//test physical files
		if(dir.exists()) {
			for(File file:dir.listFiles()){
				//skip and ignore a directory
				if(file.isDirectory())
					continue;
				
				//test file
				try{
					BigInteger id = new BigInteger(file.getName());
					if( ! store.containsKey(id) )
						killFile(file);
				}catch(NumberFormatException e){ /*does nothing*/ }
			}
		}
		
		//test entries
		Iterator<BigInteger> iter = store.keySet().iterator();
		while(iter.hasNext()){
			BigInteger id = iter.next();
			//StoreEntry entry = (StoreEntry)store.get(id);
			
			//String fstore = pstore+File.separator+id.toString();
			//File file = new File(fstore);
			File file = fStore(dir, id);
			if( ! file.exists())
				iter.remove();
		}
	}
	
	/**
	 * Cleanup a store
	 * 
	 * @param key The key of the store.
	 * @throws IOException
	 */
	public synchronized void cleanupStore(BigInteger key) throws IOException {
		trans.remove(key);
		cleanupInffile(key);
		cleanupDirectory( path.add(key.toString()).toFile(), true);
	}
	
	/**
	 * Creates a new store entry
	 * 
	 * @param key The key of the store 
	 * @param lifetime The time to live until the entry exceed
	 * @param persistent True to mark an entry as persistent (not to be garbage collected).
	 * @param sort The sort object
	 * @return The store entry
	 */
	public synchronized StoreEntry nextEntry(BigInteger key, long lifetime, boolean persistent, Object sort) throws IOException {
		
		ensureInffile(key);
		ensureDirectory(key);
		
		StoreEntry result = new StoreEntry();
		result.setId( getFreeFile(key) );
		result.setTrans(key);
		result.addLifetime( lifetime );
		result.setPersistent(persistent);
		result.setSort(sort);
		
		logger.debug("The {} has been successfully created.", result.toString());
		
		return result;
	}
	
	/**
	 * Gets the store entry and returns an InputStream to read
	 * 
	 * @param key The key of the store
	 * @param id The id of the object
	 * @return The input stream
	 * @throws IOException
	 */
	public synchronized InputStream read(BigInteger key, BigInteger id) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		StoreEntry entry = store.get(id);
		if(entry == null)
			throw new IOException("The entry does't exist ("+key.toString()+"/"+id.toString()+").");
		
		//
		// Note: MyInputStream may clear the entry at close.
		//
		
		//String pstore = path+File.separator+key.toString();
		//String fstore = pstore+File.separator+id.toString();
		//File file = new File(fstore);
		File file = fStore(key, id);
		if(file.exists())
			return new MyInputStream(new FileInputStream(file), entry);
		
		throw new IOException("The store "+key.toString()+" has no entry "+id.toString());
	}
	
	/**
	 * Gets the store entry and return the content as a String.
	 * 
	 * @param key The key of the store
	 * @param id The id of the object
	 * @return The content as a String
	 * @throws IOException
	 */
	public synchronized String readString(BigInteger key, BigInteger id) throws IOException {
		
		//
		// Note MyInputStream may clear the entry at close.
		//
		
		InputStreamReader ir = new InputStreamReader(read(key, id), BYTE_ENC);
		StringWriter out = new StringWriter();
		
		int c = 0;
		char[] buf = new char[1024];
		while((c=ir.read(buf))>-1)
			out.write(buf, 0, c);
		
		ir.close();
		out.flush();
		out.close();
		
		return out.toString();
	}
	
	/**
	 * Returns a file object that represents a store. Note that a store can move
	 * while reorganizing the store. Do not persist the file or it's data.
	 * The directory file is used to export store entries.
	 * 
	 * @param key The key of the store.
	 * @return The file
	 * @throws IOException
	 */
	public synchronized File getFile(BigInteger key) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		return pStore(key);
	}
	
	/**
	 * Returns a file object that represents a store entry. Note that this file
	 * can move while reorganizing the store. Do not persist the file or it's data.
	 * 
	 * @param key The key of the store.
	 * @param id The id of the store entry
	 * @return The file
	 * @throws IOException
	 */
	public synchronized File getFile(BigInteger key, BigInteger id) throws IOException {
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		StoreEntry entry = store.get(id);
		if(entry == null)
			throw new IOException("The entry does't exist ("+key.toString()+"/"+id.toString()+").");
		
		//String pstore = path+File.separator+key.toString();
		//String fstore = pstore+File.separator+id.toString();
		//File file = new File(fstore);
		File file = fStore(key, id);
		if(file.exists())
			return file;
		
		throw new IOException("The store "+key.toString()+" has no entry "+id.toString());
	}
	
	/**
	 * Creates a new file using a StoreEntry created outside the store
	 * It is recommended to use the nextEntry mechanism to create a
	 * store entry.
	 * 
	 * @param entry The store entry
	 * @return The file
	 * @throws IOException
	 */
	public synchronized File getFile(StoreEntry entry) throws IOException {
		
		BigInteger key = entry.getTrans();
		BigInteger id = entry.getId();
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		//@removed, to offer the update of a store entry.
		//sh, 12.01.2011
		//if(store.containsKey(id))
		//	throw new DoubleKeyException("Double key in store "+key.toString()+" for "+id.toString());
		
		store.put(id, entry);
		
		ensureInffile(key);
		File dir = ensureDirectory(key);
		
		return new File(dir, id.toString());
	}
	
	/**
	 * Gets an OutputStream to write to a store entry.
	 * 
	 * @param entry The store entry
	 * @return The output stream
	 * @throws IOException
	 */
	public synchronized OutputStream write(StoreEntry entry) throws IOException {
		return new FileOutputStream( getFile(entry));
	}
	
	/**
	 * Write string data to a store.
	 * 
	 * @param data The data to write
	 * @param entry The store entry
	 * @throws IOException
	 */
	public synchronized void writeString(String data, StoreEntry entry) throws IOException {
		OutputStream out = write(entry);
		OutputStreamWriter wr = new OutputStreamWriter(out, BYTE_ENC);
		wr.write(data);
		wr.flush();
		wr.close();
	}
	
	/**
	 * @param key The key of the store
	 * @param id The id of the entry
	 * @throws IOException
	 */
	public synchronized void cleanupStoreEntry(BigInteger key, BigInteger id) throws IOException {
		
		Map<BigInteger, StoreEntry> store = trans.get(key);
		if(store == null)
			throw new IOException("The store does't exist ("+key.toString()+").");
		
		StoreEntry entry = store.get(id);
		if(entry == null)
			throw new IOException("The entry does't exist ("+key.toString()+"/"+id.toString()+").");
		
		store.remove(id);
	
		//String pstore = path+File.separator+key.toString();
		//String fstore = pstore+File.separator+id.toString();
		File dir = pStore(key);
		File fstore = fStore(dir, id);
		killFile(fstore);
		
		if(store.size() == 0){
			cleanupInffile(key);
			ensureInffile(key);
			cleanupDirectory(dir, true);
		}
	}
	
	/**
	 * Creates the inffile if not available
	 * 
	 * @param key The key of the store
	 * @return The new inffile
	 * @throws IOException
	 */
	private File ensureInffile(BigInteger key) throws IOException {
		File file = iStore(key);
		file.getParentFile().mkdirs();
		
		if( ! file.exists())
			file.createNewFile();
		
		return file;
	}
	
	/**
	 * Creates the directory if not available
	 * 
	 * @param key The key of the store
	 * @return The directory of the store (key)
	 * @throws IOException
	 */
	private File ensureDirectory(BigInteger key) throws IOException {
		File dir = pStore(key);
		if( ! dir.exists())
			dir.mkdirs();
		
		return dir;
	}
	
	/**
	 * @param key The key of the store
	 */
	private void cleanupInffile(BigInteger key) {
		killFile( iStore(key) );
	}
	
	/**
	 * @throws IOException
	 */
	private void cleanupInffiles() throws IOException {
		//cleanup file info
		cleanupDirectory(path.add(infodir).toFile(), false);
	}
	
	/**
	 * Deletes the given directory and all subdirectories
	 * 
	 * @param dir The directory to delete with all files and subdirectories.
	 * @param delete True to delete the directory itself, false otherwise.
	 * @throws IOException
	 */
	private void cleanupDirectory(File dir, boolean delete) throws IOException {
		if(dir.exists() && dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0; i<files.length; i++){
				if(files[i].isFile()){
					killFile(files[i]);
				}else{
					cleanupDirectory(files[i], true);
				}
			}
			
			if(delete)
				dir.delete();
		}
	}
	
	/**
	 * @throws IOException
	 */
	private void gcEmptyInffiles() throws IOException {
		File dir = path.add(infodir).toFile();
		if(dir.exists() && dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0; i<files.length; i++){
				if(files[i].isFile() && files[i].length()==0){
					killFile(files[i]);
				}
			}
		}
	}
	
	/**
	 * @param file The file to kill
	 */
	private void killFile(File file) {
		if(file.exists())
			file.delete();
		
		if(file.exists())
			file.deleteOnExit();
	}
	
	/**
	 * Returns the root directory of the store
	 * 
	 * @param key The key of the store
	 * @return The root directory of the store
	 */
	private File pStore(BigInteger key) {
		return path.add(key.toString()).toFile();
	}
	
	/**
	 * Returns the file object of the info file
	 * 
	 * @param key The key of the store 
	 * @return The file object of the info file
	 */
	private File iStore(BigInteger key) {
		return path.add(infodir).add(key.toString()).toFile();
	}
	
	/**
	 * Returns the file object of the store entry
	 * 
	 * @param dir The store directory (pStore)
	 * @param id The id of the store entry
	 * @return The file object of the store entry
	 */
	private File fStore(File dir, BigInteger id) {
		return new File(dir, id.toString());
	}
	
	/**
	 * Returns the file object of the store entry
	 * 
	 * @param key The key of the store
	 * @param id The id of the store entry
	 * @return The file object of the store entry
	 */
	private File fStore(BigInteger key, BigInteger id) {
		return fStore(pStore(key), id);
	}
	
	/**
	 * @param key The key of the store
	 * @return The next free file id
	 * @throws IOException
	 */
	private BigInteger getFreeFile(BigInteger key) throws IOException {
		File dir = pStore(key);
		long last = 0;
		
		//block the class
		synchronized(getClass()) {
			String[] list = dir.list();
			
			if(list == null){
				//create tombstone to block the file number
				new File(dir, "1").createNewFile();
				return BigInteger.ONE;
			}
			
			for(int i=0; i<list.length; i++) {
				long n = Long.parseLong(list[i]);
				if(n > last)
					last = n;
			}
			BigInteger id = BigInteger.valueOf(++last); //increment
			
			//create tombstone to block the file number
			fStore(dir, id).createNewFile();
			return id;
		}
	}
	
	/**
	 * @author Administrator
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	private class MyInputStream extends InputStream
	{
		private InputStream in;
		private StoreEntry entry;

		public MyInputStream(InputStream i, StoreEntry e) throws IOException
		{
			super();
			
			in =  i;
			entry = e;
		}

		public int read() throws IOException
		{
			return in.read();
		}

		public int read(byte[] buf) throws IOException
		{
			return in.read(buf);
		}

		public int read(byte[] buf, int off, int len) throws IOException
		{
			return in.read(buf, off, len);
		}

		public void close() throws IOException
		{
			in.close();
			
			if( (! entry.isPersistent()) || 
					(entry.getLifetime() < System.currentTimeMillis()) ) {
				
				cleanupStoreEntry( entry.getTrans(), entry.getId() );
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.i3xx.util.core.ITimer#timerNotify()
	 */
	public void timerNotify() {
		try {
			gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param e
	 * @throws IOException
	 */
	private void reportError(Exception e) throws IOException {
		
		logger.debug("An exception occurs.", e);
		
		IOException io = new IOException( e.getMessage() );
		io.initCause(e);
		
		throw io;
	}
	
	/**
	 * @param e
	 * @param msg
	 * @throws IOException
	 */
	private void reportError(Exception e, String msg) throws IOException {
		
		logger.debug("An exception occurs. {}", msg, e);
		
		IOException io = new IOException( msg );
		io.initCause(e);
		
		throw io;
	}
}
