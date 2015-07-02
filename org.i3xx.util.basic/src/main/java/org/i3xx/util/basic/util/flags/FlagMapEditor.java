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


import java.util.Map;

public class FlagMapEditor {

	/**
	 * Edits the flags by the statement
	 * 
	 * Description of the operator:
	 * 
	 * := sets the value
	 * &amp;= does a logical AND (101 &amp; 110 = 100)
	 * |= does a logical OR  (101 | 110 = 111)
	 * += Add all flags, does a logical OR or sets the value if it is not available.
	 * -= Remove all flags, does a logical AND with the complement (101 &amp; ~110 = 001, ~110 = 001)
	 * 
	 * @param flags The map of flags
	 * @param stmt The statement to edit the flags
	 */
	public static void edit(Map<Long, Long> flags, String stmt) {
		
		FlagMapParser parser = new FlagMapParser();
		
		parser.parse(stmt);
		for(FlagMapElement elem : parser.getList()){
			long v = 0;
			Long val = flags.get( Long.valueOf(elem.getKey()) );
			switch(elem.getOp()){
			case SET:
				v = elem.getValue();
				flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				break;
			case ADD:
				if(val==null){
					v = elem.getValue();
					flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				}else{
					v = elem.getValue() | val.longValue();
					flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				}
				break;
			case REMOVE:
				if(val!=null){
					v = (~ elem.getValue()) & val.longValue();
					flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				}
				break;
			case AND:
				if(val!=null){
					v = elem.getValue() & val.longValue();
					flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				}
				break;
			case OR:
				if(val!=null){
					v = elem.getValue() | val.longValue();
					flags.put(Long.valueOf(elem.getKey()), Long.valueOf(v));
				}
				break;
			}//switch
		}//for
	}
}
