package org.i3xx.util.qtuple.impl;

import static org.junit.Assert.*;

import org.i3xx.util.qtuple.impl.Tuple.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpaceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testK() {
		
		Parser p = new Parser(" & ( key1 = \"hallo\" , key2 = \"welt\" ) ");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.AND );
		assertEquals( t.getLeft().getType(), Type.LEAF );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.LEAF );
		assertEquals( t.getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().toString(), "welt" );
	}

}
