import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class VertexTest {
	HashMap<String, Vertex<String>> vertices;

	@Before
	public void setUp() throws Exception {
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
		this.vertices = hash;
	}

	@Test
	public void test() {
		System.out.println("testing keyset contains functions..");
		for (String vertex : vertices.keySet()) {
			Vertex<String> v = new Vertex<String>(vertex);
			System.out.println(v.toString());
			assertTrue(vertices.get(vertex).equals(v));
			assertTrue(vertices.containsValue(v));
			assertTrue(vertices.containsValue(vertices.get(vertex)));
			Vertex<String> v2 = new Vertex<String>("dummy");
			System.out.println(v2.toString());
			assertFalse(vertices.get(vertex).equals(v2));
			assertFalse(vertices.containsValue(v2));
			System.out.println("vertex: " + vertices.get(vertex).toString() + " tested");
		}
	}

}
