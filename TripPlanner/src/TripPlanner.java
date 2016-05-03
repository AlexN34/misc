import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TripPlanner {

	public static void main(String[] args) {
		// Handle spaces when the single word also exists ..assume all unique
		//TODO if time, combine reqTrips methods into one single graph heuristic method --> swap out
		//TODO remove need for clone functionality in path. Use maps to get around unique closedSets/ArrayLists for each path?
		//TODO remove need for so many new paths -> optimise for less expansion?
			// Initialise system contents as empty lists
			// Scan from input for stuff to put into system s
		Scanner sc = null; // sc is now the input file.
		AdjListGraph<Station> g = new AdjListGraph<>();
		try {
			// args[0] is the first command line argument
			sc = new Scanner(new FileReader(args[0]));
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
		} catch (FileNotFoundException e) {
		} catch (NotInStructureException e) {
			e.printStackTrace();
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

			System.out.println("Scan finished");

}

private static void parseLine (AdjListGraph<Station> g, String line) throws NotInStructureException { // ensure E has a constructor from parsed Line //probably call search from here too
	if (!line.startsWith("#")) {
		if (line.startsWith("Transfer")) {
			System.out.println(g.addVertex(parseVertex(g, line)));//print result to debug
//		#Vertex <layover id>
//		#Edge <weight> <src> <dest>			
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