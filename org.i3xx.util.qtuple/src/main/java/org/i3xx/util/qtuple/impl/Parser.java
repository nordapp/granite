package org.i3xx.util.qtuple.impl;

import org.i3xx.util.qtuple.impl.Tuple.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Use regexp
 * 
 * @author Stefan
 *
 */
public final class Parser {
	
	private static Logger logger = LoggerFactory.getLogger(Parser.class);
	
	private final Tuple root;
	
	/**
	 * symbol ::= A valid Java string, may be quoted by '"' (escape char '\')
	 * a ::= symbol
	 * b ::= symbol
	 * op ::= operator: and '&', or '|', not '!'
	 * tuple ::= '(' a ',' b ')'
	 * statement ::= '(' op tuple ')'
	 * 
	 * @param stmt
	 */
	public Parser(String stmt) {
		root = parses(stmt, Type.ROOT);
	}
	
	/**
	 * @return
	 */
	Tuple getRoot() {
		return root;
	}
	
	/**
	 * @param stmt The statement to parse
	 * @param type The type of the tuple (start with Type.ROOT)
	 * @return
	 */
	public Tuple parses(String stmt, Type type) {
		//stmt = stmt/*.trim()*/; //the central trim here is optional
		logger.trace("parses: {}", stmt);
		
		String s = null;
		
		if( isBracket(stmt) ){
			s = readBracket(stmt);
			return parses(s, type);
		}else if( isQuote(stmt) ){
			s = readQuote(stmt);
			return new Monade(s, Type.VALUE);
		}else if( hasComma(stmt) ){
			//is tuple
			s = readBeforeComma(stmt);
			Tuple k = parses(s, type);
			s = stmt.substring(s.length()+1);
			Tuple v = parses(s, type);
			return new Tuple(k, v, type);
		}else if( isOperator(stmt) ){
			s = readOperator(stmt).trim(); //needs a trim because of the equals
			Type t = s.equals("&") ? Type.AND : 
				s.equals("|") ? Type.OR : 
					s.equals("!") ? Type.NOT :
						null;
			if(t==null)
				throw new IllegalArgumentException("The operator '"+s+"' of the statement '"+
						stmt+"' is not valid (usage: &, |, !).;");
			
			// (key1=value,key2=value,key3=value,...)
			// If there are more than 2 elements in a parameter list
			// the and|or parameters are processed here.
			
			s = stmt.substring(s.length());
			return t==Type.NOT ? new Monade(parses(s, t), t): parses(s, t);
		}else if( hasSeparator(stmt) ){
			//is tuple
			s = readBeforeSeparator(stmt);
			Tuple k = parses(s, Type.KEY);
			char t = stmt.charAt(s.length());
			s = stmt.substring(s.length()+1);
			Tuple v = parses(s, Type.VALUE);
			//like separator
			if(t=='~') {
				v = new Monade((Monade)v, Monade.LIKE);
			}
			return new Tuple(k, v, Type.LEAF);
		}else if( hasBracket(stmt) ){
			s = readBeforeBracket(stmt);
			
			throw new IllegalArgumentException("The operator '"+s+"' of the statement '"+
					stmt+"' is not valid (usage: &, |, !).");
		}else{
			s = stmt.trim(); //needs a trim because of setting the tuple
			
			return new Monade(s, type);
		}
	}
	
	/**
	 * Reads the next quoted String or null if the String is not quoted.
	 * 
	 * @return The quoted String without the quotes
	 */
	public String readQuote(String stmt) {
		String s = stmt.trim(); //needs a trim because of the startsWith and endsWith
		if(s.startsWith("\"")){
			for(int i=1;i<s.length();i++) {
				char c = s.charAt(i);
				if(c=='"' && s.charAt(i-1)!='\\') {
					return s.substring(1, i);
				}//fi
			}//for
		}//fi
		
		return null;
	}
	
	/**
	 * Reads the next bracket or null if the String is not a bracket.
	 * 
	 * @param stmt
	 * @return
	 */
	public String readBracket(String stmt) {
		String s = stmt.trim(); //needs a trim because of the startsWith and endsWith
		if(s.startsWith("(") && s.endsWith(")")){
			return s.substring(1, s.length()-1);
		}//fi
		return null;
	}
	
	/**
	 * Reads the String before the next bracket '('
	 * @param stmt
	 * 
	 * @return
	 */
	public String readOperator(String stmt) {
		String s = stmt;
		//if(s.length()!=1)
		//	throw new IllegalArgumentException("The statement '"+s+"' is not an operator (usage: &, |, !).");
		
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='('){
				return s.substring(0, i);
			}//fi
		}//for
		
		return null;
	}
	
	/**
	 * Reads the String before the next bracket '('
	 * @param stmt
	 * 
	 * @return
	 */
	public String readBeforeBracket(String stmt) {
		String s = stmt;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='('){
				return s.substring(0, i);
			}//fi
		}//for
		
		return null;
	}
	
	/**
	 * Reads the String before the next comma ','
	 * @param stmt
	 * 
	 * @return
	 */
	public String readBeforeComma(String stmt) {
		String s = stmt;
		int p = scanComma(s);
		
		return p<0 ? null : s.substring(0, p);
	}
	
	/**
	 * Reads the String before the next separator '='
	 * @param stmt
	 * 
	 * @return
	 */
	public String readBeforeSeparator(String stmt) {
		String s = stmt;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='=' || s.charAt(i)=='~'){
				return s.substring(0, i);
			}//fi
		}//for
		
		return null;
	}
	
	/**
	 * Returns true if the next String is quoted
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean isQuote(String stmt) {
		String s = stmt;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==' '){
				//does nothing
			}else if(s.charAt(i)=='"'){
				return true;
			}else{
				return false;
			}//fi
		}//for
		
		return false;
	}
	
	/**
	 * Returns true if the statement starts with a bracket
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean isBracket(String stmt) {
		String s = stmt;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==' '){
				//does nothing
			}else if(s.charAt(i)=='('){
				return true;
			}else{
				return false;
			}//fi
		}//for
		
		return false;
	}
	
	/**
	 * Returns true if the statement starts with an operator
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean isOperator(String stmt) {
		String s = stmt;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)==' '){
				//does nothing
			}else if(s.charAt(i)=='&'){
				return true;
			}else if(s.charAt(i)=='|'){
				return true;
			}else if(s.charAt(i)=='!'){
				return true;
			}else{
				return false;
			}//fi
		}//for
		
		return false;
	}
	
	/**
	 * Returns true if the statement contains a separator char
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean hasSeparator(String stmt) {
		return stmt.contains("=") || stmt.contains("~");
	}
	
	/**
	 * Returns true if the statement contains a separator char
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean hasComma(String stmt) {
		String s = stmt;
		return scanComma(s)>-1;
	}
	
	/**
	 * @param s
	 * @return
	 */
	public int scanComma(String s) {
		int b = 0;
		boolean c = false;
		for(int i=0;i<s.length();i++) {
			if(s.charAt(i)=='(')
				b++;
			if(s.charAt(i)==')')
				b--;
			if(s.charAt(i)=='"' && s.charAt(i-1)!='\\')
				c = !c;
			if(s.charAt(i)==',' && b==0 && !c)
				return i;
		}
		return -1;
	}
	
	/**
	 * Returns true if the statement contains an open bracket char
	 * 
	 * @param stmt
	 * @return
	 */
	public boolean hasBracket(String stmt) {
		return stmt.contains("(");
	}
	
}
