package org.i3xx.util.basic.util.key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuidList implements Cloneable, Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5490963455713624767L;

	/**
	 * @param stmt The statement to parse.
	 * @return An UuidList containing the parsed statement
	 */
	public static final GuidList of(String stmt) {
		return new GuidList().parse(stmt);
	}
	
	/**
	 * @param array The array to add
	 * @return An UuidList containing the array
	 */
	public static final GuidList of(Long[] array) {
		return new GuidList().addAll(array);
	}
	
	/**
	 * The list
	 */
	private List<Long> list;
	
	/**
	 * 
	 */
	public GuidList() {
		list = new ArrayList<Long>();
	}
	
	/**
	 * Reinitializes the list.
	 * @return this
	 */
	public GuidList reinit() {
		list = new ArrayList<Long>();
		return this;
	}
	
	/**
	 * Sets the list.
	 * @param list The list to set.
	 */
	public GuidList setList(List<Long> list){
		this.list = list;
		return this;
	}
	
	/**
	 * @return The list
	 */
	public List<Long> getList() {
		return list;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		
		if(obj==this)
			return true;
		
		if(obj instanceof GuidList) {
			GuidList u = (GuidList)obj;
			List<Long> ul = u.getList();
			
			if(ul.size()!=list.size())
				return false;
			
			for(int i=0;i<list.size();i++) {
				if( ! list.get(i).equals( ul.get(i) ) )
					return false;
			}
			return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new GuidList().setList( new ArrayList<Long>(list) );
	}
	
	/**
	 * Parses a statement of separated uuid's. The separator can be a comma, a semicolon or a space.
	 * 
	 * @param stmt The separated uuid statement.
	 * @return
	 */
	public GuidList parse(String stmt) {
		String[] uuid = stmt.indexOf(',')>-1 ?
				stmt.split(","):
					stmt.indexOf(';')>-1 ?
							stmt.split(";"):
									stmt.split("\\s,");
							
		for(int i=0;i<uuid.length;i++){
			list.add( Long.valueOf( uuid[i].trim() ) );
		}
		return this;
	}
	
	/**
	 * @param uuid
	 * @return
	 */
	public GuidList addAll(Long[] uuid) {
		for(int i=0;i<uuid.length;i++){
			list.add( uuid[i] );
		}
		return this;
	}
	
	/**
	 * Adds the uuid to the list
	 * 
	 * @param uuid The uuid to add
	 * @return
	 */
	public GuidList add(Long uuid) {
		list.add(uuid);
		return this;
	}
	
	/**
	 * @return The content of the list as an array
	 */
	public Long[] toArray() {
		return list.toArray( new Long[list.size()] );
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<list.size();i++){
			if(buf.length()>0){
				buf.append(',');
			}
			buf.append(list.get(i).longValue());
		}
		return buf.toString();
	}

}
