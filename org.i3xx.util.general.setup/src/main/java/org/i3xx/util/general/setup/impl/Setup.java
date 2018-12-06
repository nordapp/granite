package org.i3xx.util.general.setup.impl;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import org.i3xx.node.domain.Base;

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
	private Long obid;
	//The guid of this object (global identifier)
	private Long obguid;
	//The uuid of this object (universal identifier)
	private String obuuid;
	
	//------------------------------------------------
	// Info section
	//------------------------------------------------
	
	//The creation timestamp of this object
	private GregorianCalendar obctime;
	//The update timestamp of this object
	private GregorianCalendar obutime;
	//The history of this object
	private String obhistory;
	//The hash of this object
	private Long obhash;
	//The flags of this object
	private BigInteger obflags;

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
	public Long getObid() {
		return obid;
	}

	/**
	 * @param obid the obid to set
	 */
	public Base setObid(Long obid) {
		this.obid = obid;
		return this;
	}

	/**
	 * @return the obguid
	 */
	public Long getObguid() {
		return obguid;
	}

	/**
	 * @param obguid the obguid to set
	 */
	public Base setObguid(Long obguid) {
		this.obguid = obguid;
		return this;
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
	public GregorianCalendar getObctime() {
		return obctime;
	}

	/**
	 * @param obctime the obctime to set
	 */
	public Base setObctime(GregorianCalendar obctime) {
		this.obctime = obctime;
		return this;
	}

	/**
	 * @return the obutime
	 */
	public GregorianCalendar getObutime() {
		return obutime;
	}

	/**
	 * @param obutime the obutime to set
	 */
	public Base setObutime(GregorianCalendar obutime) {
		this.obutime = obutime;
		return this;
	}

	/**
	 * @param obuuid the obuuid to set
	 */
	public Base setObuuid(String obuuid) {
		this.obuuid = obuuid;
		return this;
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
	public Base setObhistory(String obhistory) {
		this.obhistory = obhistory;
		return this;
	}
	
	/**
	 * @return the obhash
	 */
	public Long getObhash() {
		return obhash;
	}

	/**
	 * @param obhash the obhash to set
	 */
	public Base setObhash(Long obhash) {
		this.obhash = obhash;
		return this;
	}

	/**
	 * @return the obflags
	 */
	public BigInteger getObflags() {
		return obflags;
	}

	/**
	 * @param obflags the obflags to set
	 */
	public Base setObflags(BigInteger obflags) {
		this.obflags = obflags;
		return this;
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
	public Base setObname(String obname) {
		this.obname = obname;
		return this;
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
	public Base setObalias(String obalias) {
		this.obalias = obalias;
		return this;
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
	public Base setObtitle(String obtitle) {
		this.obtitle = obtitle;
		return this;
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
	public Base setObdescription(String obdescription) {
		this.obdescription = obdescription;
		return this;
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
	public Base setObroot(String obroot) {
		this.obroot = obroot;
		return this;
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
	public Base setObmime(String obmime) {
		this.obmime = obmime;
		return this;
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
	public Base setObdata(String obdata) {
		this.obdata = obdata;
		return this;
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
	public Base setOburi(String oburi) {
		this.oburi = oburi;
		return this;
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
	public Base setOblink(String oblink) {
		this.oblink = oblink;
		return this;
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
	public Base setLinkuuid(String linkuuid) {
		this.linkuuid = linkuuid;
		return this;
	}

	/**
	 * After every change of a property 'refresh' has to be called
	 */
	public Base refresh() {
		
		//
		// To update the mandator, call 'ob:mandator-setup'
		//
		return this;
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
