import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class AListGraph<E> implements Graph<E>{
	private ArrayList<Vertex<E>> vertexList; //identify vertices with index # given by V
	private Map<String, E> contentMap; //map name of String to object contents for easier setup

	/**
	 * @return the vertexList
	 */
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
	public void setObjectMap(Map<String, E> objectMap) {
		this.contentMap = objectMap;
	}

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



	@Override
	public int numV() {
		return this.vertexList.size();
	}

	@Override
	public int numE() {
		int i = 0;
		for (Vertex<E> vertex : this.vertexList) {
			i += vertex.getConnectedEdges().size();
		}
		return i; 
	}

	@Override
	public Edge<E> getEdge(E srcContents, E destContents, int weight) throws NotInStructureException { 
		Vertex<E> src = this.getVertex(srcContents);
		Vertex<E> dest = this.getVertex(destContents);
		for (Edge<E> edge : src.getConnectedEdges()) {
			if (edge.getFrom().equals(src) && edge.getTo().equals(dest) && edge.weight == weight) {
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
		return "AListGraph [vertexList=" + vertexList + "]";
	}

	public AListGraph () {
		this.vertexList = new ArrayList<>();
		this.contentMap = new HashMap<>();
	}
	

	private static void parseLine (AListGraph<Station> g, String line) throws NotInStructureException { // ensure E has a constructor from parsed Line
		if (!line.startsWith("#")) {
			if (line.startsWith("Vertex")) {
				System.out.println(g.addVertex(parseVertex(g, line)));//print result to debug
//			#Vertex <layover id>
//			#Edge <weight> <src> <dest>			
			} else if (line.startsWith("Edge")) {
				System.out.println(g.addEdge(parseEdge(g, line))); //print result to debug
			}
		}
	}
	private static Vertex<Station> parseVertex (AListGraph<Station> g, String line) {
		//Remember vertex using strings for edge setup
		Station s = new Station(line);
		g.getContentMap().put(s.getName(), s);
		return new Vertex<Station>(s);
	}

	private static Edge<Station> parseEdge (AListGraph<Station> g, String line) throws NotInStructureException {
		String[] tokens = line.split(" ");
		int weight = Integer.parseInt(tokens[1]);
		String substr = ""; //0th token is Vertex, 1st is contents
		ArrayList<String> vertexNames = new ArrayList<>();
		for (int i = 2; i < tokens.length; i++) {
			substr += tokens[i];
			//check if substr forms a vertex name
			if ((g.getContentMap().containsKey(substr))) {
				vertexNames.add(substr);
				substr = "";
			}
		}
		return new Edge<>(g, g.inputStrToContents(vertexNames.get(0)), g.inputStrToContents(vertexNames.get(1)), weight);
	}
	
	public E inputStrToContents (String input) { //returns null if nonexistent in graph
		return this.contentMap.get(input);
	}
	
	public void deleteGraph () {
		for (Iterator<Vertex<E>> iteratorV = this.getVertexList().iterator(); iteratorV.hasNext();) {
			Vertex<E> v = iteratorV.next();
			ArrayList<Edge<E>> cE = v.getConnectedEdges();
			for (Iterator<Edge<E>> iteratorE = cE.iterator(); iteratorE.hasNext();) {
				Edge<E> edge = iteratorE.next();
				iteratorE.remove(); //TODO migrate remove step to remove methods
				System.out.println("Removed " + edge.toString() );
//				assertTrue(g.removeEdge(edge));
			}
			iteratorV.remove();
			System.out.println("Removed " + v.toString() );
//			assertTrue(g.removeVertex(v));
		}
	}
	public static void main(String[] args) { //section that will parse input
		// Initialise system contents as empty lists
		// Scan from input for stuff to put into system s
		Scanner sc = null; // sc is now the input file.
		AListGraph<Station> g = new AListGraph<>();
		try {
			// args[0] is the first command line argument
			sc = new Scanner(new FileReader(args[0]));
			// set first argument as object to scan through
			parseLine(g, sc.nextLine());
			g.toString();
		} catch (FileNotFoundException e) {
		} catch (NotInStructureException e) {
			e.printStackTrace();
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

//		System.out.println("Scan finished");
	}



}
