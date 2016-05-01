import java.util.HashMap;
import java.util.Map;

public class Search<E> {
	private Vertex<E> s;
	private Map<Vertex<E>, Integer> pathTable;
	public Search(Graph<E> G, Vertex<E> s)  { // Search for vertices connected to s
		this.s = s;
		this.pathTable = new HashMap<>();
		//do search in constructor -> use other public methods to access data from search
	}
	
	private void srch (Graph<E> g, Vertex<E> s) {
	
		

	} 

	public Search(Graph<E> G, Vertex<E> src, Vertex<E> dest)  { // Search for path from src to dest
		this.s = s;
		this.pathTable = new HashMap<>();
		//do search in constructor -> use other public methods to access data from search
	}

	public boolean marked(Vertex<E> v) { //is v connected to s? // has this vertex been visited?
		return true;
	}

	public int count() { // how many vertices are connected to s ?
		return s.getConnectedEdges().size();
	}
}

/*


*/