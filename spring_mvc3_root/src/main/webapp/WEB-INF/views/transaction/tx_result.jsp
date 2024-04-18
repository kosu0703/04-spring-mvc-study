<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>결과보기</h2>
	<c:choose>
		<%-- 처음1개 성공 1 + 다른1개 성공 1 = 2 --%>
		<c:when test="${res >= 2}">
			<h3>
				${txvo.customerId} 고객님, &nbsp;
				<span style="color: red;">${txvo.amount}</span> 자리 결제를 하셨습니다. &nbsp;
				<span style="color: red;">${txvo.countnum}</span> 개 티켓을 구매하셨습니다.
			</h3>
		</c:when>
		<c:otherwise>
			<h3>결제가 취소되었습니다.</h3>
		</c:otherwise>
	</c:choose>

</body>
</html>