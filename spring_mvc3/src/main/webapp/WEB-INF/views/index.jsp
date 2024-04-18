<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
</script>
</head>
<body>
	<button onclick="gb_list()">guestbook</button>
	<button onclick="gb2_list()">guestbook2</button>
	<button onclick="bbs_list()">게시판</button>
	<button onclick="board_list()">게시판2</button>
</body>
</html>