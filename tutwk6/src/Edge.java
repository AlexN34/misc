
public class Edge<E>{
	Vertex<E> src;
	Vertex<E> dest;
	int weight;

	public Edge(Vertex<E> from, Vertex<E> to, int weight) {
		super();
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
		return "Edge [src=" + src.toString() + ", dest=" + dest.toString() + ", weight=" + weight + "]";
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
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (src == null) {
			if (other.src != null)
				return false;
		} else if (!src.equals(other.src))
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}
}
