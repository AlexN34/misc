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

public class AStarSearch<E extends Station> {
	private Map<Vertex<Station>, Boolean> marked; 
	private Set<Edge<Station>> closedSet;
	private Map<Vertex<Station>, Vertex<Station>> cameFrom; //edgeTo - parent node of this vertex
	private Vertex<Station> s; //source node/vertex
	private Vertex<Station> t; //sink node/vertex
	private Map<Vertex<Station>, Integer> gScoreMap; //edgeTo - parent node of this vertex
//	private Map<Vertex<Station>, Integer> fScoreMap; 
	private Graph<Station> g;
	private Path resultPath;
	private int pathNumVertices;
	private int verticesExpanded;
	//How to abstract with strategy pattern?
	/*
	private Comparator<Vertex<Station>> cmpHeuristic = new Comparator<Vertex<Station>>() //controls FScore pretty much
	 {
			@Override
			public int compare(Vertex<Station> o1, Vertex<Station> o2) {
				int gScore1 = gScoreMap.get(o1); // TODO these don't exist anymore, heuristic moved from graph. perhaps bring back for back compatible
				int heur1 = g.getHeuristicVal(o1);
				int total1 = gScore1 + heur1;
				int gScore2 = gScoreMap.get(o2);
				int heur2 = g.getHeuristicVal(o2);
				int total2 = gScore2 + heur2;

				int result = (Integer.compare(total1, total2));
				//if same score, return alphabetical
				return (!(result != 0)) ? result : o1.getContents().compareTo(o2.getContents());
			} 
	 };
	 */
	private Comparator<Path> cmpPath = new Comparator<Path>() //controls FScore pretty much
	 {
		@Override
		public int compare(Path arg0, Path arg1) {
			int gScore0 = arg0.getPathGScore();
			int heur0 = arg0.getHeuristicVal();
			int total0 = gScore0 + heur0;
			int gScore1 = arg1.getPathGScore();
			int heur1 = arg1.getHeuristicVal();
			int total1 = gScore1 + heur1;
			int result = Integer.compare(total0, total1); //adjust so this is based on property of dest vertex
			return result;
		}
	 };

	private Comparator<Vertex<Station>> cmpBasic = new Comparator<Vertex<Station>>()
	 {
			@Override
			public int compare(Vertex<Station> o1, Vertex<Station> o2) {
				int gScore1 = gScoreMap.get(o1);
				int gScore2 = gScoreMap.get(o2);

				int result = (Integer.compare(gScore1, gScore2));
				//if same score, return alphabetical
				return (!(result != 0)) ? result : o1.getContents().compareTo(o2.getContents());
			} 
	 };
	 //Searches given heuristic and a destination to get to.
	public AStarSearch (Graph<Station> g, Vertex<Station> s, Vertex<Station> t){
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
//		aStarPathFind(g, s, t);
	}
	//Searches on heuristic given a starting node and reqTrips (in g)
	public AStarSearch (Graph<Station> g, Vertex<Station> s){
		this.closedSet = new HashSet<>();
		this.cameFrom = new HashMap<>();
		this.g = g;
		this.s = s;
		this.pathNumVertices = 0;
		this.verticesExpanded = 0;
		gScoreMap = new HashMap<>(); //edgeTo - parent node of this vertex
//		fScoreMap = new HashMap<>(); 
		for (Vertex<Station> v : g.getVertexList()) {
			gScoreMap.put(v, Integer.MAX_VALUE);
//			fScoreMap.put(v, Integer.MAX_VALUE);
		}
		aStarPathFind(g, s);
	}	

