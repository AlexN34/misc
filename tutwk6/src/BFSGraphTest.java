import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class BFSGraphTest {
	BFSGraph<String> graph;
	@Before
	public void setUp() throws Exception {
		this.graph = new BFSGraph<>();
//		System.out.println("Setting up graph");
		System.out.println(graph.toString());
		Vertex<String> Arad = new Vertex<String>("Arad");
		this.graph.addVertex(Arad);
		Vertex<String> Zerind = new Vertex<String>("Zerind");
		this.graph.addVertex(Zerind);
		Vertex<String> Sibiu = new Vertex<String>("Sibiu");
		this.graph.addVertex(Sibiu);
		Vertex<String> Timisoara = new Vertex<String>("Timisoara");
		this.graph.addVertex(Timisoara);
		Vertex<String> Oaradea = new Vertex<String>("Oradea");
		this.graph.addVertex(Oaradea);
		Vertex<String> Fagaras = new Vertex<String>("Fagaras");
		this.graph.addVertex(Fagaras);
//		System.out.println(graph.toString());
		HashMap<String, Vertex<String>> hash = new HashMap<String, Vertex<String>>();
		for (Vertex<String> vertex : this.graph.vertices.keySet()) {
			hash.put(vertex.contents, vertex);
		}

		for (Vertex<String> vertex : this.graph.vertices.keySet()) {
			if (vertex.contents.equals("Arad")) {
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Zerind"), 75));
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Sibiu"), 140));
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Timisoara"), 118));
			} else if (vertex.contents.equals("Zerind")) {
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Oradea"), 146));
			} else if (vertex.contents.equals("Sibiu")) {
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Fagaras"), 239));
			} else if (vertex.contents.equals("Oradea")) {
				this.graph.addEdge(new Edge<String>(vertex, hash.get("Sibiu"), 297));
			}
		}
	}

	@Test
	public void testAddVertex() {

	}

	@Test
	public void testRemoveVertex() {
		/*System.out.println("Testing removeVertex");
		System.out.println(graph.toString());
		Vertex<String> Arad = new Vertex<String>("Arad");
		this.graph.removeVertex(Arad);
		System.out.println(graph.toString());
		Vertex<String> Zerind = new Vertex<String>("Zerind");
		this.graph.removeVertex(Zerind);
		System.out.println(graph.toString());
		Vertex<String> Sibiu = new Vertex<String>("Sibiu");
		this.graph.removeVertex(Sibiu);
		System.out.println(graph.toString());
		Vertex<String> Timisoara = new Vertex<String>("Timisoara");
		this.graph.removeVertex(Timisoara);
		System.out.println(graph.toString());
		Vertex<String> Oaradea = new Vertex<String>("Oaradea");
		this.graph.removeVertex(Oaradea);
		System.out.println(graph.toString());
		Vertex<String> Fagaras = new Vertex<String>("Fagaras");
		this.graph.removeVertex(Fagaras);
		System.out.println(graph.toString());*/

	}

	@Test
	public void testAddEdge() {


		System.out.println("Added edges..");
		for (Vertex<String> vertex : this.graph.vertices.keySet()) {
			System.out.println(vertex.toString());
				System.out.println("about to print edges..");
			for (Edge<String> edge : this.graph.vertices.get(vertex)) {
				System.out.println(edge.toString());
			}
		}
		System.out.println("done testing edges.. printing whole graph");
		System.out.println(graph.toString());
		}

	@Test
	public void testRemoveEdge() {
		System.out.println("About to remove edges..");
		System.out.println(graph.toString());
		for (Vertex<String> vertex : this.graph.getVertexSet()) {
			System.out.println("Currently in: vertex:" + vertex.toString() + " set: " + graph.getVertexSet().toString());
			for (Edge<String> edge : this.graph.getEdgesFromVertex(vertex)) {
				System.out.println("Currently in edgeList:" + graph.getEdgesFromVertex(vertex).toString());
				System.out.println("About to remove: " + edge.toString());
				this.graph.removeEdge(edge);
			}
		}
		System.out.println("removed edges..");
		System.out.println(graph.toString());
	}

	@Test
	public void testContains() {
		System.out.println("testing contains.. current graph is:");
		System.out.println(graph.toString());
		HashMap<String, Vertex<String>> hash = new HashMap<>();
		ArrayList<String> nodeNames = new ArrayList<>();
		nodeNames.add("Arad");
		nodeNames.add("Zerind");
		nodeNames.add("Sibiu");
		nodeNames.add("Timisoara");
		nodeNames.add("Oaradea");
		nodeNames.add("Fagaras");
		for (String name : nodeNames) {
			hash.put(name, new Vertex<String>(name));
		}
		ArrayList<Edge<String>> edges = new ArrayList<>();

		edges.add(new Edge<String>(hash.get("Arad"), hash.get("Zerind"), 75));
		edges.add(new Edge<String>(hash.get("Arad"), hash.get("Sibiu"), 140));
		edges.add(new Edge<String>(hash.get("Arad"), hash.get("Timisoara"), 118));
		edges.add(new Edge<String>(hash.get("Zerind"), hash.get("Oradea"), 146));
		edges.add(new Edge<String>(hash.get("Sibiu"), hash.get("Fagaras"), 239));
		edges.add(new Edge<String>(hash.get("Oaradea"), hash.get("Sibiu"), 297));
		for (String name : nodeNames) {
			assertTrue(graph.isInVertexSet(hash.get(name)));
		}
		for (Edge<String> edge : edges) {
			assertTrue(graph.contains(edge));
		}
		assertFalse(graph.contains(new Vertex<String>("Zamboozle")));
		System.out.println("finished testing contains..");
	}

	@Test
	public void testBFSGraphMapOfVertexOfStringArrayListOfEdgeOfString() {
		fail("Not yet implemented");
	}

	@Test
	public void testBFSGraph() { 
		System.out.println(" === Testing contains ===");
		System.out.println(" Graph is: " + graph.toString());
		Vertex<String> v = new Vertex<String>("Arad");
		System.out.println(v.toString());
		Vertex<String> v2 = this.graph.findVertex("Arad");
		System.out.println(v2.toString());
		System.out.println(this.graph.getVertexSet().contains(v));
		System.out.println(this.graph.getVertexSet().contains(v2));
	}

}