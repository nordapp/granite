package org.i3xx.util.basic.io;

/*
 * #%L
 * NordApp OfficeBase :: util :: basic
 * %%
 * Copyright (C) 2015 I.D.S. DialogSysteme GmbH
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

/**
 * Using directory and file paths with the separator '/'
 * 
 * @author Stefan
 *
 */
public class FilePath {
	
	//The separator '/'
	private static final String sep = "/";
	
	//The separator '/'
	private static final char sepChar = '/';
	
	private final String path;
	
	public FilePath() {
		this.path = "";
	}

	public FilePath(String path) {
		this.path = normalize(path);
	}
	
	/**
	 * Gets a path object with the path appended.
	 * 
	 * @param path The path to append
	 * @return The FilePath of the path
	 */
	public FilePath add(String path) {
		StringBuffer buf = new StringBuffer(this.path);
		
		if( ! (this.path.endsWith(sep) || path.startsWith(sep)) )
			buf.append(sepChar);
		
		buf.append(path);
		
		return get( buf.toString() );
	}
	
	/**
	 * Gets a path object with the new path. If the current path ends
	 * with the given path, the given path is removed from the new path.
	 * If the current path doesn't end with path, this is returned. 
	 * 
	 * @param tail The tail to cut off.
	 * @return  The file path of the path
	 */
	public FilePath nibble(String tail) {
		
		//remove superfluous '/' from the end of tail
		tail = normalize(tail);
		
		//cut off tail
		if(this.path.endsWith(tail)) {
			return get( this.path.substring(0, this.path.length()-tail.length()) );
		}
		
		return this;
	}
	
	/**
	 * @return The path '/' a a path separator.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * @return A file object from the path
	 */
	public File toFile() {
		return new File( toString() );
	}
	
	/**
	 * @return The system dependent path (replace '/' by File.separatorChar)
	 */
	public String toString() {
		return path.replace('/', File.separatorChar);
	}
	
	/**
	 * Gets a path object of the path, replaces OS dependent path separator by '/'
	 * 
	 * @param path The path
	 * @return The FilePath of the path
	 */
	public static FilePath get(String path) {
		FilePath p = new FilePath(path.replace(File.separatorChar, '/'));
		return p;
	}
	
	
	/**
	 * Gets a path object of the path, replaces OS dependent path separator by '/'
	 * 
	 * @param path The path
	 * @return The FilePath of the path
	 */
	public static FilePath append(String... path) {
		FilePath p = get(path[0]);
		for(int i=1;i<path.length;i++)
			p = p.add(path[i]);
			
		return p;
	}
	
	/**
	 * Normalizes the path, this is to cut a trailing '/'
	 * 
	 * @param path The path
	 * @return The normalized path
	 */
	private static String normalize(String path) {
		if(path.endsWith(sep))
			path = path.substring(0, path.length()-1);
		
		return path;
	}
}
