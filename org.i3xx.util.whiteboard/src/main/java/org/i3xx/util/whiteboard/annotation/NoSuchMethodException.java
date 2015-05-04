/**
 * 
 */
package org.i3xx.util.whiteboard.annotation;

/*
 * #%L
 * NordApp OfficeBase :: Whiteboard
 * %%
 * Copyright (C) 2014 - 2015 I.D.S. DialogSysteme GmbH
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


/**
 * @author Administrator
 *
 */
public class NoSuchMethodException extends InterfaceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoSuchMethodException() {
	}

	/**
	 * @param message
	 */
	public NoSuchMethodException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NoSuchMethodException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchMethodException(String message, Throwable cause) {
		super(message, cause);
	}

}
