import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Juan Jose Rivera
 *
 */
public class prim {
	public static int branchingFactor;
	public static int bitBranchingFactor;
	public static int numKeyComparison;
	public static ArrayList<Integer> vertexPosInHeap;
	public static ArrayList<Boolean> isPartOfTree;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int n, m;
		int vertex, adj, weight;
		double exactPower;
		ArrayList<HashMap<Integer, Integer>> graph = new ArrayList<HashMap<Integer, Integer>>();
		isPartOfTree = new ArrayList<Boolean>();
		vertexPosInHeap = new ArrayList<Integer>();
		ArrayList<Integer> heapArray = new ArrayList<Integer>();

		// loading graph
		Scanner scan = new Scanner(System.in);

		n = scan.nextInt();
		m = scan.nextInt();

		// calculating branching factor
		exactPower = (double) (Math.log((double) m / n) / Math.log(2));

		branchingFactor = (int) (Math.pow(2, (int) Math.ceil(exactPower)));
		if (branchingFactor < 2) {
			branchingFactor = 2;
		}

		bitBranchingFactor = (int) (Math.log(branchingFactor) / Math.log(2));

		// initializing graph and arrays

		for (int i = 0; i < n; i++) {
			HashMap<Integer, Integer> edge = new HashMap<Integer, Integer>();
			graph.add(edge);
			isPartOfTree.add(Boolean.FALSE);
			vertexPosInHeap.add(-1);
		}
		
		//filling up the graph

		while (scan.hasNextInt()) {

			vertex = scan.nextInt();
			adj = scan.nextInt();
			weight = scan.nextInt();

			//adding edges, undirected graph
			graph.get(vertex).put(adj, weight);
			graph.get(adj).put(vertex, weight);
		}

		scan.close();


		
		int sumWeights = 0;
		int numMST = 0;

		for (int i = 0; i < n; i++) {
			if (!isPartOfTree.get(i)) {
				insertValue(heapArray, 0, i);
				sumWeights = getMST(graph, heapArray) + sumWeights;
				numMST++;

			}
		}

		System.out.println(branchingFactor + " " + numMST + " " + sumWeights);

	}

	public static int getMST(ArrayList<HashMap<Integer, Integer>> graph, ArrayList<Integer> heap) {
		int sumWeights = 0;
		// get MST based on root that is already on heap

		while (!heap.isEmpty()) {
			
			// remove the vertex with smallest cost
		
			removeMin(heap);
		
			isPartOfTree.set(MinKeyValue.value, Boolean.TRUE);
			// when we remove that vertex is part of MST
			sumWeights = sumWeights + MinKeyValue.key;
			
			
			
			// after we remove we add adjacents to heap
			Iterator<Entry<Integer, Integer>> it = graph.get(MinKeyValue.value).entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, Integer> entry = it.next();

				int posInHeap = vertexPosInHeap.get(entry.getKey());

				if (!isPartOfTree.get(entry.getKey())) {//if vertex is not part of the tree
					
					// if vertex is not in heap and is not part of MST we add vertex to heap
					if (posInHeap == -1) {
						insertValue(heap, entry.getValue(), entry.getKey());

					} else {
						// if vertex is in heap we validate if this edge cost is less than the current
						// one
						if (entry.getValue() < heap.get(posInHeap * 2)) {
							// if is less we decrease the key of that vertex in heap
							decreaseKey(heap, entry.getValue(), entry.getKey());
						}

					}
				}


			}

		}
		return sumWeights;
	}

	public static boolean decreaseKey(ArrayList<Integer> heap, int key, int value) {
		boolean result = false;
		int parentIndex;
		int childIndex;
		boolean stop = false;

		// get index in heap
		childIndex = vertexPosInHeap.get(value) * 2;

		if (childIndex >= 0 && !heap.isEmpty()) {
			// update key in heap
			heap.set(childIndex, key);

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
		}

		return result;

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

		// updating position of vertex in heap
		vertexPosInHeap.set(value, childIndex / 2);

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

		MinKeyValue.value = -1;
		MinKeyValue.key = -1;

		if (!heap.isEmpty()) {

			swap(heap, 0, heapSize - 2);

			MinKeyValue.value = heap.remove(heapSize - 1);
			MinKeyValue.key = heap.remove(heapSize - 2);

			// updating position of vertex in heap, this element is not in the heap anymore
			vertexPosInHeap.set(MinKeyValue.value, -1);

			heapify(heap, 0);
			result = true;
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

		// updating vertex position in heap
		vertexPosInHeap.set(heap.get(index_i + 1), index_i / 2);
		vertexPosInHeap.set(heap.get(index_j + 1), index_j / 2);

	}

	public static class MinKeyValue {
		public MinKeyValue() {
			// TODO Auto-generated constructor stub
		}

		static int key;
		static int value;

	}

}
