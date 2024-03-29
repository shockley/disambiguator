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
	
	private Directory dir = null;
	private IndexSearcher searcher = null;
	private HibernateService hs = DataSourceFactory.getHibernateInstance();
	public void idfScanner(){
		
	}
	
	/**
	 * scan them to find out all the mentions. 
	 * Specifically, we fetch the realnames (from db) in an n-fold manner
	 * in which n is specified by the @param n; 
	 * and use each of them as keyword to 
	 * hit the SUMMARY fields of index file, and record the PROJECT_REAL_NAME field of the hits
	 */
	public void scanThemAll(int n) {
			// List<Project> projects = new ArrayList<Project>();
			List<String> allRealnames = null;
			Session session = hs.getSession();
			Transaction tx = session.beginTransaction();
			String thisname = null;
			
			//count the project number
			String hql = "select count(obj) from Project obj";
			Query q = session.createQuery(hql);
			List resultList = q.list();
			if(resultList==null || resultList.size()!=1){
				logger.error("Wrong result list returned when doing sql query!");
			}
			long count = (Long) resultList.get(0);
			tx.commit();
			for(long j = 0; j <= n; j++){
				//each fold a transaction
				tx = session.beginTransaction();
				long loopbegin =  j *(count/n)+1;
				long loopend;
				if(n!=j){
					loopend = (j+1)*(count/n);
				}else{
					loopend = count;
				}
				//database tuple id is 1-based, so 'loopbegin+1'
				for(long i = loopbegin; i<=loopend; i++){
					if(i%1000==0)
						logger.warn("We've scanned " +i+ " realnames");
					hql = "select obj.realname from Project obj where obj.id = " + i;
					q = session.createQuery(hql);
					resultList = q.list();
					if(resultList==null || resultList.size()!=1){
						logger.error("Wrong result list returned when doing sql query");
					}
					thisname = (String) resultList.get(0);
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
							logger.info(thisname +" : "+ sdoc+" OK ");
							//here something wrong with the index file, string decoding, out of java space error
							Document d = searcher.doc(sdoc.doc);
							Long mentiond_in = Long.valueOf(d.get(Fields.PROJECT_ID));
							String forge = d.get(Fields.PROJECT_FORGE);
							String context = "";
							
							if(forge.equals("sourceforge")){
								context = d.get(Fields.SF_SUMMARY);
							}else if(forge.equals("ow2")){
								context = d.get(Fields.FM_SUMMARY);
							}else if(forge.equals("freshmeat")){
								context = d.get(Fields.OW2_SUMMARY);
							}
							Mentioned mention = new Mentioned();
							mention.setRealname(thisname);
							mention.setMentionedIn(mentiond_in);
							mention.setContext(context);
							//Here appears the problem
							//be aware!! nested tx appeared
							hs.addTuple(mention);
						}//end of each realname's match-list
					} catch (Exception e) {
						e.printStackTrace();
					}
				}// end of each loop
				tx.commit();
			}// end of each fold
			session.close();
			
	}

	public DescriptionScanner() {
		super();
		File path = new File(StaticVariables.ORG_INDEX_FOLDER);
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
		scanner.scanThemAll(100);
	}
}
