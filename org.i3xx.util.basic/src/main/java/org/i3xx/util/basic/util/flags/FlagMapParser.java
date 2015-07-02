package org.i3xx.util.basic.util.flags;

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


import java.util.ArrayList;
import java.util.List;

/**
 * <p>The parser parses a comma separated list of elements. Each element
 * represents a command to edit a map of flags.</p>
 * 
 * <ul>
 * <li>key      ::= index of the flag in the map</li>
 * <li>operator ::= the operator</li>
 * <li>value    ::= value of the flag</li>
 * <li>element  ::= key operator value</li>
 * <li>list     ::= element ( ',' element )*</li>
 * </ul>
 * 
 * <p>Description of the operator:</p>
 * 
 * <ul>
 * <li>:= sets the value</li>
 * <li>&= does a logical AND (101 & 110 = 100)</li>
 * <li>|= does a logical OR  (101 | 110 = 111)</li>
 * <li>+= Add all flags, does a logical OR or sets the value if it is not available.</li>
 * <li>-= Remove all flags, does a logical AND with the complement (101 & ~110 = 001, ~110 = 001)</li>
 * </ul>
 * 
 * <p>The key represents the index of the flag in the map.<br>
 * The operator is the command what to do with the map.<br>
 * The value represent the flags to set/remove/edit.</p>
 * 
 * <p>The values of the flags to set (+=) and remove (-=) have up to 64 bit.</p>
 * 
 * <ul>
 * <li> 00 - 0x0000000000000000 - ... 0000 0000 0000 0000 0000 0000 0000 0000</li>
 * 
 * <li> 01 - 0x0000000000000001 - ... 0000 0000 0000 0000 0000 0000 0000 000<b>1</b></li>
 * <li> 02 - 0x0000000000000002 - ... 0000 0000 0000 0000 0000 0000 0000 00<b>1</b>0</li>
 * <li> 03 - 0x0000000000000004 - ... 0000 0000 0000 0000 0000 0000 0000 0<b>1</b>00</li>
 * <li> 04 - 0x0000000000000008 - ... 0000 0000 0000 0000 0000 0000 0000 <b>1</b>000</li>
 * 
 * <li> 05 - 0x0000000000000010 - ... 0000 0000 0000 0000 0000 0000 000<b>1</b> 0000</li>
 * <li> 06 - 0x0000000000000020 - ... 0000 0000 0000 0000 0000 0000 00<b>1</b>0 0000</li>
 * <li> 07 - 0x0000000000000040 - ... 0000 0000 0000 0000 0000 0000 0<b>1</b>00 0000</li>
 * <li> 08 - 0x0000000000000080 - ... 0000 0000 0000 0000 0000 0000 <b>1</b>000 0000</li>
 * 
 * <li> 09 - 0x0000000000000100 - ... 0000 0000 0000 0000 0000 000<b>1</b> 0000 0000</li>
 * <li> 10 - 0x0000000000000200 - ... 0000 0000 0000 0000 0000 00<b>1</b>0 0000 0000</li>
 * <li> 11 - 0x0000000000000400 - ... 0000 0000 0000 0000 0000 0<b>1</b>00 0000 0000</li>
 * <li> 12 - 0x0000000000000800 - ... 0000 0000 0000 0000 0000 <b>1</b>000 0000 0000</li>
 * 
 * <li> 13 - 0x0000000000001000 - ... 0000 0000 0000 0000 000<b>1</b> 0000 0000 0000</li>
 * <li> 14 - 0x0000000000002000 - ... 0000 0000 0000 0000 00<b>1</b>0 0000 0000 0000</li>
 * <li> 15 - 0x0000000000004000 - ... 0000 0000 0000 0000 0<b>1</b>00 0000 0000 0000</li>
 * <li> 16 - 0x0000000000008000 - ... 0000 0000 0000 0000 <b>1</b>000 0000 0000 0000</li>
 * 
 * <li>...</li>
 * <li> 61 - 0x1000000000000000 - 000<b>1</b> ... 0000 0000 0000 0000 0000 0000 0000</li>
 * <li> 62 - 0x2000000000000000 - 00<b>1</b>0 ... 0000 0000 0000 0000 0000 0000 0000</li>
 * <li> 63 - 0x4000000000000000 - 0<b>1</b>00 ... 0000 0000 0000 0000 0000 0000 0000</li>
 * <li> 64 - 0x8000000000000000 - <b>1</b>000 ... 0000 0000 0000 0000 0000 0000 0000</li>
 * </ul>
 * 
 * <p>Example:</p>
 * 
 * <p><b>8+=0x4,8-=0x20</b><br><br>
 * At the position 8 set the flag 0100 and remove the flag 00100000.</p>
 * 
 * <p><b>10+=0b01001,12|=0x37</b><br><br>
 * The position at 10 the flags 01001 will be added and at the position
 * 12, the flags 00110111 will be set.</p>
 * 
 * @author Stefan
 *
 */
public class FlagMapParser {
	
	private List<FlagMapElement> list;
	private String statement;
	private int parsePosition;
	
	public FlagMapParser() {
		list = new ArrayList<FlagMapElement>();
		statement = null;
		parsePosition = 0;
	}
	
	/**
	 * The flag map parser parses the statement
	 * The key is a long int with the radix 10 and the value is a long int with the radix 16.
	 * Valid operators are ':=' '+=' '-=' '&amp;=' '|='
	 * 
	 * key ::=   index of the flag in the map
	 * value ::= value of the flag
	 * 
	 * @param stmt The statement to parse
	 */
	public void parse(String stmt) {
		list.clear();
		statement = stmt;
		parsePosition = 0;
		
		while(parsePosition<statement.length()) {
			int p = getNextPart();
			
			//get the part without the ',' and trim, p<0 is the last element
			String part = statement.substring(parsePosition, (p<0 ? statement.length() : p-1)).trim();
			FlagMapElement elem = new FlagMapElement();
			elem.parse(part);
			list.add(elem);
			
			if(p<0)
				break; //eol
			
			parsePosition = p;
		}
	}
	
	/**
	 * @return The index of the next command.
	 */
	private int getNextPart() {
		
		int p = statement.indexOf(',', parsePosition);
		//no next part available
		if(p==-1)
			return -1;
		
		//set the pointer to the first element after the next part,
		//and leave the parsePosition at the start of the current part.
		return p+1;
	}

	/**
	 * @return the elements list
	 */
	public List<FlagMapElement> getList() {
		return list;
	}
	
	/**
	 * @return The array representation of the elements list.
	 */
	public FlagMapElement[] toArray() {
		return list.toArray(new FlagMapElement[list.size()]);
	}
	
}
