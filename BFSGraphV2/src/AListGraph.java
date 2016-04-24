import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AListGraph<E> implements Graph<E>{
	private ArrayList<Vertex<E>> vertexList; //identify vertices with index # given by V
	private Map<String, E> contentMap; //map name of String to object contents for easier setup

	/**
	 * @return the vertexList
	 */
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
		boolean result = true; // TODO: Unwieldy converting Vertex -> index -> original index. Is this necessary? -> does e point to original graph
		result = vertexList.get(vertexList.indexOf(e.src)).addConnectedEdge(e); //returns bool, TODO throw error if false
		Edge<E> mirror = new Edge<>(e.dest, e.src, e.weight);
		result = vertexList.get(vertexList.indexOf(mirror.src)).addConnectedEdge(mirror); //returns bool, TODO throw error if false
		return result;

	}

	@Override
	public boolean removeEdge(Edge<E> e) {
		System.out.println("About to remove: " + e.toString()); //for debugging
		boolean result = true;
		result = vertexList.get(vertexList.indexOf(e.src)).removeConnectedEdge(e); //returns bool, TODO throw error if false
		Edge<E> mirror = new Edge<>(e.dest, e.src, e.weight);
		result = vertexList.get(vertexList.indexOf(mirror.src)).removeConnectedEdge(mirror); //returns bool, TODO throw error if false
		return result; //if any fail, result will be false
	}

	/*@Override
	public boolean contains(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof Edge) {
			Edge<?> obj = (Edge<?>) o;
			if (!(vertexList.contains(obj.src)) || !(vertexList.contains(obj.dest)) ) { //TODO: consider using recursive contains call
				return false;
			}
			if (!(vertexList.get(vertexList.indexOf(obj.src)).getConnectedEdges().contains(obj)) || 
					!(vertexList.get(vertexList.indexOf(obj.src)).getConnectedEdges().contains(obj))) {
				return false;
			} else {
				return true;
			}
		} else if (o instanceof Vertex) {
			Vertex<?> obj = (Vertex<?>) o;
			return vertexList.contains(obj);
		} else {
			return false;
		}
		
	}
	*/


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
	public Edge<E> getEdge(E srcContents, E destContents) { //TODO consider adding weight consideration if needed
		Vertex<E> src = this.getVertex(srcContents);
		Vertex<E> dest = this.getVertex(destContents);
		for (Edge<E> edge : src.getConnectedEdges()) {
			if (edge.src.equals(src) && edge.dest.equals(dest)) {
				return edge;
			}
		}
		return new Edge<>(src, dest, 0); //TODO change to throw exception instead;
	}

	@Override
	public Vertex<E> getVertex(E contents) {
		ArrayList<Vertex<E>> vList = this.getVertexList();
		for (Vertex<E> vertex : vList) {
			if (vertex.contents.equals(contents)) {
				return vertex;
			}
		}
		return new Vertex<E>(contents); //make a vertex if not found ? TODO change to throw exception
	}


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AListGraph [vertexList=" + vertexList + "]";
	}

	//TODO: constructors for graph w/ no edges given vertexSet, graph taking entirely from input
	public AListGraph () {
		this.vertexList = new ArrayList<>();
		this.contentMap = new HashMap<>();
	}
	

	private static void parseLine (AListGraph<Station> g, String line) { // ensure E has a constructor from parsed Line
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

	private static Edge<Station> parseEdge (AListGraph<Station> g, String line) {
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
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

//		System.out.println("Scan finished");
	}



}