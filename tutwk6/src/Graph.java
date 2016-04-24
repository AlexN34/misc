import java.util.ArrayList;
import java.util.Set;

public interface Graph<E> {
	public void addVertex (Vertex<E> Vertex);
	public void removeVertex (Vertex<E> Vertex);
	public void addEdge (Edge<E> e);
	public void removeEdge (Edge<E> e);
	public boolean contains (Object o);
	public Set<Vertex<E>> getVertexSet();
	public ArrayList<Edge<E>> getEdgesFromVertex(Vertex<E> v);
//	public Edge<E> searchEdge (Vertex<E> src, Vertex<E> dest);
//	public Vertex<E> searchVertex (E contents);

}
