package org.i3xx.util.symbol.service.impl;

/*
 * #%L
 * NordApp OfficeBase :: uno
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


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.i3xx.util.symbol.service.model.SymbolFileLocationService;
import org.i3xx.util.symbol.service.model.SymbolService;
import org.osgi.framework.BundleContext;

public class SymbolServiceImpl implements SymbolService {
	
	/** The symbol counter */
	private static int main_counter = 0;
	
	/** The mapping of the symbol */
	private static Map<String, Integer> symbol_mapping = null;
	
	/** initializes the mapping */
	private static boolean init() {
		//use class lock
		synchronized( SymbolServiceImpl.class ) {
			if(symbol_mapping == null) {
				main_counter = 0;
				symbol_mapping = new LinkedHashMap<String, Integer>();
				
				return true;
			}
			return false;
		}
	}
	
	/** gets the number of the next mapping */
	private static Integer next(String symbol) {
		synchronized(symbol_mapping) {
			main_counter++;
			Integer c = new Integer(main_counter);
			symbol_mapping.put(symbol, c);
			
			return c;
		}
	}
	
	/** gets the number to the symbol */
	private static Integer get(String symbol) {
		synchronized(symbol_mapping) {
			return symbol_mapping.get(symbol);
		}
	}
	
	/** reads the map from the file */
	private static void read(File file) throws IOException {
		synchronized(symbol_mapping) {
			RandomAccessFile f = new RandomAccessFile(file, "r");
			try {
				long l = f.length();
				long p = f.getFilePointer();
				
				while(p<l) {
					int c = f.readInt();
					String s = f.readUTF();
					p = f.getFilePointer();
					
					symbol_mapping.put(s, new Integer(c));
				}
				main_counter = symbol_mapping.size();
			}finally{
				f.close();
			}
		}
	}
	
	/** writes the map to the file */
	private static void write(File file) throws IOException {
		synchronized(symbol_mapping) {
			RandomAccessFile f = new RandomAccessFile(file, "w");
			try {
				Iterator<String> i = symbol_mapping.keySet().iterator();
				while(i.hasNext()) {
					String s = i.next();
					Integer c = symbol_mapping.get(s);
					
					f.writeInt(c.intValue());
					f.writeUTF(s);
				}
				
			}finally{
				f.close();
			}
		}
	}
	
	/** writes an entry to the file */
	private static void append(File file, String symbol, Integer c) throws IOException {
		synchronized(symbol_mapping) {
			RandomAccessFile f = new RandomAccessFile(file, "rw");
			f.seek(f.length());
			try {
				f.writeInt(c.intValue());
				f.writeUTF(symbol);
		
			}finally{
				f.close();
			}
		}
	}
	
	/** The osgi bundle context */
	private BundleContext bundleContext;
	
	/** The SetupService to get the base data */
	private SymbolFileLocationService locationService;
	
	/** The file that backup the map */
	private File backup;
	
	/** Creates a new service */
	public SymbolServiceImpl() {
		bundleContext = null;
		locationService = null;
		backup = null;
	}
	
	/**
	 * The startup of the service
	 * @throws IOException 
	 */
	public void startUp() throws IOException {
		
		File file = locationService.getLocation();
		
		if( SymbolServiceImpl.init() )
			SymbolServiceImpl.read(file);
		
		backup = file;
	}
	
	/** gets the id to the symbol and stores the symbol if not stored yet.
	 * @param symbol The symbol */
	public int getSymbol(String symbol) throws IOException {
		Integer c = get(symbol);
		
		if(c==null) {
			c = next(symbol);
			append(backup, symbol, c);
		}
		
		return c.intValue();
	}
	
	/** stores all the data */
	public void backup() throws IOException {
		write(backup);
	}

	/**
	 * @return the bundleContext
	 */
	public BundleContext getBundleContext() {
		return bundleContext;
	}

	/**
	 * @param bundleContext the bundleContext to set
	 */
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * @return the locationService
	 */
	public SymbolFileLocationService getLocationService() {
		return locationService;
	}

	/**
	 * @param locationService the locationService to set
	 */
	public void setLocationService(SymbolFileLocationService locationService) {
		this.locationService = locationService;
	}

}
