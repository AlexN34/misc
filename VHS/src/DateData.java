import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

public class DateData {
	private Set<Calendar> bookedDates;

	@Override
	public String toString() {
		String output = "";
		for (Calendar calendar : bookedDates) {
			output += calendar.getTime().toString() ;
		}
		
		return output;
		//TODO: Figure out if actually needed..
	}

	/**
	 * Instantiates DateData
	 *
	 * @return      new DateData object
	 */	

	public DateData (Set<Calendar> dates){
		this.bookedDates = dates;
	}
	
	/**
	 * Check whether a date is available inside the dateData database
	 *
	 * @param  dates Calendar dates to check
	 * @return     boolean 
	 */
	boolean isAvailable(Calendar d) {
//		System.out.println("About to check: " + d.getTime().toString() + "bookedDates: ");
//		System.out.println(this.toString() + "empty?: " + bookedDates.isEmpty());
		Iterator<Calendar> itr = this.bookedDates.iterator();
		while (itr.hasNext()) {
			Calendar compDate = itr.next();
			Calendar curDate = (Calendar) compDate.clone();
			compDate.set(Calendar.MONTH, d.get(Calendar.MONTH));
			compDate.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH));
			if (curDate.equals(compDate)) {
				return false;
			}
		}
		return true;
		//dates.contains()
	}
	/**
	 * Add new date to DateData object - done when a date is taken up
	 *
	 * @param  date Calendar date to check
	 * @param  name the location of the image, relative to the url argument
	 * @see         removeDate
	 */	
	public void addDate(Calendar d) {
		this.bookedDates.add(d);

	}
	/**
	 * Remove date d for DateData object - done when reservation is cancelled
	 *
	 * @param  date Calendar date to check
	 * @param  name the location of the image, relative to the url argument
	 * @see         removeDate
	 */	
	public void removeDate(Calendar d) {
		this.bookedDates.remove(d);
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
 *  -DateData availabilities 
 *  + <<constructor>> Room(name:String, type:String, availabilities:DateData) 
 * + * isAvailable(date:String) : Boolean 
 * + bookRoom(date:String) : String 
 * + * freeRoom(date:String) : String 
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