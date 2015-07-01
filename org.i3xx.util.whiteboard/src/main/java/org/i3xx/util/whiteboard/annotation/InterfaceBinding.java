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


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>The InterfaceBinding provides a simple wrapper that can break
 * the dependency between bundles.</p>
 * 
 * <p>The whiteboard pattern is a good practice to break dependency.</p>
 * 
 * <p>A bundle (A) wants to consume a function. So it provides an interface
 * to do this. It queries the registry for a service that implements the
 * interface to support the required functionality. This service may 
 * be registered by another bundle (B).</p>
 * 
 * <p>Now the bundle (B) depends on the interface exported by bundle (A).</p>
 * 
 * <p>This is not what we really want using OSGi. The InterfaceBinding resolves
 * that issue, using annotations to mark the methods of a service that can
 * be used by the consumer. The server has no need to implement the interface
 * but annotate it's relating methods by the <code>@BindIf</code> annotation.</p>
 * 
 * <p>Usage: <code>@BindIf(method="name")</code> The name and the arguments
 * must match the method definition of the interface.</p>
 * 
 * <p>Example:</p>
 * 
 * <p>Interface:</p>
 * <code><pre>
 * public interface MyInterface {
 *    void add(String key, String param);
 *    String get(String key);
 *    String[] list();
 * }
 * </pre></code>
 * 
 * <p>The service:</p>
 * <pre><code>
 * <span>@</span>BindIf(method="add")
 * public void test1(String key, String value) {
 *   store.put(key, value);
 * }
 * <span>@</span>BindIf(method="get")
 * String test2(String key) {
 *   return ...
 * }
 * <span>@</span>BindIf(method="list")
 * String[] test3() {
 *   return ...
 * }
 * </code></pre>
 * 
 * <p>The concrete binding:</p>
 * <pre><code>
 * public class MyInterfaceImpl extends InterfaceBinding implements MyInterface {
 *   public MyInterfaceImpl(Object service) {
 *     super(service);
 *   }
 *   public void add(String key, String param) {
 *     try {
 *       invoke("add", key, param);
 *     } catch (InterfaceException e) {
 *       logAndThrow(e);
 *     }
 *   }
 *   public String get(String key) {
 *     try {
 *       return invoke("get", key);
 *     } catch (InterfaceException e) {
 *       return logAndThrow(e);
 *     }
 *   }
 *   public String[] list() {
 *     try {
 *       return invoke("list");
 *     } catch (InterfaceException e) {
 *       return logAndThrow(e);
 *     }
 *   }
 * }
 * </code></pre>
 * 
 * <p>Now, the bundle (A) can use the service from bundle (B) without any
 * dependency of the both bundles. Instead of the availability of the service
 * itself. But it does no matter who provides the requested service.</p>
 * 
 * 
 * @author Stefan
 * @since 2015-04-20
 *
 */
public abstract class InterfaceBinding {
	
	private Logger logger = LoggerFactory.getLogger(InterfaceBinding.class);
	
	protected Object service;
	protected Class<?> clazz;
	protected Map<String, Method> methods;
	
	/**
	 * @param service The sevice that "implements" the interface
	 * using the annotation.
	 */
	public InterfaceBinding(Object service) {
		this.service = service;
		this.clazz = service.getClass();
		this.methods = new HashMap<String, Method>();
		
		Method[] methods = this.clazz.getDeclaredMethods();
		for(int i=0;i<methods.length;i++) {
			Method m = methods[i];
			if(m.isAnnotationPresent(BindIf.class)) {
				this.methods.put( m.getAnnotation(BindIf.class).method() , m);
			}//fi
		}//for
	}
	
	/**
	 * Invokes the method of the interface.
	 * 
	 * @param name The name of the method to be called
	 * @param args The arguments
	 * @return The result of dispatching the method.
	 * @throws InterfaceException
	 */
	@SuppressWarnings("unchecked")
	public <T> T invoke(String name, Object... args) throws InterfaceException {
		Method m = methods.get(name);
		if(m==null){
			//extends InterfaceException
			throw new NoSuchMethodException("The method '"+name+"' is not present or annotated.");
		}
		
		try {
			return (T)m.invoke(service, args);
		} catch (IllegalArgumentException e) {
			throw new InterfaceException("The argument is not legal.", e);
		} catch (IllegalAccessException e) {
			throw new InterfaceException( e.getMessage(), e );
		} catch (InvocationTargetException e) {
			//extends InterfaceException
			throw new InterfaceInvocationException( e.getTargetException() );
		}
	}
	
	/**
	 * @param e The exception to log
	 * @return Nothing, because a RuntimeException is thrown.
	 * <p>The compiler needs a return because the runtime exception
	 * thrown in every case cannot be recognized.</p>
	 */
	public <T> T logAndThrow(InterfaceException e) {
		if(e instanceof NoSuchMethodException) {
			logger.debug( e.getMessage() );
			throw new RuntimeException( e.getMessage() );
		}
		else if(e instanceof InterfaceInvocationException) {
			logger.debug( "The method throws:", ((InterfaceInvocationException) e).getTargetException() );
			throw new RuntimeException( ((InterfaceInvocationException) e).getTargetException().toString() );
		}
		else{
			logger.debug( "An exception occurs:", e );
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param e The exception to log
	 * @param result The result to return
	 * @return <code>result</code>
	 */
	public <T> T logOnly(InterfaceException e, T result) {
		if(e instanceof NoSuchMethodException) {
			logger.debug( e.getMessage() );
		}
		else if(e instanceof InterfaceInvocationException) {
			logger.debug( "The method throws:", ((InterfaceInvocationException) e).getTargetException() );
		}
		else{
			logger.debug( "An exception occurs:", e );
		}
		return result;
	}

}
