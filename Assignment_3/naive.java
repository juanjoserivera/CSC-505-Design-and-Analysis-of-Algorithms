import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class naive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		// reading first element size
		int n = scan.nextInt();
		// creating array of n elements and loading that to and array of n integers
		long[] inputSequence = new long[n+1];
		for (int i = 0; i < n+1; i++) {// complexity n
			inputSequence[i] = scan.nextInt();
		}
		scan.close();
		
		long cost =0;
		 
		
		
		for(int i =2; i<n+1;i++) {
			
			cost = cost + (inputSequence[0]*inputSequence[i-1]*inputSequence[i]) ;
			
		}
		System.out.println(cost);

	}

}
