import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class BFSPathsTest {

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
	@Test
	public void test() {
		try {
			BFSPaths<Station> srch = new BFSPaths<>(g, g.getVertex(new Station("Eforie", 85))/*, new Comparator<Edge<Station>>() {
				@Override
				public int compare(Edge<Station> o1, Edge<Station> o2) {
					return o1.getTo().getContents().compareTo(o2.getTo().getContents()); //compare destination contents
				}
			}*/);
			System.out.println(srch.pathString(g, g.getVertex(new Station("Mehadia", 50))));
			
			for (Vertex<Station> vertex : g.getVertexList()) {
				assertTrue(srch.hasPathTo(g, vertex));
			}

		} catch (NotInStructureException e) {
			e.printStackTrace();
		}
	}
	
	/* ================= HELPER FUNCTIONS BELOW ================== */
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

