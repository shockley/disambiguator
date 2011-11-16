package test.index;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import datachecker.StaticVariables;

public class IndexTester {
	public static String INDEX_FOLDER = "d:/influx.result.index/";
	private Directory dir = null;
	private IndexReader reader = null;

	@Test public void scanAllIndexTest() throws CorruptIndexException, IOException{
		int maxdoc = reader.maxDoc();
		Document d;
		for(int i = 0; i< maxdoc;i++){
			d = reader.document(i);
			assertTrue(d!=null);
		}
	}

	@Before public void setUp() throws IOException{
		File indexfolder = new File(StaticVariables.ORG_INDEX_FOLDER);
		dir = FSDirectory.open(indexfolder);
		reader = IndexReader.open(dir);
	}
}
