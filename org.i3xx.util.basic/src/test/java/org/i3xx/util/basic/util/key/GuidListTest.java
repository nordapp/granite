package org.i3xx.util.basic.util.key;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GuidListTest {

	@Test
	public void testA() {
		
		Long uuidA = new Long(KeyTool.random().getMostSignificantBits());
		Long uuidB = new Long(KeyTool.random().getMostSignificantBits());
		Long uuidC = new Long(KeyTool.random().getMostSignificantBits());
		Long uuidD = new Long(KeyTool.random().getMostSignificantBits());
		Long uuidE = new Long(KeyTool.random().getMostSignificantBits());
		
		GuidList list = new GuidList()
				.add(uuidA)
				.add(uuidB)
				.add(uuidC)
				.add(uuidD)
				.add(uuidE);
		
		Long[] uno = new Long[]{uuidA, uuidB, uuidC, uuidD, uuidE};
		String due = uuidA+","+uuidB+","+uuidC+","+uuidD+","+uuidE;
		
		assertArrayEquals( list.toArray(), uno );
		assertEquals( list.toString(), due );
		assertTrue( list.equals( GuidList.of(uno) ) );
		assertTrue( list.equals( GuidList.of(due) ) );
	}
}
