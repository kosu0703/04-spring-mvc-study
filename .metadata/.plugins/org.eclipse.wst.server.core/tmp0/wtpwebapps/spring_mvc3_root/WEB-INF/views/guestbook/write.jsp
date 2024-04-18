<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>방명록 쓰기</title>

<!-- 썸머노트 -->
<link rel="stylesheet" href="resources/css/summernote-lite.css">

<style type="text/css">
a {
	text-decoration: none;
}

table {
	width: 800px;
	border-collapse: collapse;
	text-align: center;
}

table, th, td {
	border: 1px solid black;
	padding: 3px
}

div {
	width: 800px;
	margin: auto;
	text-align: center;
}

/* 썸머노트 툴바 수정 */
.note-btn-group{
	width: auto;
}
.note-toolbar{
	width: auto;
}

</style>
</head>
<body>
	<div>
		<h2>방명록 : 작성화면</h2>
		<hr>
		<p>
			[<a href="gb_list.do">목록으로 이동</a>]
		</p>
		<form action="gb_write_ok.do" method="post">
			<table>
				<tr align="center">
					<td bgcolor="#99ccff">작성자</td>
					<td><input type="text" name="name" size="20" required></td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">제목</td>
					<td><input type="text" name="subject" size="20" required></td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">이메일</td>
					<td><input type="text" name="email" size="20" required></td>
				</tr>
				<tr align="center">
					<td bgcolor="#99ccff">비밀번호</td>
					<td><input type="text" name="pwd" size="20" required></td>
				</tr>
				<tr align="center">
					<td colspan="2"><textarea id="content" rows="10" cols="60" name="content"
							required></textarea></td>
				</tr>
				<tfoot>
					<tr align="center">
						<td colspan="2"><input type="submit" value="저장">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="취소">
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js" 
			crossorigin="anonymous"></script>
	<script src="resources/js/summernote-lite.js"></script>
	<%-- 한국어 처리 --%>
	<script src="resources/js/lang/summernote-ko-KR.js"></script>
	
	<script type="text/javascript">
		$(function() {
			$("#content").summernote({
				lang : 'ko-KR',
				height : 300 ,
				focus : true ,
				placeholder : '최대 3000자까지 쓸 수 있습니다.' ,
				callbacks : {
					onImageUpload : function(files, editor) {
						for (var i = 0; i < files.length; i++) {
							sendImage(files[i], editor);
						}
					}
				}
			});
		});
		
		function sendImage(file, editor) {
			let frm = new FormData();
			frm.append("s_file", file);
			
			$.ajax({
				url : "saveImg.do" ,
				data : frm ,
				contentType : false ,
		        processData : false ,
		        cache : false ,
				method : "post" , 		// 	이미지 업로드는 무조건 post 방식
				dataType : "json" 		//	Map 은 key : value 이므로 json
			}).done(function(data) {	//	성공했을때 (success 와 같음)	
				let path = data.path;
				let f_name = data.f_name;
				$("#content").summernote("editor.insertImage", path + "/" + f_name);
			});
		}
		
	</script>
	
</body>
</html>















