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

public class JDBCExample7 {
	public static void main(String[] args) {
		
		Scanner sc =new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		try {
			
			System.out.print("입사일 입력 (YYYY-MM-DD) ");
			String input = sc.next();
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			
			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') 입사일 , DECODE(SUBSTR(EMP_NO, 8 ,1), '1', 'M','F') 성별\r\n"
					+ "FROM EMPLOYEE\r\n"
					+ "WHERE TO_CHAR(HIRE_DATE, 'YYYYMMDD') < TO_DATE('" + input + "')" ;
			
			
			
			conn = DriverManager.getConnection(url, user, pw);
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			List<Employee> list = new ArrayList<>();
			
			
			while(rs.next()) {
				
				Employee emp = new Employee();
				
				emp.setEmpName(rs.getString("EMP_NAME"));
				emp.setHireDate(rs.getString("입사일"));
				emp.setGender(rs.getString("성별").charAt(0));
				
				list.add(emp);
				
			}
			
			if(list.size() == 0) {
				System.out.println("사원 정보가 없습니다");
				
			}else {
				for(int i = 0; i < list.size(); i++) {
					System.out.printf("%02d) / %s /%s /%c \n", i+1, list.get(i).getEmpName(), list.get(i).gethireDate(), list.get(i).getGender());
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
