package test.algorithm;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * This class is to test the n-fold manner being used to load the realnames 
 * @author Shockley
 *
 */
public class NfoldLoopTest {
	private long count;
	private long n;
	@Before public void setUp(){

	}
	/**
	 * To test the validity of the n-fold manner, 
	 * the satisfaction of the following three conditions is neccessary and also enough:
	 * 1.first loopbegin == 1
	 * 2.each loopbegin == previous loopend + 1
	 * 3.last loopend == count  
	 */
	@Test public void loopTest(){
		long loopbegin;
		long loopend;
		long previousloopend;
		Random r = new Random();
		for(int i=0;i<100;i++){
			previousloopend = 0;
			count = 101 + r.nextInt(500000);
			n = 1 + r.nextInt(100);
			for(long j = 0; j <= n; j++){			
				loopbegin =  j *(count/n)+1;
				if(j!=n){
					loopend = (j+1)*(count/n);
				}else{
					loopend = count;
				}
				//the test part
				if(j==0){
					assertTrue(loopbegin==1);
				}
				assertTrue(previousloopend+1==loopbegin);
				if(j==n){
					assertTrue(loopend==count);
				}
				//
				previousloopend = loopend;
			}
			
		}
	}
}
