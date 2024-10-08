package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhonebookDao {

	// 필드
	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/phonebook_db";
	private String id = "phonebook";
	private String pw = "phonebook";
	// 생성자
	// 기본생성자 사용(그래서 생략)

	// 메소드 gs
	// 필드값을 외부에서 사용하면 안됨(그래서 생략)

	// 메소드 일반
	// DB연결 메소드
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원정리 메소드
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 수정하기

	public int updatePerson(PersonVo personVo) {
		int count = -1;
		this.getConnection();
		try {
			String query = "";
			query += " update person ";
			query += " set name =? ";
			query += " 		,hp =? ";
			query += " 		,company = ? ";
			query += " where person_id=? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());
			
			count = pstmt.executeUpdate();
			
			System.out.println("결과"+count);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();

		return count;
	}

	// 사람정보 한명 불러오기
	public PersonVo getPersonOne(int no) {
		PersonVo personVo = null;
		this.getConnection();

		try {
			// sql문 준비 / 바인딩(말랑말랑) / 실행
			String query = "";
			query += " select person_id ";
			query += " 		  ,name ";
			query += " 		  ,hp ";
			query += " 		  ,company ";
			query += " from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();
			// 결과처리
			rs.next();
			int id = rs.getInt("person_id");
			String name = rs.getString("name");
			String hp = rs.getString("hp");
			String company = rs.getString("company");

			personVo = new PersonVo(id, name, hp, company);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return personVo;
	}

	// 사람정보 저장
	public int insertPerson(PersonVo personVo) {

		int count = -1;

		this.getConnection();

		try {

			// sql문 준비 / 바인딩(말랑말랑) / 실행

			String query = "";
			query += " insert into person ";
			query += " values(null, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		
		return count;
	}

	// 리스트 가져오기

	public List<PersonVo> getPersonList() {

		List<PersonVo> personList = new ArrayList<PersonVo>();

		this.getConnection();

		try {

			// sql문 준비 / 바인딩(말랑말랑) / 실행

			String query = "";
			query += " select person_id ";
			query += " 		,name ";
			query += " 		,hp ";
			query += "    	,company ";
			query += " from person ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			// 결과처리
			while (rs.next()) {
				int id = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(id, name, hp, company);

				personList.add(personVo);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return personList;
	}

}
