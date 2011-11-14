package disambiguator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import preprocessor.StopwordsEnglish;

/**
 * The class to intialize vocabulary vector and corpus vectors
 * @author Shockley, CS, N.U.D.T.
 * @email lixiang41@nudt.edu.cn
 *
 */
public class DocInitializor {
	public static Logger logger = Logger.getLogger(DocInitializor.class);
	public static int MAX_WORD_PER_DOC = 5000;
	public static int MAX_DOC = 10;
	private int [][] corpus;
	private List<String> vocabulary = new ArrayList<String>();
	
	

	private StopwordsEnglish stopword = new StopwordsEnglish();
	
	/**
	 * Build the vocabulary, including tokenizing and stop-word-removal
	 * @param allDocs : this can have null elements in its tail
	 */
	public void buildV(String [] allDocs){
		for(String docString : allDocs){
			if(docString==null) break;
			StringTokenizer tokenizer = new StringTokenizer(docString);
			String w = null;
			if(!tokenizer.hasMoreTokens()){
				logger.warn("Empty doc encountered!");
			}else{
				w = tokenizer.nextToken();
			}
			for(;tokenizer.hasMoreTokens(); w=tokenizer.nextToken()){
				if(!vocabulary.contains(w) && !stopword.isStopword(w)){
					vocabulary.add(w);
				}
			}
		}
	}
	
	/**
	 * Build the numeric representation of the corpus
	 * 
	 * @param allDocs : we are extra careful and assumes this can have null elements in its tail
	 */
	public void buildCorpus(String [] allDocs){
		int i = 0;
		int rowNum = allDocs.length;
		
		for(int k =0; k<allDocs.length; k++){
			if(allDocs[k] == null){
				rowNum = k;
				break;
			}
		}
		corpus = new int[rowNum][];
		for(int k =0; k<rowNum; k++){
			StringTokenizer tokenizer = new StringTokenizer(allDocs[k]);
			
			//this 
			int columnNum = tokenizer.countTokens();
			if(columnNum==0){
				logger.warn("Empty doc encountered!");
				corpus[i++] = new int[0];
				break;
				//then we add an empty vector
			}
			int [] doc = new int[columnNum];
			int j=0;
			String w = tokenizer.nextToken();
			for(;tokenizer.hasMoreTokens(); w=tokenizer.nextToken()){
				if(vocabulary.contains(w)){
					if(j>=doc.length){
						//this won't happen
						logger.warn("Please set a larger value for max-word-per-doc!");
						break;
					}	
					doc[j++] = vocabulary.indexOf(w);
				}
			}
			corpus[i++] = doc;
		}
	}

	
	/**
	 * @return
	 */
	public int[][] getCorpus() {
		return corpus;
	}

	public void setCorpus(int[][] corpus) {
		this.corpus = corpus;
	}

	public List<String> getVocabulary() {
		return vocabulary;
	}

	public void setVocabulary(List<String> vocabulary) {
		this.vocabulary = vocabulary;
	}
}
