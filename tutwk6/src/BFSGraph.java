import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

	public class BFSGraph<E> implements Graph<E> {
		Map<Vertex<E>, ArrayList<Edge<E>>> vertices;
//	Vertex[] adjLists
	@Override
	public void addVertex (Vertex<E> v) {
		this.vertices.put(v, new ArrayList<Edge<E>>());
	}
	@Override
	public void removeVertex (Vertex<E> v) {
		this.vertices.remove(v);
		
	}
	

	@Override
	public void addEdge(Edge<E> e) {
		this.vertices.get(e.src).add(e);
	}
	@Override
	public void removeEdge(Edge<E> e) {
		ArrayList<Edge<E>> edgeList= this.getEdgesFromVertex(e.src);
		for (Edge<E> edge : edgeList ) {
			if (edge.equals(e)) {
				edgeList.remove(edgeList.indexOf(edge));
			}
		}
		
	}

	/* Vertices is graph representation, stores vertices and all edges to others*/
	public Map<Vertex<E>, ArrayList<Edge<E>>> getVertices() {
		return this.vertices;
	}
	public void setVertices(Map<Vertex<E>, ArrayList<Edge<E>>> vertices) {
		this.vertices = vertices;
	}

	public BFSGraph(Map<Vertex<E>, ArrayList<Edge<E>>> vertices1) {
		this.vertices = vertices1;
	}

	public BFSGraph() {
		this.vertices = new HashMap<>();
	}
	

	@Override
	public boolean contains(Object o) {
		if (o == null) return false;
		Set<Vertex<E>> vertexSet = this.getVertexSet();
		if (o instanceof Vertex<?> && vertexSet.contains(o)) {;
			return true;
		} else if (o instanceof Edge<?>) {
			for (ArrayList<Edge<E>> edgeList : this.vertices.values()) {
				if (edgeList.contains(o)) return true;
			}
		}
		return false;
	}
	
	@Override
	public Set<Vertex<E>> getVertexSet() {
		return this.vertices.keySet();
	}

	public boolean isInVertexSet(Vertex<E> v){
		  for(Vertex<E> temp : this.vertices.keySet()){
//		   System.out.println("Original: " + temp.toString());
//		   System.out.println("Other: " + v.toString());
		   if(temp.equals(v)) return true;
		  }
		  return false;
		 }

	public Vertex<E> findVertex(E contents) {
			Set<Vertex<E>> vertexSet = this.getVertexSet();
			for (Vertex<E> vertex : vertexSet) {
				if (vertex.contents.equals(contents)) {
					return vertex;
				}
			}
			return new Vertex<>(contents); //if not found, make the vertex
	}

	@Override
	public ArrayList<Edge<E>> getEdgesFromVertex(Vertex<E> v) {
		return this.vertices.get(v);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BFSGraph [vertices=" + vertices.toString() + "]";
	}

}