import java.util.ArrayList;
//import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class BFSPaths<E extends Station> {
	private Map<Vertex<Station>, Boolean> marked; 
	private Set<Vertex<Station>> closedSet;
	private Map<Vertex<Station>, Vertex<Station>> cameFrom; //edgeTo - parent node of this vertex
	private Vertex<Station> s; //source node/vertex
	private Vertex<Station> t; //sink node/vertex
	private Map<Vertex<Station>, Integer> gScoreMap; //edgeTo - parent node of this vertex
//	private Map<Vertex<Station>, Integer> fScoreMap; 
	private Graph<Station> g;
	private int pathNumVertices;
	private int verticesExpanded;
	//How to abstract with strategy pattern?
	
	/*
	 *Store the successors of a node in an ArrayList and make sure they are sorted in alphabetical order of the city name before adding them to the Queue 
	 * 
	 */
	private Comparator<Vertex<Station>> cmp = new Comparator<Vertex<Station>>()
	 {
			@Override
			public int compare(Vertex<Station> o1, Vertex<Station> o2) {
				int gScore1 = gScoreMap.get(o1);
				int heur1 = g.getHeuristicVal(o1);
				int total1 = gScore1 + heur1;
				int gScore2 = gScoreMap.get(o2);
				int heur2 = g.getHeuristicVal(o2);
				int total2 = gScore2 + heur2;

				int result = (Integer.compare(total1, total2));
				return result;
			} 
	 };


	public BFSPaths (Graph<Station> g, Vertex<Station> s, Vertex<Station> t){
		this.closedSet = new HashSet<>();
		this.cameFrom = new HashMap<>();
		this.g = g;
		this.s = s;
		this.t= t;
		this.pathNumVertices = 0;
		this.verticesExpanded = 0;
		gScoreMap = new HashMap<>(); //edgeTo - parent node of this vertex
//		fScoreMap = new HashMap<>(); 
		for (Vertex<Station> v : g.getVertexList()) {
			gScoreMap.put(v, Integer.MAX_VALUE);
//			fScoreMap.put(v, Integer.MAX_VALUE);
		}
		bfs(g, s, t);
	}
	
	/*
	BFSPaths<Station> srch = new BFSPaths<>(g, g.getVertex(new Station("Arad", 10)), new Comparator<Vertex<Station>>() {
		@Override
		public int compare(Vertex<Station> o1, Vertex<Station> o2) {
//					return o1.getTo().getContents().compareTo(o2.getTo().getContents()); //compare destination contents
			return -(Integer.compare(g.getFScoreVal(o1) + g.getHeuristicVal(o1), g.getFScoreVal(o1) + g.getHeuristicVal(o2)));
		}
	});
	*/
	

	private void bfs(Graph<Station> g, Vertex<Station> s, Vertex<Station> t) { //search starting from source - only find paths blindly w/ bfs
		Queue<Vertex<Station>> openSet = new PriorityQueue<Vertex<Station>>(11, cmp);
		gScoreMap.put(s, 0); //distance from v to itself is 0
//		fScoreMap.put(s, g.getHeuristicVal(s));
		openSet.add(s);//put on first connecting edge... somehow improve;
		while (!(openSet.isEmpty())) {
			Vertex<Station> current = openSet.poll();
			verticesExpanded++;
			closedSet.add(current);
			if (current.equals(t)) {
				return; //modify for pathTo
			}
			System.out.println("\n********Just popped off queue in bfs: " + current.toString() + "***********");
			ArrayList<Edge<Station>> edgesFromV = current.getConnectedEdges(); // computes adjacent successors..?
//			Collections.sort(edgesFromV, cmp); //sort connections so alphabetical always taken
			for (Edge<Station> e : edgesFromV) {
				System.out.println("\n********Checking edge destination vertex in bfs: " + e.dest.toString() + "***********");
				if (!(e.getFrom().equals(current))) {
					System.out.println("\n\n\n================== ERROR: CURRENT IS NOT SAME AS EDGE SOURCE =============\n\n\n");
				}
				if (closedSet.contains(e.getTo())) {
					continue; //ignore neighbours already evaluated
				} //distance from start point (take out gscore) to a neighbour
				int tentativeGScore = gScoreMap.get(current) + e.getWeight();
				if (!(openSet.contains(e.getTo()))) { //discover new node
					gScoreMap.put(e.getTo(), tentativeGScore);
					openSet.add(e.getTo()); 
				} else if (tentativeGScore >= gScoreMap.get(e.getTo())) {
					continue; //tentative path is not better than current. skip!
				} 
//				this is best til now. record
				cameFrom.put(e.getTo(), current);
				gScoreMap.put(e.getTo(), tentativeGScore);
//				fScoreMap.put(e.getTo(), gScoreMap.get(e.getTo()) + g.getHeuristicVal(e.getTo())); priority queue should do this
			}
			
		}
	}

	private void bfsNonWeight(Graph<Station> g, Vertex<Station> s) { //search starting from source - find paths, prioritising weight
		Queue<Vertex<Station>> q = new LinkedList<Vertex<Station>>();
		q.add(s);
		this.marked.put(s, true);
		while (!(q.isEmpty())) {
			Vertex<Station> v = q.poll();
			System.out.println("\n********Just popped off queue in bfs: " + v.toString() + "***********");
			ArrayList<Edge<Station>> edgesFromV = v.getConnectedEdges();
			for (Edge<Station> e : edgesFromV) {
				System.out.println("\n********Checking edge destination vertex in bfs: " + e.dest.toString() + "***********");
				if (!(this.marked.containsKey(e.dest))) {
					this.cameFrom.put(e.dest, v); //keep track of where you came from ... what if multiple paths to e.dest though?
					this.marked.put(e.dest, true); // path now exists to destination - connected
					q.add(e.dest); //in priority queue, consider weight also
				}
			}
			
		}
	} 

	public boolean hasPath () { //is there path from s to v?
		return this.closedSet.contains(t);//this.marked.get(v);
	}
	
	public Iterable<Vertex<Station>> shortestPath() { //order of path to v - possible change to arrayList. Precond: hasPathTo - throw ex if not
		if (!hasPath()) return null; //throw exception?
		LinkedList<Vertex<Station>> pathStack = new LinkedList<>();
		Vertex<Station> curV = t;
		while (!(curV.equals(s))) {
			pathStack.addFirst(curV);
			curV = cameFrom.get(curV);
			pathNumVertices++;
		}
		pathStack.addFirst(curV); //original node
		pathNumVertices++;
		return pathStack;
		//loop over everything, stop when given source s
	}
	
	public int pathGScore () {
		return this.getGScoreVal(t);
	}


	/**
	 * @return the s
	 */
	public Vertex<Station> getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(Vertex<Station> s) {
		this.s = s;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BFSPaths [marked=" + marked + ", cameFrom=" + cameFrom + ", s=" + s + "]";
	}
	
	public String pathString () {
		Iterator<Vertex<Station>> it = shortestPath().iterator();
		String str = "\n=========== About to print Path from " + s.getContents() + " to " + t.getContents() + " Cost: " + this.pathGScore() + "========================";
		str += "\nNodes Expanded: " + this.verticesExpanded + "\n path nodes: " + this.getPathNumVertices() + "\nHas path status: " + this.hasPath();
		int i = 0;
		while (it.hasNext()) {
			str += "\nVertex #" + i + ": " + it.next().toString() + "\n";
			i++;
		}
		return str;
	}
	public void addGScoreVal (Vertex<Station> v, int val) {
		this.getgScoreMap().put(v, val);
	}

	public int getGScoreVal(Vertex<Station> v){
		return this.getgScoreMap().get(v);
	}
	/**
	 * @return the gScoreMap
	 */
	public Map<Vertex<Station>, Integer> getgScoreMap() {
		return gScoreMap;
	}

	/**
	 * @param gScoreMap the gScoreMap to set
	 */
	public void setgScoreMap(Map<Vertex<Station>, Integer> gScoreMap) {
		this.gScoreMap = gScoreMap;
	}

	/**
	 * @param fScoreMap the fScoreMap to set
	 */
//	public void setfScoreMap(Map<Vertex<Station>, Integer> fScoreMap) {
//		this.fScoreMap = fScoreMap;
//	}


	/**
	 * @return the fScoreMap
	 */
//	public Map<Vertex<Station>, Integer> getfScoreMap() {
//		return fScoreMap;
//	}
	/** @return the comparator
	 * 
	 * @return
	 */
	public Comparator<Vertex<Station>> getComparator() {
		return cmp;
	}

	/** @param comparator the comparator to set
	 * 
	 * @param comparator
	 */
	public void setComparator(Comparator<Vertex<Station>> comparator) {
		this.cmp = comparator;
	}

	/**
	 * @return the pathNumVertices
	 */
	public int getPathNumVertices() {
		return pathNumVertices;
	}


}
/*
% java BreadthFirstPaths tinyCG.txt
(least recently added for BFS, most recently add-
0 to 0: 0
0 to 1: 0-1
ed for DFS). This difference leads to completely
0 to 2: 0-2
different views of the graph, even though all the
0 to 3: 0-2-3
0 to 4: 0-2-4
vertices and edges connected to the source are
0 to 5: 0-5
*/