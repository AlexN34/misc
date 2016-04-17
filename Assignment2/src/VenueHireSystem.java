import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class VenueHireSystem {
	private LinkedHashMap<String, Venue> venues;
	//Use a list? keep numbered actions 0-max

	/* CONSTRUCTORS */
	public VenueHireSystem() {
		this.venues = new LinkedHashMap<String, Venue>();
	}

	/* GETTERS AND SETTERS BELOW */
	private LinkedHashMap<String, Venue> getVenues() {
		return venues;
	}



	private void addVenue(Venue v) {
		this.venues.put(v.getName(), v);
	}
	
	private Set<String> getVenueNames() {
		return this.venues.keySet();
	}

	public ArrayList<Venue> getVenueList() {
		ArrayList<Venue> l = new ArrayList<Venue>();
		for (String key : this.getVenueNames()) {
//			System.out.println("in getVenueList(): " + key);
			l.add(this.getVenues().get(key));
		}
		return l;
	}





	/* ========= HELPER FUNCTIONS ========= */

	/* PARSER FUNCTIONS BELOW */
	/**
	 * Parse input from plaintext
	 *
	 * @param  String  input
	 * @param  name the location of the image, relative to the url argument
	 * @return      Object for Parser to set
	 * @see Parser
	 */
	private Venue venueParse(String venueString) {
		// Split into format: Venue <name> <roomname> <type>
		String[] tokens = venueString.split("\\s");
		Venue v = new Venue(tokens[1]); //initialise name, 0th token is identifier
		if (this.getVenueNames().contains(tokens[1])) {
			v = this.getVenues().get(tokens[1]);
		}

		for (int  i = 2;  i < tokens.length; i += 2) {
			Room r = new Room(tokens[i], tokens[i+1]);
			v.addRoom(r);
		}
//		System.out.println("parsed: " + venueString.toString());
		return v;
	}

	private Action actionParse(String actionResultstring) {
		String[] tokens = actionResultstring.split("\\s");
		//put stuff in properly
		Action action = new Action(tokens[0], Integer.parseInt(tokens[1]));

		/*	Token legend:
		 * 0 - Action name
		 * 1 - Action id
		 * 2 - Start month
		 * 3 - Start day
		 * 4 - End month
		 * 5 - End day
		 * 6 onwards - #room requested and each type
		 * Request <id> <month1> <date1> <month2> <date2> <number1> <type1> <number2> <type2> . . .
		 *		Request: id, may 26 to may 27, (how many, type 1)+
		 *		Change: id, may 26 to may 27, (how many, type 1)+
		 *		Cancel: id 
		 * */
		if (tokens[0].equals("Request") || tokens[0].equals("Change") ) {
			action.setStartFromInput(tokens[2], Integer.parseInt(tokens[3]));
			action.setEndFromInput(tokens[4], Integer.parseInt(tokens[5]));
			for (int  i = 6;  i < tokens.length; i += 2) {
				action.addRoomType(tokens[i+1], Integer.parseInt(tokens[i]));
			}
		} else if (tokens[0].equals("Cancel")) {
			for (Venue v : this.getVenueList()) {
				for (Room r : v.getRoomList()) {
					if (r.containsID(Integer.parseInt(tokens[1]))) {
						action = r.getBooking(Integer.parseInt(tokens[1])); 
						action.name = "Cancel";
					}
				}
			}
		}

		return action;
	}

	// Provides string containing venue that needs to be printed out
	private String printParse(String printString) {
		Pattern p = Pattern.compile("^Print\\s(\\w+)");
		Matcher m = p.matcher(printString);
		m.find();
		for (String vName : this.venues.keySet()) {
			if (vName.equals(m.group(1))) { //not necessary, should only print existing venues
				return this.venues.get(vName).toString();
			}
		}
		return "Print rejected";


	}
	
	/**
	 * Calls various parser functions and uses the respective class functions to
	 * Obtain an output result for each request
	 * The url argument must specify an absolute {@link URL}. The name
	 * argument is a specifier that is relative to the url argument. 
	 * <p>
	 * This method always returns immediately, whether or not the 
	 * image exists. When this applet attempts to draw the image on
	 * the screen, the data will be loaded. The graphics primitives 
	 * that draw the image will incrementally paint on the screen. 
	 *
	 * @param  input  raw txt input string
	 * @return     String containing output message 
	 * @see         Image
	 */
	public String Parser(String input) {
		String output = "";
		if (input.matches("^Venue\\s.*")) {
//			System.out.println("Matched venue. Line is: " + input);
			Venue newVenue = this.venueParse(input);
			this.addVenue(newVenue);
//			String roomNames = newVenue.getRoomNames().toString();
//			output = "Just added: " + newVenue.getName() + " " + roomNames;

			

		} else if (input.matches("^Print\\s.*")) {
			// Print. after parsing which venue to print, do the
			// printing
//			System.out.println("Matched Print. Line is: " + input);
			output = this.printParse(input);

		} else {
			// Action. after parsing and setting, get response and add
			// to actionResults list
//			System.out.println("Matched else. Line is: " + input);
		
			Action curAction = this.actionParse(input);
			if (curAction.getStart().compareTo(curAction.getEnd()) > 0) {
				output = curAction.name + " rejected";
				return output;
			}
//			output = curAction.toString() + " - output below:\n";
			switch (curAction.name) {
			case "Request":
				output += curAction.requestBooking(this);
				break;
			case "Cancel":
				curAction.name = "Request"; //changed to differentiate from first case
				output += curAction.cancelBooking(this);
				break;
			case "Change":
				output += curAction.changeBooking(this);
				
				Pattern p = Pattern.compile("Reservation");
				Matcher m = p.matcher(output);
				output = m.replaceFirst("Change"); 
				
				break;
			default:
				break;
			}

			//Do stuff to get result
			//addActionOutput
		}	
		

		return output; //output string made up of all actionOutput
	}

	/* Print VenueHireSystem contents maybe not req'd anymore */

	/* PROCESS ACTION OBJECTS FROM PARSE */
	/*
	private String actionResult(Action actionToCheck) {
		return "dummy";
	} */

	/* RUNTIME EXECUTABLE CODE */
	/* ANEITA: My code produces output that isn't exactly like the sample..
	 * I have a bug somewhere that prints empty strings upfgront. Otherwise the content
	 * Is -pretty- accurate other than the print part.
	 * See my JUnit action test: if set up properly, everything works.
	 * */
	public static void main(String[] args) {
		// Initialise system contents as empty lists
		VenueHireSystem vhs = new VenueHireSystem();
		// Scan from input for stuff to put into system s
		Scanner sc = null; // sc is now the input file.
		try {
			// args[0] is the first command line argument
			sc = new Scanner(new FileReader(args[0]));
			// set first argument as object to scan through
			while (sc.hasNext()) {
				System.out.println(vhs.Parser(sc.nextLine())); 

			}
		} catch (FileNotFoundException e) {
		} finally {
			// once done with file, close to free memory
			if (sc != null)
				sc.close();
		}

//		System.out.println("Scan finished");
	}

}

