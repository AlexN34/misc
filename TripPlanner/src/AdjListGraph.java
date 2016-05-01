import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AdjListGraph<E> implements Graph<E>{
	private ArrayList<Vertex<E>> vertexList; //identify vertices with index # given by V
	private Map<String, E> contentMap; //map name of String to object contents for easier setup
	private Map<Vertex<E>, Integer> heuristicMap;
	private ArrayList<Edge<E>> reqTrips;

	/* ======================================== GRAPH CONSTRUCTION AND PRINTOUT =====================================
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		/*for (Vertex<E> vertex : vertexList) {
			for (Edge<E> edge : vertex.getConnectedEdges()) {
				str += edge.getContents().toString() 
			}
		} */
		return "AdjListGraph [vertexList=" + vertexList + "]";
	}

	public AdjListGraph () {
		this.heuristicMap = new HashMap<>();
		this.vertexList = new ArrayList<>();
		this.contentMap = new HashMap<>();
		this.reqTrips = new ArrayList<>();

	}
	
//	=========================== GRAPH INTERFACE IMPLEMENTATION FUNCTIONS =======================
	@Override
	public boolean addVertex(Vertex<E> vertex) { 
		System.out.println("About to add: " + vertex.toString()); //for debugging
		return (!vertexList.contains(vertex)) ? vertexList.add(vertex) : false;
		/*if (!vertexList.contains(vertex)) {
			return vertexList.add(vertex);
		} else { 
			return false;
		} */
	}

	@Override
	public boolean removeVertex(Vertex<E> vertex) {
		System.out.println("About to remove: " + vertex.toString()); //for debugging
		return (!vertexList.contains(vertex)) ? vertexList.remove(vertex) : false;
		/*if (!vertexList.contains(vertex)) {
			return vertexList.remove(vertex); 
		} else {
			return false;
		}
		*/
	}

	@Override
	public boolean addEdge(Edge<E> e) {
		System.out.println("About to add: " + e.toString()); //for debugging
		// Unwieldy converting Vertex -> index -> original index. Is this necessary? -> does e point to original graph
		boolean result = vertexList.get(vertexList.indexOf(e.src)).addConnectedEdge(e);
		Edge<E> mirror = new Edge<>(e.dest, e.src, e.weight);
		result = vertexList.get(vertexList.indexOf(mirror.src)).addConnectedEdge(mirror); //returns bool - learn to do proper exception handling 
		return result;

	}

	@Override
	public boolean removeEdge(Edge<E> e) {
		System.out.println("About to remove: " + e.toString()); //for debugging
		boolean result = true;//returns bool - learn to do proper exception handling 
		result = vertexList.get(vertexList.indexOf(e.src)).removeConnectedEdge(e); 
		Edge<E> mirror = new Edge<>(e.dest, e.src, e.weight);
		Vertex<E> v = vertexList.get(vertexList.indexOf(mirror.src));
		result = v.getConnectedEdges().contains(mirror);
//		result = v.removeConnectedEdge(mirror); 
		System.out.println("Connected edge list to vertex: " + v.getConnectedEdges().toString());
		System.out.println("Edge to delete: " + mirror.toString());
		return result; //if any fail, result will be false
	}

	
//	================================ SINGLE OBJECT ACCESSOR HELPER FUNCTIONS ========================
	@Override
	public Edge<E> getEdge(E srcContents, E destContents) throws NotInStructureException { 
		Vertex<E> src = this.getVertex(srcContents);
		Vertex<E> dest = this.getVertex(destContents);
		for (Edge<E> edge : src.getConnectedEdges()) {
			if (edge.getFrom().equals(src) && edge.getTo().equals(dest)) { //assume one edge between destinations- one weight for all
				return edge;
			}
		}
		throw new NotInStructureException("Contents in getEdge not found in graph: " + srcContents.toString() + " and " + destContents.toString());
	}

	@Override
	public Vertex<E> getVertex(E contents) throws NotInStructureException {
		ArrayList<Vertex<E>> vList = this.getVertexList();
		for (Vertex<E> vertex : vList) {
			if (vertex.getContents().equals(contents)) {
				return vertex;
			}
		}
		throw new NotInStructureException("Contents in getVertex not found in graph: " + contents.toString());
	}

	
	public E getContentsFromString (String input) { //returns null if nonexistent in graph
		return this.contentMap.get(input);
	}
	

// 	========================= HEURISTIC METHOD STUFF ==============================
	public void initialiseHeuristic () { //only call once graph is filled out
		for (Vertex<E> vertex : vertexList) {
			heuristicMap.put(vertex, 0); //lower heuristic is higher priority. set lowest by highest value
		}
	}
	
	private int calcHeuristicVal() {
		return 0;
	}

	private void checkHeuristicTrip(Edge<E> e) { //possible change to 2 vertices, or 2 E's //pass in an expanded edge to check if heuristic should be updated
		//for now, only allow order given
		if (reqTrips.contains(e)) {
			//subtract from heuristic val of edge
		//TODO no heuristic first	
		}
		//check admissible? triangle inequality?
	}
	@Override
	public void addHeuristicVal(Vertex<E> v, int val) {
		this.heuristicMap.put(v, val);
		System.out.println("just added to heurstic, printing contents:\n");
		System.out.println(heuristicMap.toString());
		
	}

	@Override
	public int getHeuristicVal(Vertex<E> v) {
		return heuristicMap.get(v);
	}
	
	public void addReqTrip(E src, E dest) {
		try {
			this.reqTrips.add(getEdge(src, dest));
		} catch (NotInStructureException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the reqTripMap
	 */
	public ArrayList<Edge<E>> getReqTripMap() {
		return reqTrips;
	}
		/**
	 * @return the vertexList
	 */
	
	
//	======================== GETTERS AND SETTERS ===========================
	@Override
	public int getNumV() {
		return this.vertexList.size();
	}

	@Override
	public int getNumE() {
		int i = 0;
		for (Vertex<E> vertex : this.vertexList) {
			i += vertex.getConnectedEdges().size();
		}
		return i; 
	}	

	@Override
	public ArrayList<Vertex<E>> getVertexList() {
		return vertexList;
	}

	/**
	 * @param vertexList the vertexList to set
	 */
	public void setVertexList(ArrayList<Vertex<E>> vertexList) {
		this.vertexList = vertexList;
	}		
	/**
	 * @return the objectMap
	 */
	public Map<String, E> getContentMap() {
		return contentMap;
	}

	/**
	 * @param objectMap the objectMap to set
	 */
	public void setContentMap(Map<String, E> contentMap) {
		this.contentMap = contentMap;
	}

//	==================================== TEAR DOWN ==========================
	public void deleteGraph () {
		for (Iterator<Vertex<E>> iteratorV = this.getVertexList().iterator(); iteratorV.hasNext();) {
			Vertex<E> v = iteratorV.next();
			ArrayList<Edge<E>> cE = v.getConnectedEdges();
			for (Iterator<Edge<E>> iteratorE = cE.iterator(); iteratorE.hasNext();) {
				Edge<E> edge = iteratorE.next();
				iteratorE.remove(); //consider doing remove step to remove methods
				System.out.println("Removed " + edge.toString() );
//				assertTrue(g.removeEdge(edge));
			}
			iteratorV.remove();
			System.out.println("Removed " + v.toString() );
//			assertTrue(g.removeVertex(v));
		}
	}
}
