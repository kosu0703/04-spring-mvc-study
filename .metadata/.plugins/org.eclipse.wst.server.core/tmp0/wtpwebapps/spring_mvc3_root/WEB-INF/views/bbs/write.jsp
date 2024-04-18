<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>

<!-- 썸머노트 -->
<link rel="stylesheet" href="resources/css/summernote-lite.css">

<style type="text/css">
#bbs table {
	width: 900px;
	margin: 0 auto;
	margin-top: 20px;
	border: 1px solid black;
	border-collapse: collapse;
	font-size: 14px;
}

#bbs table caption {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 10px;
}

#bbs table th {
	text-align: center;
	border: 1px solid black;
	padding: 4px 10px;
}

#bbs table td {
	text-align: left;
	border: 1px solid black;
	padding: 4px 10px;
}

.no {
	width: 15%
}

.subject {
	width: 30%
}

.writer {
	width: 20%
}

.reg {
	width: 20%
}

.hit {
	width: 15%
}

.title {
	background: lightsteelblue
}

.odd {
	background: silver
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
	function list_go() {
		location.href = "bbs_list.do";
	}
	function save_go(f) {
		f.action = "bbs_write_ok.do";
		f.submit();
	}
</script>
</head>
<body>
	<div id="bbs">
		<form method="post" encType="multipart/form-data">
			<table summary="게시판 글쓰기">
				<caption>게시판 글쓰기</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td><input type="text" name="subject" size="12" /></td>
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" name="writer" size="12" /></td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea id="content" name="content" cols="50" rows="8"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<td><input type="file" name="file" /></td>
					</tr>
					<tr>
						<th>비밀번호:</th>
						<td><input type="password" name="pwd" size="12" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="button" value="보내기"
							onclick="save_go(this.form)" /> <input type="reset" value="다시" />
							<input type="button" value="목록" onclick="list_go()" /></td>
					</tr>
				</tbody>
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

