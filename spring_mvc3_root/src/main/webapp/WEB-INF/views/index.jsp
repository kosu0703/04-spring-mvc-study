<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>첫페이지</title>
<script type="text/javascript">
	function gb_list() {
		location.href = "gb_list.do";
	}
	function gb2_list() {
		location.href = "gb2_list.do";
	}
	function bbs_list() {
		location.href = "bbs_list.do";
	}
	function board_list() {
		location.href = "board_list.do";
	}
	function shop_list() {
		location.href = "shop_list.do";
	}
	function spring_ajax() {
		location.href = "spring_ajax.do";
	}
	function spring_ajax2() {
		location.href = "spring_ajax2.do";
	}
	function spring_ajax2_1() {
		location.href = "spring_ajax2_1.do";
	}
	function spring_sns() {
		location.href = "spring_sns.do";
	}
	function dynamic_query() {
		location.href = "dynamic_query.do";
	}
	function email_send() {
		location.href = "email_send.do";
	}
	function data_go() {
		location.href = "data_go.do";
	}
	function data_go2() {
		location.href = "data_go2.do";
	}
	function transaction_go() {
		location.href = "transaction_go.do";
	}
</script>
</head>
<body>
	<button onclick="gb_list()">방명록</button>
	<button onclick="gb2_list()">방명록2</button>
	<button onclick="bbs_list()">게시판</button>
	<button onclick="board_list()">게시판2</button>
	<button onclick="shop_list()">쇼핑몰</button>
	<button onclick="spring_ajax()">Spring-Ajax</button>
	<button onclick="spring_ajax2()">Spring-Ajax-xml</button>
	<button onclick="spring_ajax2_1()">Spring-Ajax-json</button>
	<button onclick="spring_sns()">Spring-SNS</button>
	<button onclick="dynamic_query()">동적쿼리</button>
	<button onclick="email_send()">이메일 보내기</button>
	<button onclick="data_go()">공공데이터 미세먼지(xml)</button>
	<button onclick="data_go2()">공공데이터 미세먼지(json)</button>
	<button onclick="transaction_go()">트랜잭션</button>

</body>
</html>










