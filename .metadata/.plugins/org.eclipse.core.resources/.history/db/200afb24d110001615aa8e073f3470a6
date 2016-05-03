
public class Station implements Comparable<Station>{
	private String name;
	private int transfer;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + name + " : " + transfer + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transfer;
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
		if (transfer != other.transfer)
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
	public int getTransfer() {
		return transfer;
	}
	/**
	 * @param layover the layover to set
	 */
	public void setTransfer(int layover) {
		this.transfer = layover;
	}
	
	
	public Station() {
		this.name = "";
		this.transfer = 0;
	}

	public Station(String name, int layover) {
		this.name = name;
		this.transfer = layover;
	}

	public Station(String parsedLine) {
		String[] tokens = parsedLine.split(" ");
		this.setTransfer(Integer.parseInt(tokens[1]));
		String substr = ""; //0th token is Vertex, 1st is contents
		for (int i = 2; i < tokens.length; i++) {
			substr += tokens[i];
		}
		this.setName(substr);
	}

	@Override
	public int compareTo(Station o) {
		int result = Integer.compare(this.transfer, o.transfer);
		return (!(result != 0)) ? result : this.getName().compareTo(o.getName());
	}
}
