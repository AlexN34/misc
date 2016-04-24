import java.util.ArrayList;

public interface Graph<E> {
	public boolean addVertex (Vertex<E> Vertex);
	public boolean removeVertex (Vertex<E> Vertex);
	public boolean addEdge (Edge<E> e);
	public boolean removeEdge (Edge<E> e);
//	public boolean contains (Object o);
	public int numV();
	public int numE();
	public Vertex<E> getVertex(E contents);
//	public ArrayList<Edge<E>> getConnectedEdges(Vertex<E> v);
	public Edge<E> getEdge(E srcContents, E destContents); 

}

