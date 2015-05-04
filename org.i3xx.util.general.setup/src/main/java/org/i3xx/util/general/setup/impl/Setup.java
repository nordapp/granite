package org.i3xx.util.general.setup.impl;

/*
 * #%L
 * NordApp OfficeBase :: zero
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


public class Setup {
	
	//The title of the mandator
	private String title;
	//The id of the mandator (former company name)
	private String id;
	//The root directory of the mandator's data (former officebase root)
	private String root;
	
	public Setup() {
		title = null;
		id = null;
		root = null;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * After every change of a property 'refresh' has to be called
	 */
	public void refresh() {
		
		//
		// To update the mandator, call 'ob:mandator-setup'
		//
	}

	// --------------------------------------------------------------------
	// Static tools
	// --------------------------------------------------------------------
	
	/**
	 * Replace the '.' with the id
	 * protect with '\\'
	 * 
	 * @param param The parameter
	 * @param id The id to insert
	 */
	public static String setCurrentId(String param, String id) {
		StringBuffer buf = new StringBuffer();
		char[] cc = param.toCharArray();
		
		for(int i=0;i<cc.length;i++){
			//protected char
			if(cc[i]=='\\') {
				if((i+1)<cc.length){
					i++;
					buf.append(cc[i]);
				}//fi
				//else does nothing
			}
			//replace '.'
			else if(cc[i]=='.'){
				buf.append(id);
			}
			//any other character
			else{
				buf.append(cc[i]);
			}//fi
		}//for
		
		return buf.toString();
	}
}
