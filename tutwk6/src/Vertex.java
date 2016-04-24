//import java.util.ArrayList;

public class Vertex<E> {
//	String id;

	E contents;
//	ArrayList<Vertex<E>> adjacentVertices;
	
	public E getContents() {
		return contents;
	}
	public void setContents(E contents) {
		this.contents = contents;
	}
//	public String getId() {
//		return id;
//	}
	
//	public void setId(String id) {
//		this.id = id;
//	}

	public Vertex (E contents/*, ArrayList<Edge<E>> connectedEdges*/) {
//		this.id = "";
		this.contents = contents;
//		this.adjacentVertices = adjacentVertices;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vertex [contents=" + contents.toString() + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Vertex<?> other = (Vertex<?>) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		return true;
	}
	
}