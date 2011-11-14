package datachecker;
/**
 * Some project real name is null due to previous incompleteness in crawling,
 * we omit them
 *
 *
 *
 *
 */
import index.Fields;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import daos.Mentioned;
import dataconns.DataSourceFactory;
import dataconns.HibernateService;

public class DescriptionScanner {
	private Logger logger = Logger.getLogger(DescriptionScanner.class);

	/**
	 * In this kind of index, each project real name has an index document
	 */
	public static String INDEX_FOLDER = "d:/influx.result.index/";
	private Directory dir = null;
	private IndexSearcher searcher = null;

	
	public void scanThemAll() {

			// List<Project> projects = new ArrayList<Project>();
			HibernateService hs = DataSourceFactory.getHibernateInstance();
			List<String> allRealnames = null;
			Session session = hs.getSession();
			Transaction tx = session.beginTransaction();
			
			//these parameters are for logging
			String thatname = null;
			String thisname = null;
			Document d = null;
			ScoreDoc scoreDoc = null;
			//
			
			String hql = "select distinct obj.realname from Project obj";
			Query q = session.createQuery(hql);
			allRealnames = q.list();
			int totalrealname = allRealnames.size();
			for (int i = 0; i<totalrealname;i++ ) {
				// a project could be mentioned in either of the 3 summaries
				//log every 1000 iterations
				if(i%1000==0)
					logger.info("We've scanned " +i+ " realnames");
				
				thisname = allRealnames.get(i);
				if(thisname == null) continue;
				
				
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
						continue;
					TopScoreDocCollector collector = TopScoreDocCollector.create(
							total, true);
					searcher.search(mainQuery, collector);
					TopDocs topDocs = collector.topDocs();
					ScoreDoc[] scoreDocs = topDocs.scoreDocs;
					for (ScoreDoc sdoc : scoreDocs) {
						scoreDoc = sdoc;
						d = searcher.doc(scoreDoc.doc);
						thatname = d.get(Fields.PROJECT_REAL_NAME);
						Mentioned mention = new Mentioned();
						mention.setRealname(thisname);
						mention.setMentionedIn(thatname);
						session.save(mention);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error("that name "+ thatname);
					logger.error("this name "+ thisname);
					logger.error("document "+ d);
					logger.error("sdoc.doc "+ scoreDoc.doc);
					logger.error("Stack Tracse "+e.getStackTrace());
					e.printStackTrace();
				}
			}
			tx.commit();
			session.close();
		
	}

	public DescriptionScanner() {
		super();
		File path = new File(INDEX_FOLDER);
		if (path == null || !path.isDirectory()) {
			String warning = "Error : Index directory is missing!";
			logger.error(warning);
		}
		try {
			dir = FSDirectory.open(path);
			searcher = new IndexSearcher(dir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DescriptionScanner scanner = new DescriptionScanner();
		scanner.scanThemAll();
	}
}