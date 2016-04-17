package tutwk2;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class testManagerEmployee {
	public static void main(String[] args) {
		System.out.println("About to set and print out details...");
		Employee e1 = new Employee("Bob Jenkins", 60000);
		Employee e2 = new Employee("Bob Jenkins", 60000);
		Calendar curDate = new GregorianCalendar(2016, 3, 8);
		Manager m1 = new Manager("Bob Zenkins", 150000, curDate);
		Calendar changedDate = new GregorianCalendar(2016, 3, 9);
		Manager m2 = new Manager("Bob Genkins", 60000, curDate);

		System.out.println(e1.toString());
		System.out.println(e2.toString());
		System.out.println(m1.toString());
		System.out.println(m2.toString());

		if (m2.equals(e2)) {
			System.out.println("Equals methods are correct and showing expected booleans");
		} else {
			System.out.println("Unexpected result -- one is not equal to the other");
		}
		Manager m3 = m2.clone();
		m3.setHireDate(changedDate);
		if (m3.getHireDate().equals(m2.getHireDate())) {
			System.out.println("Clone methods are correct and yielding clones");
		} else {
			System.out.println("Unexpected result -- clones are not the same");
			System.out.println(m2.toString());
			System.out.println(m3.toString());
		}

	}

}
