package org.i3xx.util.basic.util.key;

import java.util.UUID;

public class UuidTool {

	/**
	 * Creates a new random UUID and return it|s String representation
	 * 
	 * @return The String representation of the generated UUID. e.g. 123e4567-e89b-12d3-a456-426655440000
	 */
	public static final String getOne() {
		return UUID.randomUUID().toString();
	}
}
