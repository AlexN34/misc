import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeSet;

public class Room {
	private String name;
	private String type;
	private DateData bookedDates;
	private LinkedList<Action> reservations; //use something else, can't order reservations

	// GETTERS AND SETTERS
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the reservations
	 */
	public LinkedList<Action> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(LinkedList<Action> reservations) {
		this.reservations = reservations;
	}

	//Print room contents
	@Override
	public String toString(){
		String returnString = this.name;
		for (Action action : reservations) {
//			returnString += action.toString();
			returnString = returnString + " " + intToMonth(action.start.get(Calendar.MONTH)) +
							" " + String.valueOf(action.start.get(Calendar.DAY_OF_MONTH)); 
			returnString = returnString +  " " + String.valueOf(action.duration());
		}
		return returnString;
	}
	
	/**
	 * Converts input to 3 letter form from format understandable by Calendar
	 *
	 * @param  monthInt integer representing currentMonth
	 * @return      3 digit month code e.g Jan
	 */
	private String intToMonth (int monthInt) {
		Hashtable<Integer, String> conversionTable = new Hashtable<Integer, String>();
		conversionTable.put(0, "Jan");
		conversionTable.put(1, "Feb");
		conversionTable.put(2, "Mar");
		conversionTable.put(3, "Apr");
		conversionTable.put(4, "May");
		conversionTable.put(5, "Jun");
		conversionTable.put(6, "Jul");
		conversionTable.put(7, "Aug");
		conversionTable.put(8, "Sep");
		conversionTable.put(9, "Oct");
		conversionTable.put(10, "Nov");
		conversionTable.put(11, "Dec");
		return conversionTable.get(monthInt);
	}
	//CONSTRUCTOR
	public Room(String name, String type) {
		this.name = name;
		this.type = type;
		this.bookedDates = new DateData(new TreeSet<Calendar>()); //empty - all rooms start with none booked
		this.reservations = new LinkedList<Action>();
	}

	//	HELPER METHODS
	//	Checks whether dates between start and free exists in bookedDates
	public boolean isAvailable(Action request) {
	//TODO: Handle incorrect input -- throw exception if end > start?
		//TODO: Handle multiple numbers, multiple types
		Calendar start = request.start;
		Calendar end = request.end;
		Calendar curDate = (Calendar) start.clone();
		while (curDate.compareTo(end) <= 0) {
//			System.out.println(this.getName() + " :");
			if (!this.bookedDates.isAvailable(curDate)) {
				return false;
			}
			curDate.add(Calendar.DATE, 1);
		}
		return true;
		
	}

	/**
	 * Performs booking for action (request) if legal operation
	 *
	 */
	public void bookRoom(Action request) {
		if (isAvailable(request)) {
			//Keep track of reservation objects? keep a list of actions
			Calendar curDate = (Calendar) request.start.clone();
			while (curDate.compareTo(request.end) <= 0) {
				this.bookedDates.addDate(curDate);
				curDate = (Calendar) curDate.clone();
				curDate.add(Calendar.DATE, 1);
			}
			this.reservations.add(request);
			//return this.name; //nothing went wrong if got here
			//TODO perhaps incorporate exception
		} else {
			//return "Request Rejected";
		}

	}

	/**
	 * Removes a current reservation on the Room identified as a previous action object
	 *
	 * @param  request  action to book parsed from input
	 */
	public boolean freeRoom(Action request) {
		if(!isAvailable(request)) {
			Calendar curDate = (Calendar) request.start.clone();
			while (curDate.compareTo(request.end) <= 0) {
				curDate = (Calendar) curDate.clone();
				this.bookedDates.removeDate(curDate);
//				System.out.println("Removed date, remaining: " + this.bookedDates.toString());
				curDate.add(Calendar.DATE, 1);
			}
			this.reservations.remove(request);
			return true; //successfully removed all.
			//TODO: exception?
		} else {
			return false;
		}
	}
	/**
	 * Check if this room has a booking using Action a
	 * @param  a  Action to check
	 * @return      boolean
	 * @see containsID
	 */
	public boolean containsAction(Action a) {
		for (Action bookedActions : reservations) {
			if (bookedActions.equals(a)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Check if room has booking using id 
	 *
	 */
	public boolean containsID(int id) {
		for (Action bookedActions : reservations) {
			if (bookedActions.id == id) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Get booking from id code
	 */	
	public Action getBooking(int id) {
		try {
			for (Action action : reservations) {
				if (action.id == id) {
					return action;
				}
			}
		} catch (Exception e) {
			System.out.println("Action corresponding to cancel request id not found: should Cancel Reject");
		}
		return null; //? why error?
	}
	

}

/*
 * 
 * @startuml
 * Venue *-- Room
 * DateData --* Room
 *  
 *  class Venue {
 *  -List<Room> rooms 
 *  -String name 
 *  + <<constructor>> Venue(room: * List<Room>, name:String) 
 * + getName() : String
 * + addRoom(rooms:Hashtable, r:Room)
 * + delRoom(rooms:Hashtable, r:Room)
 * } 
 * 
 * class Room {
 *  -String name
 *  -String type
 *  -DateData bookedDates
 *  -LinkedList<Action> reservations
 *  + <<constructor>> Room(name:String, type:String, availabilities:DateData) 
 * + * isAvailable(a:Action) : Boolean 
 * + bookRoom(a:Action) : String 
 * + * freeRoom(a:Action) : String 
 * - setName(name: String) 
 * - setType(type: * String) + getName() : String + getType() : String 
 * }
 * 
 * class DateData { 
 * -Calendar[] bookedDates 
 * +<<constructor>> DateData()
 * +isAvailable(d:Calendar) 
 * +addDate(d:Calendar)
 * +removeDate(d:Calendar)
 * }
 * 
 * 
 * 

 * @enduml
 */