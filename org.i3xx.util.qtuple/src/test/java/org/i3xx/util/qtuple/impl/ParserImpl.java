package org.i3xx.util.qtuple.impl;

import static org.junit.Assert.*;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.i3xx.util.qtuple.impl.Tuple.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(ParserImpl.class);
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		URL url = ClassLoader.getSystemClassLoader().getResource("Log4j.properties");
		PropertyConfigurator.configure(url);
		logger.info("The logger started {}", url);
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testA() {
		
		Parser p = new Parser("&(key1=hallo,key2=welt,key3=test)");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.AND );
		assertEquals( t.getLeft().getType(), Type.AND );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getLeft().getRight().toString(), "welt" );
		
		assertEquals( t.getRight().getRight().getType(), Type.AND );
		assertEquals( t.getRight().getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getRight().getLeft().toString(), "key3" );
		assertEquals( t.getRight().getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().getRight().toString(), "test" );
	}

	@Test
	public void testB() {
		
		Parser p = new Parser("|(key1=hallo,key2=welt,key3=test)");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.OR );
		assertEquals( t.getLeft().getType(), Type.OR );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.OR );
		assertEquals( t.getRight().getLeft().getType(), Type.OR );
		assertEquals( t.getRight().getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getLeft().getRight().toString(), "welt" );
		
		assertEquals( t.getRight().getRight().getType(), Type.OR );
		assertEquals( t.getRight().getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getRight().getLeft().toString(), "key3" );
		assertEquals( t.getRight().getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().getRight().toString(), "test" );
	}

	@Test
	public void testC() {
		
		Parser p = new Parser("&(key1=hallo,key2=welt,!(key3=test))");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.AND );
		assertEquals( t.getLeft().getType(), Type.AND );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getLeft().getRight().toString(), "welt" );
		
		assertEquals( t.getRight().getRight().getType(), Type.NOT );
		assertEquals( t.getRight().getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getRight().getLeft().toString(), "key3" );
		assertEquals( t.getRight().getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().getRight().toString(), "test" );
	}

	@Test
	public void testD() {
		
		Parser p = new Parser("&(!(key1=hallo),key2=welt,key3=test)");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.AND );
		assertEquals( t.getLeft().getType(), Type.NOT );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getLeft().getRight().toString(), "welt" );
		
		assertEquals( t.getRight().getRight().getType(), Type.AND );
		assertEquals( t.getRight().getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getRight().getLeft().toString(), "key3" );
		assertEquals( t.getRight().getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().getRight().toString(), "test" );
	}

	@Test
	public void testE() {
		
		Parser p = new Parser("|(!(key1=hallo),&(key2=welt,key3=test))");
		Tuple t = p.getRoot();
		
		assertEquals( t.getType(), Type.OR );
		assertEquals( t.getLeft().getType(), Type.NOT );
		assertEquals( t.getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getLeft().getLeft().toString(), "key1" );
		assertEquals( t.getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getLeft().getRight().toString(), "hallo" );
		
		assertEquals( t.getRight().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getType(), Type.AND );
		assertEquals( t.getRight().getLeft().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getLeft().getLeft().toString(), "key2" );
		assertEquals( t.getRight().getLeft().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getLeft().getRight().toString(), "welt" );
		
		assertEquals( t.getRight().getRight().getType(), Type.AND );
		assertEquals( t.getRight().getRight().getLeft().getType(), Type.KEY );
		assertEquals( t.getRight().getRight().getLeft().toString(), "key3" );
		assertEquals( t.getRight().getRight().getRight().getType(), Type.VALUE );
		assertEquals( t.getRight().getRight().getRight().toString(), "test" );
	}

}
