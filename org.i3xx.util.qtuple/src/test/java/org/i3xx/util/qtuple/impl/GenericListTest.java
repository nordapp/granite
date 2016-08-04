package org.i3xx.util.qtuple.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenericListTest {
	
	private static List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		for(int i=0;i<0x10000;i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", Integer.toHexString(i));
			map.put("key1", Integer.toHexString(i&0xF));
			map.put("key2", Integer.toHexString(i&0xFF));
			map.put("key3", Integer.toHexString(i&0xFFF));
			
			data.add(map);
		}//for

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		data = null;
	}

	@Test
	public void testA() {
		assertEquals(data.size(), 65536);
		
		int a=0;
		Resolver r = new Resolver("key1=8");
		
		for(int i=0;i<data.size();i++) {
			Map<String, String> map = data.get(i);
			if( r.resolve(map) )
				a++;
		}//for
		
		assertEquals(a, 4096);
	}		

	@Test
	public void testB() {
		
		int a=0;
		Resolver r = new Resolver("key1=a");
		
		for(int i=0;i<data.size();i++) {
			Map<String, String> map = data.get(i);
			if( r.resolve(map) )
				a++;
		}//for
		
		assertEquals(a, 4096);
	}

	@Test
	public void testC() {
		
		int a=0;
		Resolver r = new Resolver("key2=aa");
		
		for(int i=0;i<data.size();i++) {
			Map<String, String> map = data.get(i);
			if( r.resolve(map) )
				a++;
		}//for
		
		assertEquals(a, 256);
	}

	@Test
	public void testD() {
		
		int a=0;
		Resolver r = new Resolver("key3=aaa");
		
		for(int i=0;i<data.size();i++) {
			Map<String, String> map = data.get(i);
			if( r.resolve(map) ) {
				a++;
			}
		}//for
		
		assertEquals(a, 16);
	}

	@Test
	public void testE() {
		
		int a=0;
		Resolver r = new Resolver("&(key1=a,key2=aa,key3=aaa)");
		
		for(int i=0;i<data.size();i++) {
			Map<String, String> map = data.get(i);
			if( r.resolve(map) )
				a++;
		}//for
		
		assertEquals(a, 16);
	}

}
