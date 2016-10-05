package org.i3xx.util.basic.util.key;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KeyToolTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testA() {
		
		UUID uuid = UUID.randomUUID();
		
		BigInteger bigInt = KeyTool.toBigInteger(uuid);
		UUID uui2 = KeyTool.toUuid(bigInt);
		
		//The assertion
		assertEquals(uuid.toString(), uui2.toString());
	}

	@Test
	public void testB() {
		
		byte[] buf = new byte[16];
		new Random().nextBytes(buf);
		
		BigInteger bigInt = new BigInteger(buf);
		
		UUID uuid = KeyTool.toUuid(bigInt);
		BigInteger bigIn2 = KeyTool.toBigInteger(uuid);
		
		// The assertion
		assertEquals(bigInt.toString(), bigIn2.toString());
	}

	@Test
	public void testC() {
		
		byte[] buf = new byte[16];
		new Random().nextBytes(buf);
		
		BigInteger bigInt = new BigInteger(buf);
		UUID uuid = KeyTool.toUuid(bigInt);
		
		//A test
		assertEquals(bigInt.toString(), KeyTool.toBigInteger(uuid).toString());
		
		long[] arr1 = KeyTool.toLong2(bigInt);
		long[] arr2 = KeyTool.toLong2(uuid);
		
		//The assertion
		assertArrayEquals( arr1, arr2 );
	}

	@Test
	public void testD() {
		
		Random r = new Random();
		long mostSigBits = r.nextLong();
		long leastSigBits = r.nextLong();
		
		BigInteger bigInt = KeyTool.toBigInteger(mostSigBits, leastSigBits);
		UUID uuid = KeyTool.toUuid(mostSigBits, leastSigBits);
		
		//A test
		assertEquals(bigInt.toString(), KeyTool.toBigInteger(uuid).toString());
		
		long[] arr1 = KeyTool.toLong2(bigInt);
		long[] arr2 = KeyTool.toLong2(uuid);
		
		//The assertion
		assertArrayEquals( arr1, arr2 );
	}

}
