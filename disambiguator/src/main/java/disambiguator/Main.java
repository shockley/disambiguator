package disambiguator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class Main {
	
	public static void main(String [] args){
		//Definitions
		int MAX = 100;
		
		
		//Get files
		File path = new File("src/main/resources/corpus/");
		FilenameFilter myfilter = new FilenameFilter(){
			public boolean accept(File dir, String str){
				if(str.startsWith("[WIKI.TEST]"))
					return true;
				return false;
			}
		};
		File [] docFiles = path.listFiles(myfilter);
		String [] corpus = new String[docFiles.length];
		//Read files so to get the corpus
		BufferedReader reader;
		int i = 0;
		for(File docFile : docFiles){
			try {
				reader = new BufferedReader(new FileReader(docFile));
				StringBuilder sb = new StringBuilder();
				sb.append(reader.readLine());
				String docString = sb.toString();
				//If empty file occurs, we add an empty doc
				corpus[i++] = docString;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//Use initor to build V and corpus numeric vector
		DocInitializor initor = new DocInitializor();
		
		initor.buildV(corpus);
		initor.buildCorpus(corpus);
		int V = initor.getVocabulary().size();
		int[][] documents = initor.getCorpus();
		
		int M = documents.length;
		// # topics
        int K = documents.length;
        // good values alpha = 2, beta = .5
        double alpha = 2;
        double beta = .5;

        System.out.println("Latent Dirichlet Allocation using Gibbs Sampling.");

        LdaGibbsSampler lda = new LdaGibbsSampler(documents, V);
        lda.configure(10000, 2000, 100, 10);
        lda.gibbs(K, alpha, beta);
        
        
        
        double[][] theta = lda.getTheta();
        double[][] phi = lda.getPhi();

        System.out.println();
        System.out.println();
        System.out.println("Document--Topic Associations, Theta[d][k] (alpha="
            + alpha + ")");
        System.out.print("d\\k\t");
        for (int m = 0; m < theta[0].length; m++) {
            System.out.print("   " + m % 10 + "    ");
        }
        System.out.println();
        for (int m = 0; m < theta.length; m++) {
            System.out.print(m + "\t");
            for (int k = 0; k < theta[m].length; k++) {
                // System.out.print(theta[m][k] + " ");
                System.out.print(lda.shadeDouble(theta[m][k], 1) + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Topic--Term Associations, Phi[k][w] (beta=" + beta
            + ")");

        System.out.print("k\\w\t");
        for (int w = 0; w < phi[0].length; w++) {
            System.out.print("   " + w % 10 + "    ");
        }
        System.out.println();
        for (int k = 0; k < phi.length; k++) {
            System.out.print(k + "\t");
            for (int w = 0; w < phi[k].length; w++) {
                // System.out.print(phi[k][w] + " ");
                System.out.print(lda.shadeDouble(phi[k][w], 1) + " ");
            }
            System.out.println();
        }
        
        int [][] z = lda.getZ();
        System.out.println("Word--Topic Assignment, z[m][n]");
        for (int m = 0; m < z.length; m++) {
            System.out.print(m + "\t");
            for (int n = 0; n < z[m].length; n++) {
                // System.out.print(phi[k][w] + " ");
                System.out.print(z[m][n] + " ");
            }
            System.out.println();
        }
	}
}
