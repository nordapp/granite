package org.i3xx.util.qtuple.impl;

import java.util.Map;
import java.util.regex.Pattern;

public class Resolver {

	private final Tuple tuple;
	
	private final Map<String, String> values;
	
	public Resolver(Tuple tuple, Map<String, String> values){
		this.tuple = tuple;
		this.values = values;
	}
	
	/**
	 * @return
	 */
	public boolean resolve() {
		return resolve(null, tuple);
	}
	
	/**
	 * @param parent
	 * @param current
	 * @return
	 */
	private boolean resolve(final Tuple parent, final Tuple current) {
		if(current==null)
			return false;
		
		switch(current.getType()) {
		case ROOT:
			return resolve(current, current.getLeft());
		case AND:
			return ( resolve(current, current.getLeft()) && resolve(current, current.getRight()) );
		case OR:
			return ( resolve(current, current.getLeft()) || resolve(current, current.getRight()) );
		case NOT:
			return ! resolve(current, current.getLeft());
		case LEAF:
			if( current.getLeft()==null || current.getRight()==null )
				return false;
			
			String key = current.getLeft().toString();
			String val = current.getRight().toString();
			
			String tmp = values.get(key);
			if(tmp==null)
				return false;
			
			//Like compare '~'
			if(current.getRight().getFlags()==1) {
				Pattern p = Pattern.compile(val);
				return p.matcher(tmp).matches();
			}
			
			return val.equals(tmp);
		case KEY:
			//does no test, see: LEAF
		case VALUE:
			//does no test, see: LEAF
			default:
				return false;
		}
	}
	
	/**
	 * @param buf
	 * @param current
	 * @param n
	 */
	private void toString(StringBuffer buf, Tuple current, int n) {
		
		if(current==null)
			return;
		
		buf.append('\n');
		for(int i=0;i<n;i++)
			buf.append(' ');
		
		switch(current.getType()) {
		case ROOT:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			toString(buf, current.getLeft(), n+1);
			break;
		case LEAF:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			toString(buf, current.getLeft(), n+1);
			toString(buf, current.getRight(), n+1);
			break;
		case AND:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			toString(buf, current.getLeft(), n+1);
			toString(buf, current.getRight(), n+1);
			break;
		case OR:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			toString(buf, current.getLeft(), n+1);
			toString(buf, current.getRight(), n+1);
			break;
		case NOT:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			toString(buf, current.getLeft(), n+1);
			toString(buf, current.getRight(), n+1);
			break;
		case KEY:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			buf.append(':');
			buf.append(current.toString());
			break;
		case VALUE:
			buf.append('<');
			buf.append(current.type);
			buf.append('>');
			buf.append(':');
			buf.append(current.toString());
			break;
			default:
				//does nothing
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		toString(buf, tuple, 0);
		return buf.toString();
	}
}
