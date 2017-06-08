package org.i3xx.util.basic.util.key;
import static org.junit.Assert.*;

import org.i3xx.util.basic.util.key.KeyTool;
import org.i3xx.util.basic.util.key.UuidList;
import org.junit.Test;

public class UuidListTest {

	@Test
	public void testA() {
		
		String uuidA = KeyTool.random().toString();
		String uuidB = KeyTool.random().toString();
		String uuidC = KeyTool.random().toString();
		String uuidD = KeyTool.random().toString();
		String uuidE = KeyTool.random().toString();
		
		UuidList list = new UuidList()
				.add(uuidA)
				.add(uuidB)
				.add(uuidC)
				.add(uuidD)
				.add(uuidE);
		
		String[] uno = new String[]{uuidA, uuidB, uuidC, uuidD, uuidE};
		String due = uuidA+","+uuidB+","+uuidC+","+uuidD+","+uuidE;
		
		assertArrayEquals( list.toArray(), uno );
		assertEquals( list.toString(), due );
		assertTrue( list.equals( UuidList.of(uno) ) );
		assertTrue( list.equals( UuidList.of(due) ) );
	}
}