/*
 * UML Diagram (s) below 'Class Diagram
 * 
 * @startuml
 * 
 * 
 * VenueHireSystem *-- Venue
 * Action --* Room
 *  Venue *-- Room
 * 
 * Request<|-- Change
 *  Cancel<|-- Change
 * 
 * Action ..|> Request
 *  Action ..|> Cancel
 * 
 * DateData --* Room
 * 
 * class Room {
 *  -String name
 *  -String type
 *  -DateData availabilities +
 * <<constructor>> Room(name:String, type:String) 
 * +  isAvailable(request:Action) : Boolean 
 * + bookRoom(request:Action)
 * +  freeRoom(request:Action): Boolean 
 * + containsAction(a:Action):Boolean
 * + containsID(id:int):Boolean
 * + getBooking(id:int): Action
 * +getName():String
 * +getType():String
 * }
 * 
 * class Venue {
 *  -LinkedHashMap<String, Room> rooms 
 *  -String name 
 *  + <<constructor>> Venue(name:String) 
 *  +getters() for all private fields
 *  +setters() for all private fields
 *  +getRoomList():ArrayList<Room>
 *  +toString()
 *  +addRoom(r:Room)
 *  +delRoom(r:Room)
 *  +roomExists(String roomName):Boolean
 *  
 *  + isAvailable(date:String) : Boolean 
 *  + * bookRoom(date:String) : String 
 *  + freeRoom(date:String) : String
 *  -setName(name: String) 
 *  - setType(type: String) 
 * + getName() : String
 * + * getType() : String 
 * }
 * 
 * class Action { 
 * -String name 
 * -LinkedHashMap<String, Integer> roomType
 * -int id 
 * -Calendar start
 * -Calendar end
 * +duration(start:CustomDate, end:CustomDate): int
 * + <<constructor>> Action()
 * + setters() for all private fields
 *   +getters() for all private fields
 *   +addRoomType(roomType:String, number:int)
 *   +setStartFromInput(startMonth:String, startDay:int)
 *   +setEndFromInput(endMonth:String, endDay:int)
 *   +clone(): Action
 *   +duration():int
 *   +toString():String
 *   +<<interface>> requestBooking(vhs:VenueHireSystem):String
 *   +<<interface>> cancelBooking(vhs:VenueHireSystem):String
 *   +<<interface>> changeBooking(vhs:VenueHireSystem):String
 * }
 * 
 * class VenueHireSystem { 
 * -LinkedHashMap<String, Venues> venues 
 * + Parser(input:String) :String 
 * + <<constructor>> VenueHireSystem()
 * +getVenueList(): ArrayList<Venue> 
 *  }
 * 
 * class DateData { 
 * -Set<Calendar> bookedDates 
 * +<<constructor>> DateData(dates:Set<Calendar>)
 * +toString():String
 * +isAvailable(date:CustomDate) :Boolean
 * +addDate(d:Calendar)
 * +removeDate(d:Calendar)
 * }
 * 

 * 
 * 
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