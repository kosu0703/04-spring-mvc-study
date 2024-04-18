<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 썸머노트 -->
<link rel="stylesheet" href="resources/css/summernote-lite.css">

<style type="text/css">
tr {
	text-align: center;
	padding: 4px 10px;
	background-color: #F6F6F6;
}

th {
	width: 120px;
	text-align: center;
	padding: 4px 10px;
	background-color: #B2CCFF;
}

/* 썸머노트 툴바 수정 */
.note-btn-group {
	width: auto;
}

.note-toolbar {
	width: auto;
}
</style>
<script type="text/javascript">
	//	유효성 검사
	function board_ans_write_ok(f) {
		/* 
		for (var i = 0; i < f.elements.length; i++) {
			if (f.elements[i].value == "") {
				//	첨부파일 일때는 검사 안함
				if (i == 3) continue;
				alert(f.elements[i].name + "를 입력하세요");
				f.elements[i].focus();
				return;//수행 중단
			}
		}
		 */
		f.action = "board_ans_write_ok.do";
		f.submit();
	}
	function board_list(f) {
		f.action = "board_list.do";
		f.submit();
	}
</script>
</head>
<body>

	<form method="post" enctype="multipart/form-data">
		<table style="margin: 0 auto; width: 900px;">
			<tbody>
				<tr>
					<th>작성자</th>
					<td align="left"><input type="text" name="writer"></td>
				</tr>
				<tr>
					<th>제목</th>
					<td align="left"><input type="text" name="title"></td>
				</tr>
				<tr>
					<th>내용</th>
					<td align="left"><textarea id="content" rows="10" cols="60"
							name="content"></textarea></td>
				</tr>
				<tr>
					<th>첨부파일</th>
					<td align="left"><input type="file" name="file"></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td align="left"><input type="password" name="pwd"></td>
				</tr>
				<tr>
					<td colspan="2">
					<input type="hidden" name="cPage" value="${cPage}"> 
						<input type="hidden" name="bo_idx" value="${bo_idx}"> 
						<input type="button" value="댓글입력" onclick="board_ans_write_ok(this.form)" />
						&nbsp;&nbsp;&nbsp;&nbsp; <input type="reset" value="지우기" />
						&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="목록"
						onclick="board_list(this.form)" /></td>
				</tr>
			</tbody>
		</table>
	</form>
	
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