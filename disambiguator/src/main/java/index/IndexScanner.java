package index;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import daos.Mentioned;

public class IndexScanner {
	public static Logger logger = Logger.getLogger(IndexScanner.class);
	public static String INDEX_FOLDER = "d:/influx.result.index/";
	private Directory dir = null;
	private IndexReader reader = null;
	private IndexSearcher searcher = null;
	
	public IndexScanner(){
		File indexfolder = new File(INDEX_FOLDER);
		try {
			dir = FSDirectory.open(indexfolder);
			reader = IndexReader.open(dir);
			searcher = new IndexSearcher(dir);
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
	
	public void test(String thisname){
		Term noselfterm = new Term(Fields.PROJECT_REAL_NAME, thisname);
		Term sfterm = new Term(Fields.SF_SUMMARY, thisname);
		Term fmterm = new Term(Fields.FM_SUMMARY, thisname);
		Term owterm = new Term(Fields.OW2_SUMMARY, thisname);
		TermQuery noselfquery = new TermQuery(noselfterm);
		TermQuery sfquery = new TermQuery(sfterm);
		TermQuery fmquery = new TermQuery(fmterm);
		TermQuery owquery = new TermQuery(owterm);
		BooleanQuery mainQuery = new BooleanQuery();
		mainQuery.add(noselfquery, Occur.MUST_NOT);
		mainQuery.add(sfquery, Occur.SHOULD);
		mainQuery.add(fmquery, Occur.SHOULD);
		mainQuery.add(owquery, Occur.SHOULD);
		// fetch all the hits
		TotalHitCountCollector counter = new TotalHitCountCollector();
		try {
			searcher.search(mainQuery, counter);
			int total = counter.getTotalHits();
			if (total <= 0)
				return;
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					total, true);
			searcher.search(mainQuery, collector);
			TopDocs topDocs = collector.topDocs();
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			Document d = searcher.doc(0);
			String thatname = d.get(Fields.PROJECT_REAL_NAME);
			/*for (ScoreDoc sdoc : scoreDocs) {
				logger.info(thisname +" : "+ sdoc+" OK ");
				//here something wrong with the index file, string decoding, out of java space error
				Document d = searcher.doc(sdoc.doc);
				String thatname = d.get(Fields.PROJECT_REAL_NAME);
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args){
		IndexScanner scanner = new IndexScanner();
		scanner.test("use");
	}
}
