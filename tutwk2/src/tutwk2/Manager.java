package tutwk2;

import java.util.Calendar;

public class Manager extends Employee {
	private Calendar hireDate;
	public Manager (String name, int salary, Calendar hireDate) {
		super(name, salary);
		this.hireDate = hireDate;
	}
	
	public Calendar getHireDate () {
		return this.hireDate;
	}
	
	public void setHireDate (Calendar hireDate) {
		this.hireDate = hireDate;
	}
	
	@Override
	public String toString () {
		return super.toString() + "{ hireDate = " + this.hireDate + "}";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!super.equals(o)) // don't need to refer to this, super infers
			return false;
		Manager m = (Manager) o;
		return m.hireDate.equals(this.hireDate);
	}
	@Override
	public Manager clone() {
		Manager m = (Manager) super.clone();
		m.hireDate = this.hireDate;
		return m;
	}
}
