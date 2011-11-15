package index;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexScanner {
	public static Logger logger = Logger.getLogger(IndexScanner.class);
	public static String INDEX_FOLDER = "d:/influx.result.index/";
	private Directory dir = null;
	private IndexReader reader = null;
	public IndexScanner(){
		File indexfolder = new File(INDEX_FOLDER);
		try {
			dir = FSDirectory.open(indexfolder);
			reader = IndexReader.open(dir);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void scanit(){
		int maxdoc = reader.maxDoc();
		Document d;
		logger.warn("max doc = "+maxdoc);
		for(int i = 0; i< maxdoc;i++){
			try {
				d = reader.document(i);
			} catch (CorruptIndexException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info(""+i+" OK");
		}
		logger.warn("all OK!");
	}
	
	public static void main(String [] args){
		IndexScanner scanner = new IndexScanner();
		scanner.scanit();
	}
}
