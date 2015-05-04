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


public class FlagMapElement {
	
	private long key;
	private FlagMapOp op;
	private long value;

	public FlagMapElement() {
		key = 0;
		op = null;
		value = 0;
	}
	
	/**
	 * The key and the value have a radix of 10, but the value can use a '0x0' or 0b0' form
	 * to use a radix of 16 or a radix of 2.
	 * @param stmt The statement to parse
	 */
	public void parse(String stmt) {
		int p = stmt.indexOf('=');
		if(p>1){
			String k = stmt.substring(0, p-1).trim();
			op = FlagMapOp.getOp( stmt.substring(p-1, p+1) );
			String v = stmt.substring(p+1).trim();
			
			int radix = 10;
			if(v.startsWith("0x")){
				v = v.substring(2);
				radix = 16;
			}
			else if(v.startsWith("0b")){
				v = v.substring(2);
				radix = 2;
			}
			key = Long.parseLong(k, 10);
			value = Long.parseLong(v, radix);
		}else
			throw new IllegalArgumentException("The statement '"+stmt+"' is not valid. Use: key operator value.");
	}
	
	/**
	 * @return the key
	 */
	public long getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(long key) {
		this.key = key;
	}

	/**
	 * @return the op
	 */
	public FlagMapOp getOp() {
		return op;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(FlagMapOp op) {
		this.op = op;
	}

	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value) {
		this.value = value;
	}

}
