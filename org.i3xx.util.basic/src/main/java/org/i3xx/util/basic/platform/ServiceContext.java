package org.i3xx.util.basic.platform;

public interface ServiceContext {

	/**
	 * @param t
	 * @return
	 */
	<T> ServiceReference<T> getServiceReference(Class<T> t);
	
	/**
	 * @param ref The service reference
	 * @return The service
	 */
	<T> T getService(ServiceReference<T> ref);
	
	/**
	 * @param name The name of the property
	 * @return The property
	 */
	Object getProperty(String name);
}
