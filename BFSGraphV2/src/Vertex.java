import java.util.ArrayList;

public class Vertex<E> {
//	String id;

	E contents;
	ArrayList<Edge<E>> connectedEdges;
	
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

	/**
	 * @return the connectedEdges
	 */
	public ArrayList<Edge<E>> getConnectedEdges() {
		return connectedEdges;
	}
	/**
	 * @param connectedEdges the connectedEdges to set
	 */
	public void setConnectedEdges(ArrayList<Edge<E>> connectedEdges) {
		this.connectedEdges = connectedEdges;
	}

	public boolean addConnectedEdge(Edge<E> e) {
		return this.connectedEdges.add(e);
	}

	public boolean removeConnectedEdge(Edge<E> e) {
		return this.connectedEdges.remove(e);
	}

	public Vertex (/*String id,*/ E contents, ArrayList<Edge<E>> connectedEdges) {
//		this.id = id;
		this.contents = contents;
		this.connectedEdges = connectedEdges;
	}

	public Vertex (/*String id, */E contents) {
//		this.id = id;
		this.contents = contents;
		this.connectedEdges = new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nVertex [\ncontents=" + contents + "\nconnectedEdges=" + connectedEdges + "\n]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connectedEdges == null) ? 0 : connectedEdges.hashCode());
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		return result;
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
		if (!(obj instanceof Vertex))
			return false;
		Vertex<?> other = (Vertex<?>) obj;
		if (connectedEdges == null) {
			if (other.connectedEdges != null)
				return false;
		} else if (!connectedEdges.equals(other.connectedEdges))
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		return true;
	}


}