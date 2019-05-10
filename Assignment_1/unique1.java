import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class unique1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//final long startTime = System.currentTimeMillis();

		// reading numbers
		Scanner scan = new Scanner(System.in);
		// reading first element size
		int n = scan.nextInt();
		// creating array of n elements and loading that to and array of n integers
		int[] inputSequence = new int[n];
		for (int i = 0; i < n; i++) {// complexity n
			inputSequence[i] = scan.nextInt();
		}
		scan.close();

		int indexMaxSequence = 0;
		int lenghtMaxSequence = 0;
		boolean duplicate = false;
		int currentSubSeqLen = 0;

		for (int start = 0; start < n - 1; start++) {//complexity n
			duplicate = false;
			currentSubSeqLen = 0;
			for (int subSeqLength = 1; subSeqLength < n + 1 - start && duplicate == false; subSeqLength++) {//complexity n

				int newValue = inputSequence[start + subSeqLength - 1];

				
				for(int it= start;it< start + subSeqLength-1 && duplicate ==false;it++) {//complexity n
					if (inputSequence[it]==newValue) {
						duplicate = true;
					}
				}
				
				if (!duplicate) {
					currentSubSeqLen=subSeqLength;
					
				}

			}

			if (lenghtMaxSequence < currentSubSeqLen) {
				indexMaxSequence = start;
				lenghtMaxSequence = currentSubSeqLen;

			}

		}
		
		//total complexity n^3

		System.out.println(indexMaxSequence + " " + lenghtMaxSequence);

		//final long endTime = System.currentTimeMillis();

		//System.out.println("Total execution time: " + (endTime - startTime));

	}

}
