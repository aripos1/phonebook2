<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.PersonVo"%>

<%
	PersonVo personVo = (PersonVo)request.getAttribute("personVo");

%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전화번호부</h1>
	<h2>전화번호-수정폼</h2>
	<p>아래의 항목을 입력한 후 수정 버튼을 클릭해주세요.</p>

	<form action="/phonebook2/pbc" method="get">

		<div>
			<label for="txt-name">이름(name) :</label> <input type="text"
				id="txt-name" name="name"  value="<%=personVo.getName()%>" placeholder="이름">
		</div>
		<div>
			<label for="txt-hp">핸드폰(hp) :</label> <input type="text" id="txt-hp"
				name="hp" value="<%=personVo.getHp()%>"  placeholder="핸드폰">
		</div>
		<div>
			<label for="txt-company">회사(company) :</label> <input type="text"
				id="txt-company" name="company" value="<%=personVo.getCompany()%>"  placeholder="회사">
		</div>
		<input type="hidden" name="action" value="update">
		<input type="hidden" name="no" value="<%=personVo.getPersonId()%>">
		
		<button type="submit">수정(전송)</button>

	</form>
	<br>
	<br>

	<a href="#">리스트로 가기</a>

</body>
</html>