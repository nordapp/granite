package org.i3xx.util.qtuple.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ResolverImpl {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testA() {
		
		Parser p = new Parser("&(key1=hallo,key2=welt)");
		Resolver r = new Resolver(p.getRoot(), null);
		assertTrue( r.resolve(getParams()) );
	}

	@Test
	public void testB() {
		
		Parser p = new Parser("&(key1=hallo,key2~welt)");
		Resolver r = new Resolver(p.getRoot(), null);
		assertTrue( r.resolve(getParams()) );
	}

	@Test
	public void testC() {
		
		Parser p = new Parser("&(key1=hallo,key2~\"wel.\")");
		Resolver r = new Resolver(p.getRoot(), null);
		assertTrue( r.resolve(getParams()) );
	}
	
	/**
	 * @return
	 */
	private Map<String, String> getParams() {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("key1", "hallo");
		params.put("key2", "welt");
		params.put("key3", "some");
		params.put("key4", "other");
		params.put("key5", "value");
		
		return params;
	}
	
}
