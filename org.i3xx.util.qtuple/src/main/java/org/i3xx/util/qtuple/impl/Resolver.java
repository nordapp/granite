package org.i3xx.util.qtuple.impl;

import java.util.Map;
import java.util.regex.Pattern;

public class Resolver {

	private final Tuple tuple;
	
	private final Map<String, Pattern> regCache;
	
	/**
	 * @param tuple The root node of the tree of tuples
	 * @param regCache The cache map for the regular expression
	 */
	public Resolver(Tuple tuple, Map<String, Pattern> regCache){
		this.tuple = tuple;
		this.regCache = regCache;
	}
	
	/**
	 * @param tuple The root node of the tree of tuples
	 */
	public Resolver(Tuple tuple){
		this.tuple = tuple;
		this.regCache = null;
	}
	
	/**
	 * @param stmt The filter statement
	 */
	public Resolver(String stmt){
		this.tuple = (new Parser(stmt)).getRoot();
		this.regCache = null;
	}
	
	/**
	 * @return
	 */
	public boolean resolve(final Map<String, String> values) {
		return resolve(tuple, values);
	}
	
	/**
	 * @param parent
	 * @param current
	 * @return
	 */
	private boolean resolve(final Tuple current, final Map<String, String> values) {
		if(current==null)
			return false;
		
		switch(current.getType()) {
		case ROOT:
			return resolve(current.getLeft(), values);
		case AND:
			return ( resolve(current.getLeft(), values) && resolve(current.getRight(), values) );
		case OR:
			return ( resolve(current.getLeft(), values) || resolve(current.getRight(), values) );
		case NOT:
			return ! resolve(current.getLeft(), values);
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
				Pattern p = null;
				if(regCache==null) {
					p = Pattern.compile(val);
				}else{
					String k = key+"~"+val;
					p = regCache.get(k);
					if(p==null) {
						p = Pattern.compile(val);
						regCache.put(k, p);
					}//fi
				}//fi
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
