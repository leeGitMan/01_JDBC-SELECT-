package edu.kh.jdbc1.model.vo;

public class Employee3 {

	private String empName;
	private String hireDate;
	private char gender;
	
	
	public Employee3() {}


	public Employee3(String empName, String hireDate, char gender) {
		super();
		this.empName = empName;
		this.hireDate = hireDate;
		this.gender = gender;
	}
	
	
	public String empName() {
		return empName;
		
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String hireDate() {
		return hireDate;
	}
	
	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}
	
	public char gender() {
		return gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return empName + " / " + hireDate + " / " + gender;
	}
	
	
	
	
}
