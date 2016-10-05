package org.i3xx.util.basic.util.key;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Converts the possible types of an 128-bit UUID
 * 
 *   BigInteger
 *   UUID
 *   long[]
 *   String representation of the UUID with exact 38 chars
 * 
 * @author Stefan
 * @since 05.10.2016
 */
public class KeyTool {
	
	/**
	 * @return The random UUID
	 */
	public static final UUID random() {
		return UUID.randomUUID();
	}
	
	// ----- BigInteger -----
	
	/**
	 * Converts an UUID String to a BigInteger by using the bit data of each
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final BigInteger toBigInteger(String uuid) {
		return toBigInteger( UUID.fromString(uuid) );
	}
	
	/**
	 * Converts an UUID to a BigInteger by using the bit data of each
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final BigInteger toBigInteger(UUID uuid) {
	    ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
	    buf.putLong(uuid.getMostSignificantBits());
	    buf.putLong(uuid.getLeastSignificantBits());
	    
	    return new BigInteger(buf.array());
	}
	
	/**
	 * Creates a BigInteger from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The BigInteger
	 */
	public static final BigInteger toBigInteger(long mostSigBits, long leastSigBits) {
	    ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
	    buf.putLong(mostSigBits);
	    buf.putLong(leastSigBits);
	    
	    return new BigInteger(buf.array());
	}
	
	// ----- UuidString -----
	
	/**
	 * Converts a BigInteger to an UUID String by using the bit data of each
	 *  
	 * @param bigInt The BigInteger to convert
	 * @return The UUID corresponding to the BigInteger
	 */
	public static final String toUuidString(BigInteger bigInt) {
		return toUuid(bigInt).toString();
	}
	
	/**
	 * Gets an UUID String from an UUID
	 * @param uuid The UUID
	 * @return The UUID String
	 */
	public static final String toUuidString(UUID uuid) {
		return uuid.toString();
	}
	
	/**
	 * Creates a UUID String from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The UUID String
	 */
	public static final String toUuidString(long mostSigBits, long leastSigBits) {
		return toUuid(mostSigBits, leastSigBits).toString();
	}
	
	// ----- Uuid -----
	
	/**
	 * Converts an UUID String to an Uuid
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final UUID toUuid(String uuid) {
		return UUID.fromString(uuid);
	}
	
	/**
	 * Converts a BigInteger to an UUID by using the bit data of each
	 *  
	 * @param bigInt The BigInteger to convert
	 * @return The UUID corresponding to the BigInteger
	 */
	public static final UUID toUuid(BigInteger bigInt) {
		byte[] in = bigInt.toByteArray();
		ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
		buf.put(in, 0, in.length<16?in.length:16);
		buf.rewind();
		long mostSigBits = buf.getLong();
		long leastSigBits = buf.getLong();
		
		return new UUID(mostSigBits, leastSigBits);
	}
	
	/**
	 * Creates a UUID from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The UUID
	 */
	public static final UUID toUuid(long mostSigBits, long leastSigBits) {
		return new UUID(mostSigBits, leastSigBits);
	}
	
	// ----- long2 -----
	
	/**
	 * Get the values from the BigInteger
	 * 
	 * Caution: It is not recommended to store the both values in two long fields in a database
	 * and build a common index for iteration purposes, because this index has some unexpected behaviors.
	 * Better use an UUID VARCHAR field instead.
	 * 
	 * @param bigInt The big integer
	 * @return a long array containing the mostSigBits (up) at the index 0, and the leastSigBits (id) at the index 1;
	 */
	public static final long[] toLong2(BigInteger bigInt) {
		byte[] in = bigInt.toByteArray();
		ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
		buf.put(in, 0, in.length<16?in.length:16);
		buf.rewind();
		long mostSigBits = buf.getLong();
		long leastSigBits = buf.getLong();
		
		return new long[]{mostSigBits, leastSigBits};
	}
	
	/**
	 * Get the values from the UUID
	 * 
	 * Caution: It is not recommended to store the both values in two long fields in a database
	 * and build a common index for iteration purposes, because this index has some unexpected behaviors.
	 * Better use an UUID VARCHAR field instead.
	 * 
	 * @param uuid The UUID to convert
	 * @return a long array containing the mostSigBits (up) at the index 0, and the leastSigBits (id) at the index 1;
	 */
	public static final long[] toLong2(UUID uuid) {
		return new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
	}
	
	/**
	 * Get the values from the UUID String
	 * 
	 * Caution: It is not recommended to store the both values in two long fields in a database
	 * and build a common index for iteration purposes, because this index has some unexpected behaviors.
	 * Better use an UUID VARCHAR field instead.
	 * 
	 * @param uuid The UUID String to convert
	 * @return a long array containing the mostSigBits (up) at the index 0, and the leastSigBits (id) at the index 1;
	 */
	public static final long[] toLong2(String uuid) {
		UUID u = UUID.fromString(uuid);
		return new long[]{u.getMostSignificantBits(), u.getLeastSignificantBits()};
	}

}
