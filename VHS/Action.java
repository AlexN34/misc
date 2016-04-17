import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

public class Action implements Request, Cancel, Change, Cloneable {
	String name;
	LinkedHashMap<String, Integer> roomType; //stores how many of each room type is needed
	int id;
	Calendar start;
	Calendar end;

//	Construct Action - leave Calendar to be set later
	public Action (String name, int id) {
		this.name = name;
		this.roomType = new LinkedHashMap<String, Integer>(); 
		this.id = id;
		this.start = Calendar.getInstance();
		this.end = Calendar.getInstance();
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
	 * @return the roomTypes [contains numbers of each room type in hash]
	 */
	public LinkedHashMap<String, Integer> getRoomType() {
		return roomType;
	}

	/**
	 * @param type the type to set
	 */
	public void setRoomType(LinkedHashMap<String, Integer> roomType) {
		this.roomType = roomType;
	}

	public void addRoomType(String roomType, int number) {
		this.roomType.put(roomType, Integer.valueOf(number));
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the start
	 */
	public Calendar getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Calendar start) {
		this.start = start;
	}
	
	public void setStartFromInput(String startMonth, int startDay) {
		this.start.set(Calendar.MONTH, monthToInt(startMonth));;
		this.start.set(Calendar.DAY_OF_MONTH, startDay);;
		timeZero(start);
	}
	
	private int monthToInt(String month) {
		Hashtable<String, Integer> conversionTable = new Hashtable<String, Integer>();
		conversionTable.put("Jan", 0);
		conversionTable.put("Feb", 1);
		conversionTable.put("Mar", 2);
		conversionTable.put("Apr", 3);
		conversionTable.put("May", 4);
		conversionTable.put("Jun", 5);
		conversionTable.put("Jul", 6);
		conversionTable.put("Aug", 7);
		conversionTable.put("Sep", 8);
		conversionTable.put("Oct", 9);
		conversionTable.put("Nov", 10);
		conversionTable.put("Dec", 11);
		return conversionTable.get(month);
	}

	/**
	 * @return the end
	 */
	public Calendar getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Calendar end) {
		this.end = end;
	}
	
	public void setEndFromInput(String endMonth, int endDay) {
		this.end.set(Calendar.MONTH, monthToInt(endMonth));;
		this.end.set(Calendar.DAY_OF_MONTH, endDay);;
		timeZero(end);
	}

	
	private void timeZero(Calendar c) {
		//Force later compareTo to only consider dates rather than time of day
		//Dates will roll onto each other evenly
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
	}
	//To identify for cancel/change
	@Override
	public boolean equals(Object o) {
		if (this == o) 
			return true;
			// check if refer to same plus - object is equal to itself
		if (o == null || this.getClass() != o.getClass())
			return false;
			// not equal if classes differ or null
			Action a = (Action) o;
		return a.name.equals(this.name) && a.roomType.equals(this.roomType) && a.id == this.id &&
				a.start.equals(this.start) && a.end.equals(this.end);	//check that this works..
	}
	


