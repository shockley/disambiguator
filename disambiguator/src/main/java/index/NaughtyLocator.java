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
import org.junit.Test;

public class NaughtyLocator {
	public static Logger logger = Logger.getLogger(NaughtyLocator.class);
	public static String INDEX_FOLDER = "d:/influx.result.index/";
	private Directory dir = null;
	private IndexSearcher searcher = null;
	
	public NaughtyLocator(){
		File indexfolder = new File(INDEX_FOLDER);
		try {
			dir = FSDirectory.open(indexfolder);
			searcher = new IndexSearcher(dir);
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document getNaughtyDoc(String thisname){
		Document d = null;
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
				return d;
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					total, true);
			searcher.search(mainQuery, collector);
			TopDocs topDocs = collector.topDocs();
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			d = searcher.doc(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

}
