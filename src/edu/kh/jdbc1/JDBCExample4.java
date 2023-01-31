package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	public static void main(String[] args) {
		
		// 직급명, 급여를 입력받아
		// 해당 직급에서 입력받은 급여보다 많이 받는 사원의
		// 이름, 직급명, 급여, 연봉을 조회하여 출력하시오.
		// 단, 조회 결과 없으면 "조회 결과 없음" 출력
		
		// 조회 결과가 있으면 아래와 같이 출력
		
		// 선동일 / 대표 / 8000000 / 9600000
		// 송종기 / 부장 / 6000000 / 7200000
		
		Scanner sc = new Scanner(System.in);
		
		
		// JDBC 객체 참조 변수 선언
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		
		
		
		try {
			
			System.out.print("직급명 입력 : ");
			String inputJobName = sc.next();
			
			System.out.print("급여 입력 : ");
			int inputSalary = sc.nextInt();
			sc.nextLine();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY, SALARY * 12 ANNUAL_INCOME\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "JOIN JOB USING(JOB_CODE)\r\n"
					+ "WHERE JOB_NAME = '" + inputJobName + "'"
					+ " AND SALARY > " + inputSalary;
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			List<Employee>list = new ArrayList<>();
			
			while(rs.next()) {
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annualIncome = rs.getInt("ANNUAL_INCOME");
				
				list.add(new Employee(empName, jobName, salary, annualIncome));
				
			}
			
			if(list.isEmpty()) {
				System.out.println("조회 결과 없음");
				
			}else {
				for(Employee emp : list) {
					System.out.println(emp);
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
