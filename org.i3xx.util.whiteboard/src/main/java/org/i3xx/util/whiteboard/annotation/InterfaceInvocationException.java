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


public class InterfaceInvocationException extends InterfaceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Exception targetException;

	/**
	 * 
	 */
	public InterfaceInvocationException() {
		setTargetException(null);
	}

	/**
	 * @param message
	 */
	public InterfaceInvocationException(String message) {
		super(message);
	}

	/**
	 * @param target
	 */
	public InterfaceInvocationException(Throwable target) {
		super();
		if(target instanceof Exception) {
			targetException = (Exception)target;
		}else{
			initCause(target);
		}
	}

	/**
	 * @param message
	 * @param target
	 */
	public InterfaceInvocationException(String message, Throwable target) {
		super(message);
		if(target instanceof Exception) {
			targetException = (Exception)target;
		}else{
			initCause(target);
		}
	}

	/**
	 * @return the targetException
	 */
	public Exception getTargetException() {
		return targetException;
	}

	/**
	 * @param targetException the targetException to set
	 */
	public void setTargetException(Exception targetException) {
		this.targetException = targetException;
	}

}
