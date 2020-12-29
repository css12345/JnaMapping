package com.exmaple.jna;

import junit.framework.TestCase;

public class SimpleJunitTest extends TestCase {
	public void testNothing() {
		assertTrue(true);
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(SimpleJunitTest.class);
	}
}
