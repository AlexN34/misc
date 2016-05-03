import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class AStarSearchTest {

	AdjListGraph<Station> g;
	@Before
	public void setUp() throws Exception {
		// Initialise system contents as empty lists
		// Scan from input for stuff to put into system s
		Scanner sc = null; // sc is now the input file.
		this.g = new AdjListGraph<>();
		try {
			// args[0] is the first command line argument
			sc = new Scanner(new FileReader("files/ass2input.txt"));
			// set first argument as object to scan through
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				System.out.println("About to process: " + nextLine);
				parseLine(g, nextLine);
			}
			System.out.println("\n\n\n ============== Read required trips ============ \n\n\n"); //print result to debug
			System.out.println(g.getReqTrips().toString()); //print result to debug
			System.out.println("\n\n\n ============== FINAL INPUT GRAPH: ============ \n\n\n"); //print result to debug
			System.out.println(g.toString()); //print result to debug
		} catch (NullPointerException e) {
			System.out.println("null pointer during loop");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

		System.out.println("Scan finished");
	}
	@Test
	public void test() {
		/*
		Scanner hr = null;
		try {
			hr = new Scanner(new FileReader("heuristic.txt"));
			while (hr.hasNextLine()) {
				parseHeuristic(g, hr.nextLine());
			}
		*/
		try {
//			g.initialiseHeuristic();
//			AStarSearch<Station> srch = new AStarSearch<>(g, g.getVertex(new Station("London", 60)), g.getVertex(new Station("Berlin", 40)));
			AStarSearch<Station> srch = new AStarSearch<>(g, g.getVertex(new Station("London", 60)));
			System.out.println(srch.pathString());
//				assertTrue(srch.hasPath());
				//define new searches for each vertex?
//		} catch (FileNotFoundException e) {
		} catch (NotInStructureException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		/* finally {
			if (hr != null) {
				hr.close();
			}
		}
		*/
	}
	
	/* ================= HELPER FUNCTIONS BELOW ================== */
	/* private static void parseHeuristic (AdjListGraph<Station> g, String line) {
		if (!line.startsWith("#") && !line.isEmpty()) {
			String[] tokens = line.split(" ");
			String substr = ""; //0th token is value, 1st onward is contents name
			for (int i = 1; i < tokens.length; i++) {
				substr += tokens[i];
			}
			for (Vertex<Station> v : g.getVertexList()) {
				if (v.getContents().getName().equals(substr)) {
					Vertex<Station> vToAdd = v;
					g.addHeuristicVal(vToAdd, Integer.valueOf(tokens[0]));
					return;
				}
			}
		}
	}*/
	private static void parseLine (AdjListGraph<Station> g, String line) throws NotInStructureException { // ensure E has a constructor from parsed Line //probably call search from here too
		if (!line.startsWith("#")) {
			if (line.startsWith("Transfer")) {
				System.out.println(g.addVertex(parseVertex(g, line)));//print result to debug
//			#Vertex <layover id>
//			#Edge <weight> <src> <dest>			
			} else if (line.startsWith("Time")) {
				System.out.println(g.addEdge(parseEdge(g, line))); //print result to debug
			} else if (line.startsWith("Trip")) {
				parseTrip(g, line);
			}
		}
	}

	private static void parseTrip (AdjListGraph<Station> g, String line) {
		String[] tokens = line.split(" ");
		String substr = ""; //0th token is Vertex, 1st is contents
		ArrayList<String> vertexNames = new ArrayList<>();
		for (int i = 1; i < tokens.length; i++) {
			substr += tokens[i] + " ";
			String testStr = substr.trim();
			//trim first, check
			//check if substr forms a vertex name
			if ((g.getContentMap().containsKey(testStr))) {
				vertexNames.add(substr.trim());
				substr = ""; //reset to find next vertex
			}
		}
		g.addReqTrip(g.getContentsFromString(vertexNames.get(0)), g.getContentsFromString(vertexNames.get(1)));
	}
	private static Vertex<Station> parseVertex (AdjListGraph<Station> g, String line) {
		//Remember vertex using strings for edge setup
		Station s = new Station(line);
		g.getContentMap().put(s.getName(), s);
		return new Vertex<Station>(s);
	}

	private static Edge<Station> parseEdge (AdjListGraph<Station> g, String line) throws NotInStructureException {
		String[] tokens = line.split(" ");
		int weight = Integer.parseInt(tokens[1]);
		String substr = ""; //0th token is Vertex, 1st is contents
		ArrayList<String> vertexNames = new ArrayList<>();
		for (int i = 2; i < tokens.length; i++) {
			substr += tokens[i] + " ";
			String testStr = substr.trim();
			//trim first, check
			//check if substr forms a vertex name
			if ((g.getContentMap().containsKey(testStr))) {
				vertexNames.add(testStr);
				substr = ""; //reset to find next vertex
			}
		}
		return new Edge<>(g, g.getContentsFromString(vertexNames.get(0)), g.getContentsFromString(vertexNames.get(1)), weight); //only need to get contents from graph, all vertices loaded at this pt
	}
}	

