import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Path implements Cloneable {
	private LinkedList<Edge<Station>> pathContents;
	private HashSet<Edge<Station>> pathClosedSet;
	private ArrayList<Edge<Station>> reqTrips;
	private int gScore;
	private int maxHeuristic;

	public Path (Edge<Station> e, ArrayList<Edge<Station>> reqTrips) {
		this.pathContents = new LinkedList<>();
		this.pathClosedSet = new HashSet<Edge<Station>>();
		this.reqTrips = reqTrips;
		pathContents.addLast(e);
		updateClosedSet(e);
		this.gScore = e.getWeight();
		this.maxHeuristic = sumReqTripWeight();
	}
	/* ======= SHALLOW CLONE CLASS, DOES NOT CREATE SEPARATE ADDRESSES ========
	public Path (Path p) {
		this.pathContents = p.getPathContents();
		this.pathClosedSet = p.getClosedSet();
		this.reqTrips = p.reqTrips;
		this.gScore = p.gScore;
		this.maxHeuristic = p.maxHeuristic;
	}
	*/

	@Override
	public Path clone() {
		Path clone = null;
	    try { //try this, if it doesn't work, then go to catch
			clone = (Path) super.clone(); //what is super? object class
							//object already has a clone method defined.
							//BUT it gives type object, have to typecast
	    } catch (CloneNotSupportedException e) {
	    	e.printStackTrace();
	    }
	        clone.pathContents = (LinkedList<Edge<Station>>) this.pathContents.clone();
	        clone.pathClosedSet = (HashSet<Edge<Station>>) this.pathClosedSet.clone();
	        clone.reqTrips = (ArrayList<Edge<Station>>) this.reqTrips.clone();
	        //reqTrips should not change, don't need to initialise a new one
	        return clone;
	}

	private void updateClosedSet(Edge<Station> e) {
		if (reqTrips.contains(e) && !pathClosedSet.contains(e)) {
			pathClosedSet.add(e);
		}
	}

	private int sumReqTripWeight() {
		int sum = 0;
		for (Edge<Station> edge : reqTrips) {
			sum += edge.getWeight();
		}
		return sum;
	}

	public void addToPath (Edge<Station> e) {
		pathContents.addLast(e);
		updateClosedSet(e);
		if (!(this.pathContents.size() <= 2)) this.gScore += e.getFrom().getContents().getTransfer(); 
		this.gScore +=  e.getWeight();
//		LA+A_trans+AL+L_trans+LBr+Br_trans+BrP+P_trans+PBe+Be_trans+BeA
	}
	
	public int getPathGScore() {
		return gScore;	
	}
	
	public int getHeuristicVal() {
		int total = 0;
		for (Edge<Station> edge : pathClosedSet) {
			total += edge.getWeight();
		}
		return maxHeuristic - total; //lower heuristic as more paths are closed i.e. more reqTrips taken
	}

	
	public Vertex<Station> getLastVertex() { //last visited/expanded vertex in the path
		return pathContents.getLast().getTo();
	}
	
	public Edge<Station> getLastEdge () { //last visited/expanded vertex in the path
		return pathContents.getLast();
	}
	
	public Set<Edge<Station>> getClosedSet() { 
		return pathClosedSet;
	}
	
	public int getReqTripsLeft() {
		return this.reqTrips.size() - this.pathClosedSet.size();
	}

	/**
	 * @return the pathContents
	 */
	public LinkedList<Edge<Station>> getPathContents() {
		return pathContents;
	}

	/**
	 * @param pathContents the pathContents to set
	 */
	public void setPathContents(LinkedList<Edge<Station>> pathContents) {
		this.pathContents = pathContents;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Path [pathContents=" + pathContents + ", pathClosedSet=" + pathClosedSet + ", reqTrips=" + reqTrips
				+ ", gScore=" + gScore + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gScore;
		result = prime * result + maxHeuristic;
		result = prime * result + ((pathClosedSet == null) ? 0 : pathClosedSet.hashCode());
		result = prime * result + ((pathContents == null) ? 0 : pathContents.hashCode());
		result = prime * result + ((reqTrips == null) ? 0 : reqTrips.hashCode());
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
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (gScore != other.gScore)
			return false;
		if (maxHeuristic != other.maxHeuristic)
			return false;
		if (pathClosedSet == null) {
			if (other.pathClosedSet != null)
				return false;
		} else if (!pathClosedSet.equals(other.pathClosedSet))
			return false;
		if (pathContents == null) {
			if (other.pathContents != null)
				return false;
		} else if (!pathContents.equals(other.pathContents))
			return false;
		if (reqTrips == null) {
			if (other.reqTrips != null)
				return false;
		} else if (!reqTrips.equals(other.reqTrips))
			return false;
		return true;
	}
	
}