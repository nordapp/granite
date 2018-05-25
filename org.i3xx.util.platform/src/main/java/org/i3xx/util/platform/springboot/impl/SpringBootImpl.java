package org.i3xx.util.platform.springboot.impl;

import org.i3xx.util.basic.platform.Platform;
import org.i3xx.util.basic.platform.ServiceContext;
import org.i3xx.util.platform.impl.AvailableKeys;

public class SpringBootImpl implements Platform {

	private ServiceContext serviceContext;
	
	public SpringBootImpl() {
		serviceContext = null;
	}

	/* (non-Javadoc)
	 * @see org.i3xx.util.basic.platform.Platform#keys()
	 */
	public String[] keys() {
		return AvailableKeys.getKeys();
	}

	/* (non-Javadoc)
	 * @see org.i3xx.util.basic.platform.Platform#getObject(java.lang.String)
	 */
	public Object getObject(String key) {
		if (key.equals( AvailableKeys.SERVER_HOME)) {
			return serviceContext.getProperty("server.home");
		}
		else
			throw new IllegalArgumentException("The key '"+key+"' is not supported yet.");
	}

	/**
	 * @return the serviceContext
	 */
	public ServiceContext getServiceContext() {
		return serviceContext;
	}

	/**
	 * @param serviceContext the serviceContext to set
	 */
	public void setServiceContext(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
	}
	
	
}
