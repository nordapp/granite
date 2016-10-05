package org.i3xx.util.basic.util.key;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

public class KeyTool {

	// Short forms - less writing
	
	/**
	 * Converts an UUID String to a BigInteger by using the bit data of each
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final BigInteger toBigInteger(String uuid) {
		return uuidToBigInteger( UUID.fromString(uuid) );
	}
	
	/**
	 * Creates a BigInteger from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The BigInteger
	 */
	public static final BigInteger toBigInteger(long mostSigBits, long leastSigBits) {
		return long2ToBigInteger(mostSigBits, leastSigBits);
	}
	
	/**
	 * Converts a BigInteger to an UUID String by using the bit data of each
	 *  
	 * @param bigInt The BigInteger to convert
	 * @return The UUID corresponding to the BigInteger
	 */
	public static final String toUuidString(BigInteger bigInt) {
		return bigIntegerToUuid(bigInt).toString();
	}
	
	// ------------------------------------------------------------------------
	
	/**
	 * Converts an UUID String to a BigInteger by using the bit data of each
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final BigInteger uuidToBigInteger(String uuid) {
		return uuidToBigInteger( UUID.fromString(uuid) );
	}
	
	/**
	 * Converts an UUID to a BigInteger by using the bit data of each
	 *  
	 * @param uuid The UUID to convert
	 * @return The BigInteger corresponding to the UUID
	 */
	public static final BigInteger uuidToBigInteger(UUID uuid) {
	    ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
	    buf.putLong(uuid.getMostSignificantBits());
	    buf.putLong(uuid.getLeastSignificantBits());
	    
	    return new BigInteger(buf.array());
	}
	
	/**
	 * Converts a BigInteger to an UUID String by using the bit data of each
	 *  
	 * @param bigInt The BigInteger to convert
	 * @return The UUID corresponding to the BigInteger
	 */
	public static final String bigIntegerToUuidString(BigInteger bigInt) {
		return bigIntegerToUuid(bigInt).toString();
	}
	
	/**
	 * Converts a BigInteger to an UUID by using the bit data of each
	 *  
	 * @param bigInt The BigInteger to convert
	 * @return The UUID corresponding to the BigInteger
	 */
	public static final UUID bigIntegerToUuid(BigInteger bigInt) {
		byte[] in = bigInt.toByteArray();
		ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
		buf.put(in, 0, in.length<16?in.length:16);
		buf.rewind();
		long mostSigBits = buf.getLong();
		long leastSigBits = buf.getLong();
		
		return new UUID(mostSigBits, leastSigBits);
	}
	
	/**
	 * Creates a BigInteger from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The BigInteger
	 */
	public static final BigInteger long2ToBigInteger(long mostSigBits, long leastSigBits) {
	    ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
	    buf.putLong(mostSigBits);
	    buf.putLong(leastSigBits);
	    
	    return new BigInteger(buf.array());
	}
	
	/**
	 * Get the values from the BigInteger
	 * 
	 * Caution: It is not recommended to store the both values in two long fields in a database
	 * and build a common index for ID purposes, because this index has some unexpected behaviors.
	 * Better use an UUID VARCHAR field instead.
	 * 
	 * @param bigInt The big integer
	 * @return a long array containing the mostSigBits (up) at the index 0, and the leastSigBits (id) at the index 1;
	 */
	public static final long[] bigIntegerToLong2(BigInteger bigInt) {
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
	 * and build a common index for ID purposes, because this index has some unexpected behaviors.
	 * Better use an UUID VARCHAR field instead.
	 * 
	 * @param uuid The UUID to convert
	 * @return a long array containing the mostSigBits (up) at the index 0, and the leastSigBits (id) at the index 1;
	 */
	public static final long[] uuidToLong2(UUID uuid) {
		return new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()};
	}
	
	/**
	 * Creates a UUID from the values
	 * 
	 * @param mostSigBits The value of the most significant bits (up)
	 * @param leastSigBits The value of the least significant bits (id)
	 * @return The UUID
	 */
	public static final UUID long2ToUuid(long mostSigBits, long leastSigBits) {
		return new UUID(mostSigBits, leastSigBits);
	}

}
