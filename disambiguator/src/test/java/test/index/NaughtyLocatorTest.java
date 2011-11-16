package test.index;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestSuite;

import org.apache.lucene.document.Document;
import org.junit.Before;
import org.junit.Test;

import datachecker.NaughtyLocator;

public class NaughtyLocatorTest {
	private NaughtyLocator locator;
	private String realnameMentionedByNaughty ;
	
	@Test public void testOutTheNaughty() throws IOException{
		Document d = locator.getNaughtyDoc(realnameMentionedByNaughty);
		assertTrue(d!=null);
	}

	@Before public void setUp() throws IOException{
		locator = new NaughtyLocator();
		realnameMentionedByNaughty = "use";
	}
	
	/**
     * @return the suite of tests being tested
     */
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( NaughtyLocatorTest.class);
    }
}
