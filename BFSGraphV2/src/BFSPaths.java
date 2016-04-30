import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BFSPaths<E> {
	private Map<Vertex<E>, Boolean> marked; 
	private Map<Vertex<E>, Vertex<E>> parentMap; //edgeTo - parent node of this vertex
	private Vertex<E> s; //source node/vertex
	//How to abstract with strategy pattern?
	
	//TODO Alphabetical checking of connectedEdges -- add in alphabetical/reorganise?
	/*
	 *Store the successors of a node in an ArrayList and make sure they are sorted in alphabetical order of the city name before adding them to the Queue 
	 * 
	 */
	/*compareContents <Vertex<E> extends Comparable<Vertex<E>>> implements Comparator<Vertex<E>> = new <Vertex<E> extends Comparable<Vertex<E>>> implements Comparator<Vertex<E>>>()
	 {
		@Override
		public int compare(Vertex<E> o1, Vertex<E> o2) {
			return o1.;
		}
	 };
	 */


	public BFSPaths (Graph<E> g, Vertex<E> s/*,Comparator<Edge<E>> cmp*/){
		this.marked = new HashMap<>();
		this.parentMap = new HashMap<>();
		this.s = s;
		bfs(g, s/*, cmp*/);
	}
	
	private void bfs(Graph<E> g, Vertex<E> s /*, Comparator<Edge<E>> cmp*/) { //search starting from source - only find paths blindly w/ bfs
		Queue<Vertex<E>> q = new LinkedList<Vertex<E>>();
		q.add(s);
		this.marked.put(s, true);
		while (!(q.isEmpty())) {
			Vertex<E> v = q.poll();
			System.out.println("\n********Just popped off queue in bfs: " + v.toString() + "***********");
			ArrayList<Edge<E>> edgesFromV = v.getConnectedEdges(); // computes adjacent successors..?
			//Collections.sort(edgesFromV, cmp); //sort connections so alphabetical always taken
			for (Edge<E> e : edgesFromV) {
				System.out.println("\n********Checking edge destination vertex in bfs: " + e.dest.toString() + "***********");
				if (!(this.marked.containsKey(e.dest))) { //if 2 edges of same weight, take first alphabetically
					this.parentMap.put(e.dest, v); //keep track of where you came from ... what if multiple paths to e.dest though?
													//take the first instance.
					this.marked.put(e.dest, true); // path now exists to destination - connected
					q.add(e.dest); //in priority queue, consider weight also - adds successor nodes (connectedEdges) to queue
				}
			}
			
		}
	}

	private void bfsWeight(Graph<E> g, Vertex<E> s) { //search starting from source - find paths, prioritising weight
		Queue<Vertex<E>> q = new LinkedList<Vertex<E>>();
		q.add(s);
		this.marked.put(s, true);
		while (!(q.isEmpty())) {
			Vertex<E> v = q.poll();
			ArrayList<Edge<E>> edgesFromV = v.getConnectedEdges();
			for (Edge<E> e : edgesFromV) {
				if (!(this.marked.containsKey(e.dest))) {
					this.parentMap.put(e.dest, v); //keep track of where you came from ... what if multiple paths to e.dest though?
					this.marked.put(e.dest, true); // path now exists to destination - connected
					q.add(e.dest); //in priority queue, consider weight also
				}
			}
			
		}
	}
	public boolean hasPathTo (Graph<E> g, Vertex<E> v) { //is there path from s to v?
		return this.marked.get(v);
	}
	
	public Iterable<Vertex<E>> pathTo (Graph<E> g, Vertex<E> v) { //order of path to v - possible change to arrayList. Precond: hasPathTo - throw ex if not
		if (!hasPathTo(g, v)) return null; //throw exception?
		LinkedList<Vertex<E>> pathStack = new LinkedList<>();
		Vertex<E> curV = v;
		while (!(curV.equals(s))) {
			pathStack.addFirst(curV);
			curV = parentMap.get(curV);
		}
		pathStack.addFirst(curV); //original node
		return pathStack;
		//loop over everything, stop when given source s
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

	/**
	 * @return the s
	 */
	public Vertex<E> getS() {
		return s;
	}

	/**
	 * @param s the s to set
	 */
	public void setS(Vertex<E> s) {
		this.s = s;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BFSPaths [marked=" + marked + ", parentMap=" + parentMap + ", s=" + s + "]";
	}
	
	public String pathString (Graph<E> g, Vertex<E> v) {
		Iterator<Vertex<E>> it = pathTo(g, v).iterator();
		String str = "\n=========== About to print Path from " + s.getContents() + " to " + v.getContents() + " ========================";
		int i = 0;
		while (it.hasNext()) {
			str += "\nVertex #" + i + ": " + it.next().toString() + "\n";
			i++;
		}
		return str;
	}

}
