import java.util.ArrayList;

public interface Graph<E> {
	public boolean addVertex (Vertex<E> Vertex);
	public boolean removeVertex (Vertex<E> Vertex);
	public boolean addEdge (Edge<E> e);
	public boolean removeEdge (Edge<E> e);
//	public boolean contains (Object o);
	public int getNumV();
	public int getNumE();
	public Vertex<E> getVertex(E contents) throws NotInStructureException;
//	public ArrayList<Edge<E>> getConnectedEdges(Vertex<E> v);
//	public Edge<E> getEdge(E srcContents, E destContents, int weight) throws NotInStructureException; 
	public ArrayList<Vertex<E>> getVertexList(); 
	public void addHeuristicVal (Vertex<E> v, int val);
	public int getHeuristicVal (Vertex<E> v);
	/*
	public void addFScoreVal (Vertex<E> v, int val); 
	public int getFScoreVal(Vertex<E> v);
	public Map<Vertex<E>, Integer> getgScoreMap() ;
	public void setgScoreMap(Map<Vertex<E>, Integer> gScoreMap) ;
	public Map<Vertex<E>, Integer> getfScoreMap();
	public void setfScoreMap(Map<Vertex<E>, Integer> fScoreMap);
	*/
	Edge<E> getEdge(E srcContents, E destContents) throws NotInStructureException;
}