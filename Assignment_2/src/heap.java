import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class heap {

	public static int branchingFactor;
	public static int bitBranchingFactor;
	public static int numKeyComparison;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Integer> heapArray = new ArrayList<Integer>();
		boolean badCommand = false;
		numKeyComparison = 0;

		if (args.length == 1) {
			try {
				branchingFactor = Integer.parseInt(args[0]);

				if ((branchingFactor & (branchingFactor - 1)) == 0) {
					// power of 2
					badCommand = false;
				} else {
					badCommand = true;
				}
			} catch (Exception e) {
				// TODO: handle exception
				badCommand = true;
			}

		} else if (args.length == 0) {
			branchingFactor = 2;

		} else if (args.length > 1) {
			badCommand = true;
		}

		if (badCommand) {
			System.out.println("Usage Error !!!");
			System.out.println("Usage: java heap [branching factor power of 2] < input_file.txt");
			System.exit(0);
		}

		bitBranchingFactor = (int) (Math.log(branchingFactor) / Math.log(2));

		Scanner scan = new Scanner(System.in);
		// reading first element size
		int n;
		int value;
		while (scan.hasNextInt()) {
			n = scan.nextInt();
			if (n >= 0) {
				value = scan.nextInt();
				insertValue(heapArray, n, value);

			} else {

				removeMin(heapArray);
				System.out.println(MinKeyValue.key + " " + MinKeyValue.value);
			}

		}
		scan.close();
		System.out.println("key comparisons: " + numKeyComparison);

	}

	public static boolean insertValue(ArrayList<Integer> heap, int key, int value) {
		boolean result = false;
		int heapSize;
		int parentIndex;
		int childIndex;
		boolean stop = false;
		heap.add(key);
		heap.add(value);

		heapSize = heap.size();
		// validate if parent is greater and bubble up key until find position
		childIndex = heapSize - 2;
		parentIndex = getParent(childIndex);

		while (parentIndex >= 0 && stop == false) {
			numKeyComparison++;
			if (heap.get(childIndex) < heap.get(parentIndex)) {
				swap(heap, parentIndex, childIndex);
				childIndex = parentIndex;
				parentIndex = getParent(childIndex);
			} else {
				stop = true;
			}
		}
		return result;
	}

	public static boolean removeMin(ArrayList<Integer> heap) {
		// replace first with last, remove last and apply heapify to root

		boolean result = false;
		int heapSize = heap.size();

		if (!heap.isEmpty()) {

			swap(heap, 0, heapSize - 2);

			MinKeyValue.value = heap.remove(heapSize - 1);
			MinKeyValue.key = heap.remove(heapSize - 2);

			heapify(heap, 0);
		}
		return result;

	}

	public static boolean heapify(ArrayList<Integer> heap, int index) {
		boolean result = false;

		int minIndex = getMinChildKey(heap, index);

		if ((minIndex > 0) && (minIndex < heap.size())) {

			numKeyComparison++;

			if (heap.get(index) > heap.get(minIndex)) {
				swap(heap, index, minIndex);
				heapify(heap, minIndex);
			}
		}
		return result;
	}

	public static int getParent(int index) {
		int parentIndex = 0;

		parentIndex = ((((index) >> 1) - 1) >> bitBranchingFactor) << 1;

		return parentIndex;
	}

	public static int getChildIndex(int index, int i, int heapSize) {
		int childIndex = -1;

		childIndex = (((index >> 1) << bitBranchingFactor) + i) << 1;

		if (childIndex > heapSize) {
			return -1;
		}

		return childIndex;
	}

	public static int getMinChildKey(ArrayList<Integer> heap, int index) {
		int minChildKey = -1;
		int childKey = 0;
		int heapSize = heap.size();
		boolean haveChildren = true;
		//

		minChildKey = getChildIndex(index, 1, heapSize);
		if (minChildKey < 0 || (minChildKey >= heapSize)) {
			haveChildren = false;
		}
		for (int i = 2; (i < branchingFactor + 1) && haveChildren == true; i++) {

			childKey = getChildIndex(index, i, heapSize);

			if (childKey < 0 || (childKey >= heapSize)) {
				haveChildren = false;
			} else {
				numKeyComparison++;
				if (heap.get(minChildKey) > heap.get(childKey)) {
					minChildKey = childKey;
				}
			}

		}

		return minChildKey;
	}

	public static void swap(ArrayList<Integer> heap, int index_i, int index_j) {
		int tempkey = heap.get(index_i);
		int tempValue = heap.get(index_i + 1);

		heap.set(index_i, heap.get(index_j));
		heap.set(index_i + 1, heap.get(index_j + 1));

		heap.set(index_j, tempkey);
		heap.set(index_j + 1, tempValue);

	}

	public static class MinKeyValue {
		public MinKeyValue() {
			// TODO Auto-generated constructor stub
		}

		static int key;
		static int value;

	}

}
