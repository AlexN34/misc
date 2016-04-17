package tutwk2;


public class Employee implements Cloneable {
	private String name;
	private int salary;
	public Employee (String name, int salary) {
		this.name = name;
		this.salary = salary;
	}
	
	public String getName () {
		return this.name;
	}
	
	public int getSalary () {
		return this.salary;
	}
	public void setName (String name) {
		this.name = name;
	}
	
	public void setSalary (int salary) {
		this.salary = salary;
	}
	
	@Override
	public String toString () {
		return "{ name =" + this.name + "," + " salary = " + this.salary + "}";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) // check if refer to same plus - object is equal to
						// itself
			return true;
		if (o == null || this.getClass() != o.getClass()) // not equal if
															// classes differ or
															// null
			return false;
		Employee e = (Employee) o;
		return e.name.equals(this.name) && e.salary == this.salary;
	}
	
	@Override
	public Employee clone() {
		try {
			Employee e = (Employee) super.clone();
			return e;
		} catch (CloneNotSupportedException e) {
			System.out.println("Could not clone object..");
			return null;
		}
	}
}
