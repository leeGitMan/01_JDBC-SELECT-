package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee3;

public class JDBCExample6 {
	

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		

		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		// 입사일을 입력받아
		// 01 ) 이름 / 년도 / 성별
		
		
		try {
			
			System.out.print("입사일 입력 (YYYY-MM-DD) ");
			String input = sc.next();
			
			
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') 입사일 , DECODE(SUBSTR(EMP_NO, 8 ,1), '1', 'M','F') 성별\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "WHERE TO_CHAR(HIRE_DATE, 'YYYYMMDD') < TO_CHAR('" + input + "')";
			
			conn = DriverManager.getConnection(url, user, pw);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			List<Employee3> list = new ArrayList<>();
			
			while(rs.next()) {
				
				
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("입사일");
				char gender = rs.getString("성별").charAt(0);
				
				
				list.add(new Employee3(empName, hireDate, gender));
			}
			
			if(list.isEmpty()) {
				System.out.println("조건 없음");
			}else {
				for(Employee3 emp : list) {
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