	public int duration() {
		int i = 0;
		Calendar curDate = this.start;
		while (curDate.compareTo(end) <= 0) {
			i++;
			curDate.roll(Calendar.DAY_OF_MONTH, true);
		}
		return i;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Action [name=" + name + ", roomType=" + roomType + ", id=" + id + ", start=" + 
				start.getTime().toString() + ", end=" + end.getTime().toString()
				+ "]";
	}

	//Functions only return id code and booking changes -- titles have to put in externally
	//by calling functions
	@Override
	public String requestBooking(VenueHireSystem vhs) {
		String returnString = "";
		Set<String> bookedStrings = new TreeSet<String>();
		ArrayList<Room> roomsToBook = new ArrayList<Room>(); //save rooms to book on isAvailable pass, then book all
		bookedStrings.add(String.valueOf(this.id));
		int i;
//		System.out.println(this.toString());
//		System.out.println("typestobook: " + this.roomType.keySet());
		for (String typeToBook : this.roomType.keySet()) { //how to do it n in hash times
//			System.out.println("In outside loop: " + typeToBook);
			nextType:
			for (i = 0; i < this.roomType.get(typeToBook); i++) {
				for (Venue v: vhs.getVenueList()) {
					for (Room r : v.getRoomList()) {
						returnString += "{" + r.getName() + "} \n";
						if (typeToBook.equals(r.getType())) {
							if (r.isAvailable(this)) {
								roomsToBook.add(r);
//								System.out.println(String.valueOf(i));
//								System.out.println(String.valueOf(this.roomType.get(typeToBook)) + "just added room. increment i:" +
//								"Also book: " + r.getName());
								i++;
								bookedStrings.add(v.getName()); //if from same venue not added - no duplicates
								bookedStrings.add(r.getName());
//								System.out.println(v.getName() + " " + r.getName());
								break nextType;
							} 
						}
					}
				} 
							//bookingStatus = false;
							//break outerloop; //if any rooms of right type don't work, request rejected
			}
		}
		//		System.out.println("endo f loops");
		if (!roomsToBook.isEmpty()) {
			returnString = "Reservation " + String.valueOf(this.id);
			for (Room rB : roomsToBook) {
				rB.bookRoom(this);
			}
			for (Venue v : vhs.getVenueList()) {
				if (bookedStrings.contains(v.getName())){
					returnString += " " + v.getName();
				}
				for (Room r: v.getRoomList()) {
					if (roomsToBook.contains(r)) {
						returnString = returnString + " " + r.getName();
//						System.out.println(r.getName());
					}
				}
			}
		} else {
			returnString = "Request rejected";
		}

//		System.out.println("Final returnString: " + returnString);
		return returnString;
	}

	@Override
	public String cancelBooking (VenueHireSystem vhs) {
		for (Venue v : vhs.getVenueList()) {
					for (Room r : v.getRoomList()) {
						//rotate through types. currently rotating through every room at this venue. 
						//Add venue name to returnString before next venue
						if (r.containsAction(this)) {
//							System.out.println(this.toString());
							if(!r.freeRoom(this)) { //if freeRoom false, unable to remove - didnt exist
								return "Cancel rejected";
							}
						} 
					}
		}
		return "Cancel " + String.valueOf(this.id);
	}
	@Override
	public String changeBooking (VenueHireSystem vhs) {
		//check if exists? - maybe interface function needed
		//find action with same id
		Action actionToCancel = null; //dummy for now
		try {
			outerloop:
			 for (Venue v : vhs.getVenueList()) {
					for (Room r : v.getRoomList()) {
						//TODO: Fix up once room booking logic is complete
						//rotate through types. currently rotating through every room at this venue. 
						//Add venue name to returnString before next venue
						if (r.containsID(this.id)) {
							actionToCancel = r.getBooking(this.id);
							break outerloop;
						}
					}
			}	
		} catch (Exception e) {
			System.out.println("actionToCancel was not found");
			return " rejected";
		}
		actionToCancel.cancelBooking(vhs); //discard return string
//		System.out.println("Just cancelled booking, action: " + this.toString());
		return this.requestBooking(vhs);	
	}

}
/*
 * UML Diagram (s) below 'Class Diagram
 * 
 * @startuml
 * 
 * 
 * VenueHireSystem o-- Action 
 * VenueHireSystem *-- Venue
 * 
 * Request<|-- Change
 *  Cancel<|-- Change
 * 
 * Action ..|> Request
 *  Action ..|> Cancel
 *   Action --> Room
 * 
 * 

 * 
 * class Action { 
 * -String name 
 * -String type
 * -LinkedHashMap<String, Integer> roomType 
 * -int id 
 * -Calendar start
 * -Calendar end
 * +duration(start:CustomDate, end:CustomDate): int
 * + <<constructor>> Action() + setters() and * getters() 
 * + equals(o:Object)
 * + clone(o:Object)
 * }
 * 
 * class VenueHireSystem { 
 * -Venue[] venues 
 * -Action[] actions 
 * - scanInput() : * String 
 * - venueParse(input:String) 
 * - actionParse(input:String) -
 * printOutput(): String 
 * -setters() and +getters() 
 * + <<constructor>> VenueHireSystem(roomReq: List<Room>, name:String, type:String, id:int, dateRequired:String[])
 * -actionExists(a:Action[]): boolean
 * +contains(o:Object):boolean
 *  }
 * 

 * Venue *-- Room
 * DateData --* Room
 *  
 *  class Venue {
 *  -LinkedHashMap<String, Room> rooms 
 *  -String name 
 *  + <<constructor>> Venue(rooms: LinkedHashMap<String, Room>, name:String) 
 * + getName() : String
 * + getRoomNames() : String
 * + addRoom(rooms:LinkedHashMap, r:Room)
 * + delRoom(rooms:LinkedHashMap, r:Room)
 * + roomExists(r:Room)
 * + printRoomData(r:Room)
 * } 
 * 
 * class Room {
 *  -String name
 *  -String type
 *  -DateData bookedDates
 *  -TreeSet<Action> reservations
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
 * interface Request {
 * 		+requestBooking(a:Action):String
 * }
 *  
 * interface Cancel {
 * +cancelBooking(a:Action):String 
 * }
 * 
 * interface Change {
 +changeBooking(a:Action):String 
 * }
 * @enduml 'Use cases
 * 
 * @startuml :User: (Request reservation) User --> (Request)
 * 
 * User --> (Change)
 * 
 * User --> (Cancel)
 * 
 * @enduml
 * 
 */