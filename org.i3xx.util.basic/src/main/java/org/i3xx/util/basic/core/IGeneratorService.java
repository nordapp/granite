package org.i3xx.util.basic.core;

/**
 * The class is used to create new DAO's in a generic way.
 * 
 * @author stefan
 *
 * @param <E>
 */
public interface IGeneratorService<E> {

	/**
	 * Gets the object by it's guid
	 * 
	 * @param guid The guid of the object
	 * @return The object or null if it isn't available
	 */
	E getObject(Long guid);
	
	/**
	 * Gets the object by it's uuid
	 * 
	 * @param uuid The uuid of the object
	 * @return The object or null if it isn't available
	 */
	E getObject(String uuid);
	
	/**
	 * Gets the object or creates a new object if it isn't available.
	 * 
	 * Create a new Object using a generic UUID
	 * 
	 * @param uuid The uuid of the object
	 * @return The object or a new one if it isn't available
	 */
	E getOrCreateObject(String uuid);
	
	/**
	 * Gets the object or creates a new object if it isn't available.
	 * 
	 * @param uuid The uuid of the object
	 * @param history The history of the object
	 * @return The object or a new one if it isn't available
	 */
	E getOrCreateObject(String uuid, String history);
	
	/**
	 * Gets the object or creates a new one if it isn't available.
	 * 
	 * @param guid The guid of the object
	 * @param uuid The uuid of the object
	 * @param history The history of the object
	 * @return The object or a new one if it isn't available
	 */
	E getOrCreateObject(Long guid, String uuid, String history);
	
	/**
	 * Saves the object
	 * 
	 * @param e The object to save
	 */
	void save(E e);
	
	/**
	 * @param guid
	 */
	void delete(Long guid);
}
