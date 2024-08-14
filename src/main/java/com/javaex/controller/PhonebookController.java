package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhonebookDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// controller 접수 받는 일
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 접수
		String action = request.getParameter("action");
		System.out.println(action);

		if ("list".equals(action)) {
			// db데이터 가져오기
			PhonebookDao phonebookDao = new PhonebookDao();
			List<PersonVo> personList = phonebookDao.getPersonList();

			// 화면그리기 -->포워드
			// request 에 리스트 주소 넣기
			request.setAttribute("personList", personList);

//		List<PersonVo> personList =(List<PersonVo>)request.getAttribute("personList");

			// 포워드
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
			rd.forward(request, response);

		} else if ("writeform".equals(action)) {

			System.out.println("등록폼 요청");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
			rd.forward(request, response);

		} else if ("insert".equals(action)) {

			System.out.println("등록 요청 데이터3개 저장해줘");

			// 나머지 데이터 꺼내기
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			PersonVo personVo = new PersonVo(name, hp, company);
//			personVo.setName(name);
//			personVo.setHp(hp);
//			personVo.setCompany(company);

			// Dao의 메모리에 올린다
			PhonebookDao phonebookDao = new PhonebookDao();

			// insertPerson(personVo) 사용해서 db에 저장한다.
			phonebookDao.insertPerson(personVo);

			// getPersonLsit() 사용해 리스트를 불러온다.

//			List<PersonVo> personList = phonebookDao.getPersonList();
//			
//			//화면그리기 -->포워드
//			//request 에 리스트 주소 넣기
//			request.setAttribute("personList", personList);
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
//			rd.forward(request, response);

			// 리다이렉트
			response.sendRedirect("/phonebook2/pbc?action=list");

		} else if ("editform".equals(action)) {

			System.out.println("수정폼 요청");
			int no = Integer.parseInt(request.getParameter("no"));

			PhonebookDao phonebookDao = new PhonebookDao();
			PersonVo personVo = phonebookDao.getPersonOne(no);

			request.setAttribute("personVo", personVo);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/editForm.jsp");
			rd.forward(request, response);

		} else if ("update".equals(action)) {
			System.out.println("수정 요청");
			
			int no = Integer.parseInt(request.getParameter("no")) ;
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			PersonVo personVo = new PersonVo(no, name, hp, company);
			System.out.println(personVo);
			
			//phonebookDao를 메모리에 올린다.
			PhonebookDao phonebookDao = new PhonebookDao();
			
			//phonebookDao를 통해서 수정을 시킨다
			phonebookDao.updatePerson(personVo);
		
			response.sendRedirect("/phonebook2/pbc?action=list");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
