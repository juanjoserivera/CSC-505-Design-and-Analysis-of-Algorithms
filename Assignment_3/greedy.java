import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class greedy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		// reading first element size
		int n = scan.nextInt();
		// creating array of n elements and loading that to and array of n integers
		long[] inputSequence = new long[n + 1];
		for (int i = 0; i < n + 1; i++) {// complexity n
			inputSequence[i] = scan.nextInt();
		}
		scan.close();

		long cost = 0;


		cost = greedyCost(inputSequence, 1, n);

		System.out.println(cost);

	}

	public static long greedyCost(long[] inputSequence, int mi, int mj) {
		long iterationCost = 0;
		long minCost = 0;
		long leftKCost = 0;
		long rightKCost = 0;
		int mk = 0;

		if (mi >= mj) {

			return 0;

		} else {

			for (int i = mi; i < mj; i++) {

				if (i == mi) {
					minCost = inputSequence[mi - 1] * inputSequence[i] * inputSequence[mj];
					mk = i;

				} else {
					iterationCost = inputSequence[mi - 1] * inputSequence[i] * inputSequence[mj];

					if (iterationCost < minCost) {

						minCost = iterationCost;
						mk = i;
					}

				}

			}


			if (mk > 0) {

				leftKCost = greedyCost(inputSequence, mi, mk);


				rightKCost = greedyCost(inputSequence, mk + 1, mj);

			}
			minCost = minCost + leftKCost + rightKCost;

		}

		return minCost;

	}

}
