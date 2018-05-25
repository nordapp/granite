package org.i3xx.util.general.setup.impl;

import org.i3xx.node.Base;

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

/**
 * This object contains the basic description of an object.
 * 
 * It is agreed and understood that every object used by the system
 * is accessible by it's uuid in any case.
 * 
 * This setup is the description how to get the object if you have
 * the uuid.
 * 
 * The simplest case is the field 'root' contains the String 'json'
 * and the field 'data' the object as a JSON String.
 * 
 * It doesn't make any difference if an object is stored in a database
 * or hold by a cache or is simply written to a flat file.
 * 
 * The setup can be treated as an object by the system.
 * 
 * @author green
 *
 */
public class Setup implements Base {
	
	//------------------------------------------------
	// ID section
	//------------------------------------------------

	//The id of this object (local identifier)
	private String obid;
	//The guid of this object (global identifier)
	private String obguid;
	//The uuid of this object (universal identifier)
	private String obuuid;
	
	//------------------------------------------------
	// Info section
	//------------------------------------------------
	
	//The creation timestamp of this object
	private String obctime;
	//The update timestamp of this object
	private String obutime;
	//The history of this object
	private String obhistory;
	//The hash of this object
	private String obhash;
	//The flags of this object
	private String obflags;

	//------------------------------------------------
	// Naming section
	//------------------------------------------------
	
	//The name of the object
	private String obname;
	//The optional alias name of the object
	private String obalias;
	//The title of the object
	private String obtitle;
	//The description of the object
	private String obdescription;
	
	//------------------------------------------------
	// Data section
	//------------------------------------------------
	
	//The data root of the object's data
	private String obroot;
	//The mime type
	private String obmime;
	//The object's data
	private String obdata;
	//The uri to access the object's data
	private String oburi;
	//The uri to a service that has the current uri to the data.
	private String oblink;
	
	//------------------------------------------------
	// Link section to another object or resource
	//------------------------------------------------
	
	//The uuid of the object (universal identifier)
	private String linkuuid;
	
	public Setup() {
		obid = null;
		obguid = null;
		obuuid = null;
		obctime = null;
		obutime = null;
		obhistory = null;
		obhash = null;
		obflags = null;
		obname = null;
		obalias = null;
		obtitle = null;
		obdescription = null;
		obroot = null;
		obmime = null;
		obdata = null;
		oburi = null;
		oblink = null;
		linkuuid = null;
	}
	
	/**
	 * @return the obid
	 */
	public String getObid() {
		return obid;
	}

	/**
	 * @param obid the obid to set
	 */
	public void setObid(String obid) {
		this.obid = obid;
	}

	/**
	 * @return the obguid
	 */
	public String getObguid() {
		return obguid;
	}

	/**
	 * @param obguid the obguid to set
	 */
	public void setObguid(String obguid) {
		this.obguid = obguid;
	}

	/**
	 * @return the obuuid
	 */
	public String getObuuid() {
		return obuuid;
	}

	/**
	 * @return the obctime
	 */
	public String getObctime() {
		return obctime;
	}

	/**
	 * @param obctime the obctime to set
	 */
	public void setObctime(String obctime) {
		this.obctime = obctime;
	}

	/**
	 * @return the obutime
	 */
	public String getObutime() {
		return obutime;
	}

	/**
	 * @param obutime the obutime to set
	 */
	public void setObutime(String obutime) {
		this.obutime = obutime;
	}

	/**
	 * @param obuuid the obuuid to set
	 */
	public void setObuuid(String obuuid) {
		this.obuuid = obuuid;
	}

	/**
	 * @return the obhistory
	 */
	public String getObhistory() {
		return obhistory;
	}

	/**
	 * @param obhistory the obhistory to set
	 */
	public void setObhistory(String obhistory) {
		this.obhistory = obhistory;
	}
	
	/**
	 * @return the obhash
	 */
	public String getObhash() {
		return obhash;
	}

	/**
	 * @param obhash the obhash to set
	 */
	public void setObhash(String obhash) {
		this.obhash = obhash;
	}

	/**
	 * @return the obflags
	 */
	public String getObflags() {
		return obflags;
	}

	/**
	 * @param obflags the obflags to set
	 */
	public void setObflags(String obflags) {
		this.obflags = obflags;
	}

	/**
	 * @return the obname
	 */
	public String getObname() {
		return obname;
	}

	/**
	 * @param obname the obname to set
	 */
	public void setObname(String obname) {
		this.obname = obname;
	}

	/**
	 * @return the obalias
	 */
	public String getObalias() {
		return obalias;
	}

	/**
	 * @param obalias the obalias to set
	 */
	public void setObalias(String obalias) {
		this.obalias = obalias;
	}

	/**
	 * @return the obtitle
	 */
	public String getObtitle() {
		return obtitle;
	}

	/**
	 * @param obtitle the obtitle to set
	 */
	public void setObtitle(String obtitle) {
		this.obtitle = obtitle;
	}

	/**
	 * @return the obdescription
	 */
	public String getObdescription() {
		return obdescription;
	}

	/**
	 * @param obdescription the obdescription to set
	 */
	public void setObdescription(String obdescription) {
		this.obdescription = obdescription;
	}

	/**
	 * @return the obroot
	 */
	public String getObroot() {
		return obroot;
	}

	/**
	 * @param obroot the obroot to set
	 */
	public void setObroot(String obroot) {
		this.obroot = obroot;
	}
	
	/**
	 * @return the obmime
	 */
	public String getObmime() {
		return obmime;
	}

	/**
	 * @param obmime the obmime to set
	 */
	public void setObmime(String obmime) {
		this.obmime = obmime;
	}

	/**
	 * @return the obdata
	 */
	public String getObdata() {
		return obdata;
	}

	/**
	 * @param obdata the obdata to set
	 */
	public void setObdata(String obdata) {
		this.obdata = obdata;
	}

	/**
	 * @return the oburi
	 */
	public String getOburi() {
		return oburi;
	}

	/**
	 * @param oburi the oburi to set
	 */
	public void setOburi(String oburi) {
		this.oburi = oburi;
	}

	/**
	 * @return the oblink
	 */
	public String getOblink() {
		return oblink;
	}

	/**
	 * @param oblink the oblink to set
	 */
	public void setOblink(String oblink) {
		this.oblink = oblink;
	}
	
	/**
	 * @return the linkuuid
	 */
	public String getLinkuuid() {
		return linkuuid;
	}

	/**
	 * @param linkuuid the linkuuid to set
	 */
	public void setLinkuuid(String linkuuid) {
		this.linkuuid = linkuuid;
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
