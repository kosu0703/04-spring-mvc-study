<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 수정</title>
<style type="text/css">
	a { text-decoration: none;}
	table{width: 600px; border-collapse:collapse; text-align: center;}
	table,th,td{border: 1px solid black; padding: 3px}
	div{width: 600px; margin:auto; text-align: center;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		let pwdchk = "${pwdchk}";
		if (pwdchk == 'fail') {
			alert("비밀번호틀림");
			return;
		}
	})
</script>
<script type="text/javascript">
	function update_go(f) {
		f.action = "gb_update_ok.do";
		f.submit();
	}
</script>
</head>
<body>
<div>
<h2>방명록 : 수정화면</h2>
<hr>
<p>[<a href="gb_list.do">목록으로 이동</a>]</p>
<form method="post">
	<table>
		<tr align="center">
			<td bgcolor="#99ccff">작성자</td>
			<td><input type="text" name="name" size ="20" value="${vo.name}" required></td>
		</tr>
		<tr align="center">
			<td bgcolor="#99ccff">제목</td>
			<td><input type="text" name="subject" size ="20" value="${vo.subject}" required></td>
		</tr>
		<tr align="center">
			<td bgcolor="#99ccff">이메일</td>
			<td><input type="text" name="email" size ="20" value="${vo.email}" required></td>
		</tr>
		<tr align="center">
			<td bgcolor="#99ccff">비밀번호</td>
			<td><input type="password" name="pwd" size ="20" required></td>
		</tr>
		<tr align="center">
			<td colspan="2">
				<textarea rows="10" cols="60" name="content" required>${vo.content}</textarea>
			</td>
		</tr>
		<tfoot>
		<tr align="center">
			<td colspan="2">
				<input type="hidden" name="idx" value="${vo.idx}">
				<input type="button" value="수정완료" onclick="update_go(this.form)">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="reset" value="취소">
			</td>
		</tr>
		</tfoot>
	</table>
</form>
</div>	
</body>
</html>