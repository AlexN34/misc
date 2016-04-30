
public class Edge<E>{
	Vertex<E> src;
	Vertex<E> dest;
	int weight;

	public Edge(AListGraph<E> g, E from, E to, int weight) throws NotInStructureException {
		this.src = g.getVertex(from);
		this.dest = g.getVertex(to);
		this.weight = weight;
	}


	public Edge(Vertex<E> from, Vertex<E> to, int weight) {
		this.src = from;
		this.dest = to;
		this.weight = weight;
	}

	public Vertex<E> getFrom() {
		return src;
	}
	public void setFrom(Vertex<E> from) {
		this.src = from;
	}
	public Vertex<E> getTo() {
		return dest;
	}
	public void setTo(Vertex<E> to) {
		this.dest = to;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nEdge [src=" + src.getContents().toString() + ",dest=" + dest.getContents().toString() + ",weight=" + weight + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dest.getContents() == null) ? 0 : dest.getContents().hashCode());
		result = prime * result + ((src.getContents() == null) ? 0 : src.getContents().hashCode());
		result = prime * result + weight;
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
		if (!(obj instanceof Edge))
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.getContents().equals(other.dest.getContents()))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.getContents().equals(other.src.getContents()))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

}

