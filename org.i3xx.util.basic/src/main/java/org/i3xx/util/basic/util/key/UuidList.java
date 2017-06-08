package org.i3xx.util.basic.util.key;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UuidList implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5259433300121215485L;
	
	/**
	 * @param stmt The statement to parse.
	 * @return An UuidList containing the parsed statement
	 */
	public static final UuidList of(String stmt) {
		return new UuidList().parse(stmt);
	}
	
	/**
	 * @param array The array to add
	 * @return An UuidList containing the array
	 */
	public static final UuidList of(String[] array) {
		return new UuidList().addAll(array);
	}
	
	/**
	 * The list
	 */
	private List<String> list;
	
	/**
	 * 
	 */
	public UuidList() {
		list = new ArrayList<String>();
	}
	
	/**
	 * Reinitializes the list.
	 * @return this
	 */
	public UuidList reinit() {
		list = new ArrayList<String>();
		return this;
	}
	
	/**
	 * Sets the list.
	 * @param list The list to set.
	 */
	public UuidList setList(List<String> list){
		this.list = list;
		return this;
	}
	
	/**
	 * @return The list
	 */
	public List<String> getList() {
		return list;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		
		if(obj==this)
			return true;
		
		if(obj instanceof UuidList) {
			UuidList u = (UuidList)obj;
			List<String> ul = u.getList();
			
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
		return new UuidList().setList( new ArrayList<String>(list) );
	}
	
	/**
	 * Parses a statement of separated uuid's. The separator can be a comma, a semicolon or a space.
	 * 
	 * @param stmt The separated uuid statement.
	 * @return
	 */
	public UuidList parse(String stmt) {
		String[] uuid = stmt.indexOf(',')>-1 ?
				stmt.split(","):
					stmt.indexOf(';')>-1 ?
							stmt.split(";"):
									stmt.split("\\s,");
							
		for(int i=0;i<uuid.length;i++){
			list.add( uuid[i].trim() );
		}
		return this;
	}
	
	/**
	 * @param uuid
	 * @return
	 */
	public UuidList addAll(String[] uuid) {
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
	public UuidList add(String uuid) {
		list.add(uuid);
		return this;
	}
	
	/**
	 * @return The content of the list as an array
	 */
	public String[] toArray() {
		return list.toArray( new String[list.size()] );
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
			buf.append(list.get(i));
		}
		return buf.toString();
	}

}
