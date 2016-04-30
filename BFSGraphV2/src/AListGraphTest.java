import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AListGraphTest {
	AListGraph<Station> g;
	@Before
	public void setUp() throws Exception {
		// Initialise system contents as empty lists
		// Scan from input for stuff to put into system s
		Scanner sc = null; // sc is now the input file.
		this.g = new AListGraph<>();
		try {
			// args[0] is the first command line argument
			sc = new Scanner(new FileReader("fullGraph.txt"));
			// set first argument as object to scan through
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				System.out.println(nextLine);
				parseLine(g, nextLine);
			}
			System.out.println("\n======== All Vertices and Edges created ============================\n" + g.toString());
		} catch (NullPointerException e) {
			System.out.println("null pointer during loop");
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

		System.out.println("Scan finished");
	}
	
	@After
	public void tearDown() throws Exception {
		System.out.println("\n======== About to tear down, graph is: ================================\n" + g.toString());
		System.out.println("\n======== Removal loop starting now================================\n" );
		for (Iterator<Vertex<Station>> iteratorV = g.getVertexList().iterator(); iteratorV.hasNext();) {
			Vertex<Station> v = iteratorV.next();
			ArrayList<Edge<Station>> cE = v.getConnectedEdges();
			for (Iterator<Edge<Station>> iteratorE = cE.iterator(); iteratorE.hasNext();) {
				Edge<Station> edge = iteratorE.next();
				iteratorE.remove(); //TODO migrate remove step to remove methods
				assertFalse(cE.contains(edge));
				System.out.println("Removed " + edge.toString() );
//				assertTrue(g.removeEdge(edge));
			}
			iteratorV.remove();
			System.out.println("Removed " + v.toString() );
//			assertTrue(g.removeVertex(v));
		}
		System.out.println("\n======== Tear Down complete, graph is: ================================\n" + g.toString());
	}
	
	@Test
	public void test() {
	}


	private static void parseLine (AListGraph<Station> g, String line) throws NotInStructureException { // ensure E has a constructor from parsed Line
		if (!line.startsWith("#")) {
			if (line.startsWith("Vertex")) {
//				System.out.println(g.addVertex(parseVertex(g, line)));//print result to debug
				assertTrue(g.addVertex(parseVertex(g, line)));//print result to debug
//			#Vertex <layover id>
//			#Edge <weight> <src> <dest>			
			} else if (line.startsWith("Edge")) {
//				System.out.println(g.addEdge(parseEdge(g, line))); //print result to debug
				assertTrue(g.addEdge(parseEdge(g, line))); //print result to debug
			}
		}
	}
	private static Vertex<Station> parseVertex (AListGraph<Station> g, String line) {
		//Remember vertex using strings for edge setup
		Station s = new Station(line);
		g.getContentMap().put(s.getName(), s); //Returns null... possibly giving the exception
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
			Map<String, Station> map= g.getContentMap();
			if ((map.containsKey(substr))) {
				vertexNames.add(substr);
				substr = "";
			}
		}
		return new Edge<>(g, g.inputStrToContents(vertexNames.get(0)), g.inputStrToContents(vertexNames.get(1)), weight);
	}
}	
