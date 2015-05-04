/*
 * Created on 30.03.2005
 */
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * @author S. Hauptmann
 */
public class CURL {

	/**
	 * 
	 */
	public CURL() {
		super();
	}
	
	/**
	 * @param urlString
	 * @return
	 * @throws URISyntaxException
	 * 
	 * Thanks to Christian Fries, Simone Giannis
	 * @see http://stackoverflow.com/questions/18520972/converting-java-file-url-to-file-path-platform-independent-including-u
	 */
	public static String fileURLtoFilename(String urlString) throws URISyntaxException {
		if(urlString == null)
			return "";
		
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new URISyntaxException(urlString, e.toString());
		}
		URI uri = url.toURI();
		if(uri.getAuthority() != null && uri.getAuthority().length() > 0) {
			// Hack for UNC Path
			try {
				uri = (new URL("file://" + urlString.substring("file:".length()))).toURI();
			} catch (MalformedURLException e) {
				throw new URISyntaxException(urlString, e.toString());
			}
        }
		File file = new File(uri);
		return file.getAbsolutePath();
	}

	/**
	 * @param urlString
	 * @return
	 * @throws URISyntaxException
	 * 
	 * Thanks to Christian Fries, Simone Giannis
	 * @see http://stackoverflow.com/questions/18520972/converting-java-file-url-to-file-path-platform-independent-including-u
	 */
	public static File fileURLtoFile(String urlString) throws URISyntaxException {
		if(urlString == null)
			return new File("");
		
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			throw new URISyntaxException(urlString, e.toString());
		}
		URI uri = url.toURI();
		if(uri.getAuthority() != null && uri.getAuthority().length() > 0) {
			// Hack for UNC Path
			try {
				uri = (new URL("file://" + urlString.substring("file:".length()))).toURI();
			} catch (MalformedURLException e) {
				throw new URISyntaxException(urlString, e.toString());
			}
        }
		File file = new File(uri);
		return file;
	}
	
	/**
	 * @param filename
	 * @return
	 * @throws URISyntaxException
	 */
	public static String fileURLfromFilename(String filename) throws URISyntaxException {
		return fileURLfromFile( new File(filename) );
	}
	
	/**
	 * @param file
	 * @return
	 * @throws URISyntaxException
	 */
	public static String fileURLfromFile(File file) throws URISyntaxException {
		
		try {
			return file.toURI().toURL().toString();
		} catch (MalformedURLException e) {
			throw new URISyntaxException(file.getAbsolutePath(), e.toString());
		}
	}
}
