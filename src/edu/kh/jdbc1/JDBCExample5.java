package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee2;

public class JDBCExample5 {
	public static void main(String[] args) {
		// 입사일을 입력("2022-09-06") 받아
		// 입력 받은 값보다 먼저 입사한 사람의
		// 이름, 입사일, 성별(M,F)조회
		
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.print("입사일 입력 :");
			String input = sc.next();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "kh";
			String pw = "kh1234";
			
			conn = DriverManager.getConnection(url,user,pw);
			
			String sql = "SELECT EMP_NAME ,TO_CHAR(HIRE_DATE, 'YYYY\"년\" MM\"월\" DD\"일\"') 입사일, "
					+ " DECODE(SUBSTR(EMP_NO, 8 ,1), '1' , 'M', 'F') AS GENDER\r\n "
					+ " FROM EMPLOYEE "
					+ " WHERE HIRE_DATE <  TO_DATE('" + input + "')";
			
			// 문자열 내부에 쌍따옴표 작성 시, \" 로 작성해야한다.(Escape 문자)
			// Java의 char는 문자 한 개를 의미하는데
			// DB에서 char는 고정 길이 문자열을 의미한다. --> String을 의미
			
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			List<Employee2> list = new ArrayList<>();
			
		
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("입사일");
				String gender = rs.getString("GENDER");
				
				list.add(new Employee2(empName, hireDate, gender));
				
			}
			
			if(list.isEmpty()) {
				System.out.println("조회 값 없음");
			}else {
				for(Employee2 emp : list) {
					System.out.println(emp);
				}
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) rs.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}		
	}
}