	private void aStarPathFind (Graph<Station> g, Vertex<Station> s) { //search starting from source - only find paths blindly w/ bfs
		Queue<Path> openSet = new PriorityQueue<Path>(11, cmpPath); //use set of edges instead?
		Path start = new Path(new Edge<>(s, s, 0), g.getReqTrips()); //make new edge on path with 0 weight - 0 gscore.

		openSet.add(start);//put on first connecting edge... somehow improve;
		while (!(openSet.isEmpty())) {
			Path current = openSet.poll();
			verticesExpanded++;
			this.closedSet = current.getClosedSet();
//			closedSet.add(current); this is now done in path class when edges are added
			if (current.getReqTripsLeft() == 0) { //finish when there are no required trips left
				this.resultPath = current;
				System.out.println("aStarPathFind final path:" + current.toString());
				return; 
			}
			System.out.println("\n********Just popped off queue in bfs: " + current.getLastVertex().toString() + "***********");
			ArrayList<Edge<Station>> edgesFromV = current.getLastVertex().getConnectedEdges(); // computes adjacent successors..?
//			Collections.sort(edgesFromV, cmp); //sort connections so alphabetical always taken
			for (Edge<Station> e : edgesFromV) {
				System.out.println("\n********Checking edge destination vertex in bfs: " + e.getTo().toString() + "***********");

				if (!(e.getFrom().equals(current.getLastEdge().getTo()))) {
					System.out.println("\n\n\n================== ERROR: CURRENT IS NOT SAME AS EDGE SOURCE =============\n\n\n SRC below:");
					System.out.println(e.getFrom().toString());
					System.out.println("\n\n\n================== =============\n\n\n");
				}
				if (closedSet.contains(e)) {
					continue; //ignore neighbours already evaluated
				} //distance from start point (take out gscore) to a neighbour
//				int tentativeGScore = (openSet.isEmpty()) ?  current.getPathGScore() + e.getWeight() :  e.getFrom().getContents().getTransfer() + current.getPathGScore() + e.getWeight();
				Path curClone = current.clone(); 
				curClone.addToPath(e);
				//check current path PLUS next edge
				if (!(openSet.contains(curClone))) { //discover new node //possible always put into openSet?
//					gScoreMap.put(e.getTo(), tentativeGScore); //update total score to consider for cost from this e.getFrom()
					openSet.add(curClone); 
				} //else if (tentativeGScore >= curClone.getPathGScore()) {
					//continue; //tentative path is not better than current. skip! now done inside path method when adding...
				//} 
				//openSet.add(curClone);
//				this is best til now. record
//				cameFrom.put(e.getTo(), current); //list for path?
//				gScoreMap.put(e.getTo(), tentativeGScore);
//				fScoreMap.put(e.getTo(), gScoreMap.get(e.getTo()) + g.getHeuristicVal(e.getTo())); priority queue should do this
			}
			
		}

		try {
			throw new NotInStructureException("All edges relaxed without path found, openSet is empty");
		} catch (NotInStructureException e) {
			e.printStackTrace();
		}
//		return new Path(null, g.getReqTrips()); //check for null path if fail
	}
	/*
	private void aStarPathFind (Graph<Station> g, Vertex<Station> s, Vertex<Station> t) { //search starting from source - only find paths blindly w/ bfs
		Queue<Vertex<Station>> openSet = new PriorityQueue<Vertex<Station>>(11, cmpHeuristic); //use set of edges instead?
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
				int tentativeGScore = (openSet.isEmpty()) ?  gScoreMap.get(current) + e.getWeight() :  e.getFrom().getContents().getTransfer() + gScoreMap.get(current) + e.getWeight();

				if (!(openSet.contains(e.getTo()))) { //discover new node //possible always put into openSet?
					gScoreMap.put(e.getTo(), tentativeGScore); //update total score to consider for cost from this e.getFrom()
					openSet.add(e.getTo()); 
				} else if (tentativeGScore >= gScoreMap.get(e.getTo())) {
					continue; //tentative path is not better than current. skip!
				} 
//				this is best til now. record
				cameFrom.put(e.getTo(), current); //list for path?
				gScoreMap.put(e.getTo(), tentativeGScore);
//				fScoreMap.put(e.getTo(), gScoreMap.get(e.getTo()) + g.getHeuristicVal(e.getTo())); priority queue should do this
			}
			
		}
	}
	*/
	/*public boolean hasPath () { //is there path from s to v?
		return this.closedSet.contains(t);//this.marked.get(v);
	} */
	
	public Iterable<Vertex<Station>> shortestPath() { //order of path to v - possible change to arrayList. Precond: hasPathTo - throw ex if not
		/*
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
		 */
		LinkedList<Vertex<Station>> pathStack = new LinkedList<>();
		for (Edge<Station> edge : this.resultPath.getPathContents()) {
			pathStack.add(edge.getTo());
		}
		return pathStack;
	}
	
	public int pathCost () {
//		return this.getGScoreVal(t);
		return this.resultPath.getPathGScore();
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
		return "AStarSearch [marked=" + marked + ", cameFrom=" + cameFrom + ", s=" + s + "]";
	}
	
	public String pathString () {
		Iterator<Vertex<Station>> it = shortestPath().iterator();
//		String str = "\n=========== About to print Path from " + s.getContents() + " to " + t.getContents() + " Cost: " + this.pathCost() + "========================";
		String str = "\n=========== About to print Path from " + s.getContents()  + " - Cost: " + this.pathCost() + "========================";
		str += "\nNodes Expanded: " + this.verticesExpanded + "\n path nodes: " + this.getPathNumVertices() /*+ "\nHas path status: " + this.hasPath()*/;
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
	/*
	public Comparator<Vertex<Station>> getComparator() {
		return cmpHeuristic;
	}
	*/
	/** @param comparator the comparator to set
	 * 
	 * @param comparator
	 */
	/*
	public void setComparator(Comparator<Vertex<Station>> comparator) {
		this.cmpHeuristic = comparator;
	}
	/*
	/**
	 * @return the pathNumVertices
	 */
	public int getPathNumVertices() {
		return this.resultPath.getPathContents().size();
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