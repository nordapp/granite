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


public enum FlagMapOp {

	SET(":="),
	ADD("+="),
	REMOVE("-="),
	AND("&="),
	OR("|=");
	
	private final String op;
	
	/**
	 * @param op
	 */
	FlagMapOp(String op) {
		this.op = op;
	}
	
	/**
	 * @param op The operator String
	 * @return True if the string matches the operator.
	 */
	public final boolean isOp(String op) {
		return this.op.equals(op);
	}
	
	/**
	 * @param op The operator String
	 * @return The enumeration
	 */
	public static final FlagMapOp getOp(String op) {
		
		if( SET.isOp(op))
			return SET;
		else if(ADD.isOp(op))
			return ADD;
		else if(REMOVE.isOp(op))
			return REMOVE;
		else if(AND.isOp(op))
			return AND;
		else if(OR.isOp(op))
			return OR;
		else
			throw new IllegalArgumentException("The operator '"+op+"' is not defined. Use ':=' '+=' '-=' '&=' '|='.");
	}
}
