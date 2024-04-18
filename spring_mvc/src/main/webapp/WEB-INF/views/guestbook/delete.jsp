<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제하기</title>
<style type="text/css">
	a { text-decoration: none;}
	table{width: 600px; border-collapse:collapse; text-align: center;}
	table,th,td{border: 1px solid black; padding: 3px}
	div{width: 600px; margin:auto; text-align: center;}
</style>
<script type="text/javascript">
	function delete_go(f) {
		const pwd = f.pwd.value;
		const pwd2 = ${gvo.pwd};
		if (pwd == pwd2) {
			const check = confirm("정말 삭제하시겠습니까?");
			if (check) {
				f.action = "delete_ok.do?idx=${gvo.idx}";
				f.submit();
			}else {
				history.go(-1);
			}
		}else {
			alert("비밀번호가 틀렸습니다.");
			f.pwd.value = "";
			f.pwd.focus();
			return;
		}
	}
</script>
</head>
<body>
<div>
<h2>방명록 삭제하기</h2>
<hr>
<p>[<a href="guestbook_list.do">목록으로 이동</a>]</p>
<form method="post">
	<table>
		<tr align="center">
			<td bgcolor="#99ccff">비밀번호</td>
			<td><input type="password" name="pwd" size="20"></td>
		</tr>
		<tfoot>
		<tr align="center">
			<td colspan="2">
				<input type="button" value="삭제" onclick="delete_go(this.form)">
			</td>
		</tr>
		</tfoot>
	</table>
</form>
</div>
</body>
</html>