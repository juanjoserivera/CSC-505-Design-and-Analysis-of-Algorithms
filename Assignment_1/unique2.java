import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author Juan Jose Rivera
 *
 */
public class unique2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//final long startTime = System.currentTimeMillis();

		// reading numbers
		Scanner scan = new Scanner(System.in);
		// reading first element size
		int n = scan.nextInt();
		// creating arry of n elements and loading that to and array of n integers
		int[] inputSequence = new int[n];
		for (int i = 0; i < n; i++) { // complexity n
			inputSequence[i] = scan.nextInt();
		}
		scan.close();
		int indexSequence = 0;
		int lengthSequence = 0;
		boolean duplicate = false;

		TreeMap<Integer, Integer> subSequenceTmap = new TreeMap<Integer, Integer>();

		for (int start = 0; start < n - 1; start++) { // complexity n
			duplicate = false;
			subSequenceTmap.clear();

			for (int subSeqLength = 1; subSeqLength < n + 1 - start && duplicate == false; subSeqLength++) { // complexity n
																												 

				int newValue = inputSequence[start + subSeqLength - 1];

				if (!subSequenceTmap.containsKey(newValue)) { // complexity logn
					subSequenceTmap.put(newValue, newValue); // complexity logn
				} else {
					duplicate = true;

				}

			}

			if (lengthSequence < subSequenceTmap.size()) {
				indexSequence = start;
				lengthSequence = subSequenceTmap.size();

			}

		}

		// total complexity then : n^2(lg n)
		System.out.println(indexSequence + " " + lengthSequence);

		//final long endTime = System.currentTimeMillis();

		//System.out.println("Total execution time: " + (endTime - startTime));

	}

}
