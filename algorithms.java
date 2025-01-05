public class MelikeZeynepCakmakci {
	
	public static void main(String[] args) {
		vertexNumber = 8;
		weights = new int[vertexNumber][];
		queue = new int[vertexNumber];
		initDirectedWeights();
		bellmanFord();
		
		vertexNumber = 7;
		weights = new int[vertexNumber][];
		parents = new int[vertexNumber];
		initUndirectedWeights();
		prims();
	}
	
	static int vertexNumber;
	static int source;
	static int[][] weights;
	static int[] queue;
	static int[] key;
	static int[] parents;
	
	public static void initDirectedWeights() {
		for (int i = 0; i < weights.length; i++)
			weights[i] = new int[weights.length];
		weights[0][1] = 4;
		weights[0][2] = 2;
		weights[1][2] = 3;
		weights[1][3] = 2;
		weights[1][4] = 3;
		weights[2][1] = -1;
		weights[2][5] = 4;
		weights[3][5] = -3;
		weights[3][6] = 1;
		weights[4][6] = -2;
		weights[5][7] = 2;
		weights[6][5] = -2;
	}
	
	public static void relax(int weight, int from, int to) {
		if (weight == 0)
			return;
		if (queue[to] > queue[from] + weight)
			queue[to] = queue[from] + weight;
	}
	
	public static void initQueue() {
		for (int i = 0; i < queue.length; i++)
			queue[i] = 10000;
		queue[source] = 0;
	}
	
	public static boolean bellmanFord() {
		initQueue();
		for (int t = 1; t < vertexNumber-1; t++) {
			for (int i = 0; i < vertexNumber-1; i++) {
				for (int j = 0; j < vertexNumber; j++) {
					relax(weights[i][j], i, j);
				}
			}
		}
		for (int i = 0; i < vertexNumber-1; i++) {
			for (int j = 0; j < vertexNumber; j++) {
				if (weights[i][j] != 0 && queue[j] > queue[i] + weights[i][j]) {
					System.out.println("No solution!");
					return false;
				}
			}
		}
		System.out.println("Output of Bellman-ford:");
		printQueue(queue);
		return true;
	}
	
	public static void printQueue(int[] queue) {
		char vertex = 'A';
		for (int weight: queue)
			System.out.println(vertex++ + ":" + weight);
	}

	public static void initUndirectedWeights() {
		for (int i = 0; i < 7; i++)
			weights[i] = new int[7];
		weights[0][1] = weights[1][0] = 3;
		weights[0][2] = weights[2][0] = 1;
		weights[0][3] = weights[3][0] = 5;
		weights[1][2] = weights[2][1] = 6;
		weights[1][4] = weights[4][1] = 7;
		weights[2][3] = weights[3][2] = 2;
		weights[2][4] = weights[4][2] = 4;
		weights[3][5] = weights[5][3] = 8;
		weights[4][5] = weights[5][4] = 9;
		weights[4][6] = weights[6][4] = 2;
		weights[5][6] = weights[6][5] = 6;
	}
	
	public static void initKey() {
		for (int i = 0; i < key.length; i++)
			key[i] = 10000;
		key[source] = 0;
	}

	public static int extractMin() {
		int minVal = 10000;
		int minIndex = -1;
		for (int i = 0; i < vertexNumber; i++) {
			if (queue[i] == -1)
				continue;
			if (key[i] < minVal) {
				minIndex = i;
				minVal = key[i];
			}
		}
		queue[minIndex] = -1;
		return minIndex;
	}

	public static boolean isQueueEmpty() {
		for (int i = 0; i < vertexNumber; i++) {
			if (queue[i] == i)
				return false;
		}
		return true;
	}
	
	public static boolean inQueue(int index) {
		return (queue[index] != -1);
	}
	
	public static void prims() {
		queue = new int[vertexNumber];
		for (int i = 0; i < vertexNumber; i++)
			queue[i] = i;
		key = new int[vertexNumber];
		initKey();
		parents[source] = 0;
		while (!isQueueEmpty()) {
			int u = extractMin();
			for (int v = 0; v < vertexNumber;v++) {
				if (weights[u][v] != 0) {
					if (inQueue(v) && weights[u][v] < key[v]) {
						parents[v] = u;
						key[v] = weights[u][v];					
					}
				}
			}
		}
		System.out.println("Output of Prim's Algorithm:");
		printQueue(key);
	}
}
