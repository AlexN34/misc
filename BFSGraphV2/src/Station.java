
public class Station {
	private String name;
	private int layover;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Station [name=" + name + ", layover=" + layover + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + layover;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Station))
			return false;
		Station other = (Station) obj;
		if (layover != other.layover)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the layover
	 */
	public int getLayover() {
		return layover;
	}
	/**
	 * @param layover the layover to set
	 */
	public void setLayover(int layover) {
		this.layover = layover;
	}
	
	public Station() {
		this.name = "";
		this.layover = 0;
	}

	public Station(String name, int layover) {
		this.name = name;
		this.layover = layover;
	}

	public Station(String parsedLine) {
		String[] tokens = parsedLine.split(" ");
		this.setLayover(Integer.parseInt(tokens[1]));
		String substr = ""; //0th token is Vertex, 1st is contents
		for (int i = 2; i < tokens.length; i++) {
			substr += tokens[i];
		}
		this.setName(substr);
	}
}