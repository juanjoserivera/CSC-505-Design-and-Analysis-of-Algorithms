import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class dp {

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

		// matrix that contains the cost of multiply matrix
		long[][] m = new long[n + 1][n + 1];

		int j = 0;

		// loop that fill each diagonal on m
		for (int deltai = 0; deltai < n + 1; deltai++) {
			for (int i = 1; i < n - deltai + 1; i++) {
				j = i + deltai;

				if (deltai == 0) {// filling diagonal with 0
					m[i][j] = 0;

				} else {

					m[i][j] = getMinCost(inputSequence, m, i, j);
				}

			}
		}
		System.out.println(m[1][n]);
	}

	public static long getMinCost(long[] inputSequence, long[][] m, int i, int j) {
		long minCost = 0;
		long iterationCost = 0;
		long outerCost = 0;

		for (int k = i; k < j; k++) {

			outerCost = inputSequence[i - 1] * inputSequence[k] * inputSequence[j];
			
			if(k==i) {
				minCost=m[i][k]+m[k+1][j] +outerCost;
				
			}else {
				iterationCost=m[i][k]+m[k+1][j] +outerCost;
				if (iterationCost < minCost) {

					minCost = iterationCost;
				}

			}


		}

		return minCost;
	}

}
