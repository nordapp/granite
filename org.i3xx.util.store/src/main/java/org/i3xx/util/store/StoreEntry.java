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


import java.io.Serializable;
import java.math.BigInteger;
import java.net.MalformedURLException;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class StoreEntry implements Serializable {
	
	private static final long serialVersionUID = -6050240307701676763L;
	
	public static final long MINUTE = 60000L;
	public static final long HOUR = 3600000L;
	public static final long DAY = 86400000L;
	public static final long WEEK = 604800000L;
	
	private byte[] id;
	private byte[] trans;
	private long lifetime;
	private boolean persistent;
	private Object sort;
	
	/**
	 * 
	 */
	public StoreEntry() {
		super();
		
		this.id = BigInteger.ZERO.toByteArray();
		this.trans = BigInteger.ZERO.toByteArray();
		this.lifetime = System.currentTimeMillis();
		this.persistent = false;
		this.sort = new Integer(hashCode());
	}
	
	/**
	 * @param trans The store key
	 * @param id The id of the store entry
	 * @param lifetime The time to live
	 * @param persistent The persistence flag
	 * @param sort The sort parameter
	 * @return The store entry
	 */
	public static StoreEntry of(BigInteger trans, BigInteger id, long lifetime, boolean persistent, Object sort) {
		
		StoreEntry result = new StoreEntry();
		result.setId(id);
		result.setTrans(trans);
		result.addLifetime(lifetime);
		result.setPersistent(persistent);
		result.setSort(sort);
		
		return result;
	}
	
	/**
	 * @return Returns the id.
	 */
	public BigInteger getId() {
		return new BigInteger(id);
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(BigInteger id) {
		this.id = id.toByteArray();
	}

	/**
	 * @return Returns the lifetime.
	 */
	public long getLifetime() {
		return lifetime;
	}

	/**
	 * @param lifetime The lifetime to set.
	 */
	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}

	/**
	 * If the liftime is lower than the current time, the gc can destroy the object
	 * and free any ressource used by it.
	 * 
	 * @param lifetime The lifetime to add.
	 */
	public void addLifetime(long lifetime) {
		this.lifetime += lifetime;
	}

	/**
	 * @return Returns the persistent.
	 */
	public boolean isPersistent() {
		return persistent;
	}

	/**
	 * If persistent is set to false, the object can be read once. Than it will
	 * be destroyed.
	 * 
	 * @param persistent The persistent to set.
	 */
	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	/**
	 * @return Returns the trans.
	 */
	public BigInteger getTrans() {
		return new BigInteger(trans);
	}

	/**
	 * @param trans The trans to set.
	 */
	public void setTrans(BigInteger trans) {
		this.trans = trans.toByteArray();
	}

	/**
	 * @return Returns the sort.
	 */
	public Object getSort() {
		return sort;
	}

	/**
	 * @param sort The sort to set.
	 */
	public void setSort(Object sort) {
		this.sort = sort;
	}
	
	/**
	 * @param entry The store entry
	 * @return The String representation of the URI
	 */
	public static final String getURI(StoreEntry entry) {
		return "store://"+entry.trans+"/"+entry.id;
	}
	
	/**
	 * @param trans The store key
	 * @param id The id of the store entry
	 * @return The String representation of the URI
	 */
	public static final String getURI(BigInteger trans, BigInteger id) {
		return "store://"+String.valueOf(trans)+"/"+String.valueOf(id);
	}
	
	/**
	 * @param uri The URI to parse the StoreEntry from.
	 * @return The store entry
	 * @throws MalformedURLException 
	 */
	public static final StoreEntry parseURI(String uri) throws MalformedURLException {
		StoreEntry entry = new StoreEntry();
		
		if(uri==null || !uri.startsWith("store://"))
			throw new MalformedURLException("The uri '"+uri+"' is not valid.");
		
		uri = uri.substring(8);
		
		int i = uri.indexOf('/');
		if(i==-1){
			throw new MalformedURLException("The uri '"+uri+"' is not valid.");
		}else if(i==0){
			throw new MalformedURLException("The uri '"+uri+"' is not valid.");
		}else{
			entry.trans = new BigInteger(uri.substring(0,i)).toByteArray();
			uri = uri.substring(i+1);
		}

		i = uri.indexOf('/');
		if(i==-1){
			entry.id = new BigInteger(uri).toByteArray();
		}else if(i==0){
			throw new MalformedURLException("The uri '"+uri+"' is not valid.");
		}else{
			entry.id = new BigInteger(uri.substring(0,i)).toByteArray();
			//uri = uri.substring(i+1);
		}
		
		return entry;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("StoreEntry");
		buf.append(" id:");
		buf.append(new BigInteger(this.id));
		buf.append(", key:");
		buf.append(new BigInteger(this.trans));
		buf.append(", liftime:");
		buf.append(this.lifetime);
		buf.append(", persistent:");
		buf.append(this.persistent);
		
		return buf.toString();
	}

}
