<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기</title>

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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js" 
	crossorigin="anonymous"></script>
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
	function list_go(f) {
		f.action = "bbs_list.do";
		f.submit();
	}
	function update_ok(f) {
		f.action = "bbs_update_ok.do";
		f.submit();
	}
</script>
</head>
<body>
	<div id="bbs">
		<form method="post" enctype="multipart/form-data">
			<table summary="수정하기">
				<caption>수정하기</caption>
				<tbody>
					<tr>
						<th>제목:</th>
						<td><input type="text" name="subject" size="12"
							value="${bvo.subject }"></td>
					</tr>
					<tr>
						<th>이름:</th>
						<td><input type="text" name="writer" size="12"
							value="${bvo.writer }"></td>
					</tr>
					<tr>
						<th>내용:</th>
						<td><textarea id="content" cols="50" rows="8" name="content">${bvo.content }</textarea></td>
					</tr>
					<tr>
						<th>첨부파일:</th>
						<c:choose>
							<c:when test="${empty bvo.f_name}">
								<td>
									<input type="file" name="file" /><b>이전파일이 없습니다.</b>
									<input type="hidden" name="f_name" value="${bvo.f_name}"> 
								</td>
							</c:when>
							<c:otherwise>
								<td>
									<input type="file" name="file" />
									<input type="hidden" name="f_name" value="${bvo.f_name}"> 
									<b>이전파일명 : ${bvo.f_name }</b>
								</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<th>비밀번호:</th>
						<td><input type="password" name="pwd" size="12" /></td>
					</tr>
					<tr>
						<td colspan="2"><input type="hidden" name="b_idx"
							value="${bvo.b_idx}"> <input type="hidden" name="cPage"
							value="${cPage}"> <input type="button" value="수정완료"
							onclick="update_ok(this.form)" /> <input type="button"
							value="목록" onclick="list_go(this.form)" /></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<br>
	<br>
	<br>
	<%-- 댓글 입력 부분 --%>
	<div style="padding: 50px; width: 580px; margin: 0 auto;">
		<form method="post">
			<fieldset>
				<p>
					이름 : <input type="text" name="writer">
				</p>
				<p>
					내용 :
					<textarea rows="4" cols="40" name="content"></textarea>
				</p>
				<%-- 어떤글의 댓글인지 알아야하기 때문에 원글의 b_idx 가 필요하다. --%>
				<%-- **찜이나 장바구니를 하는 것도 비슷하다(어떤상품의~) --%>
				<input type="hidden" name="b_idx" value="${bvo.b_idx}"> <input
					type="button" value="댓글저장" onclick="comm_write(this.form)" />
			</fieldset>
		</form>
	</div>
	<%-- 댓글 출력 부분 --%>
	<div style="display: table; margin: 0 auto;">
		<%-- 댓글을 여러개 가지고 있을 수 있다. --%>
		<c:forEach var="k" items="${comm_list}">
			<div
				style="border: 1px solid #cc00cc; width: 400px; margin: 20px; padding: 20px;">
				<form method="post">
					<p>이름 : ${k.writer}</p>
					<p>내용 : ${k.content}</p>
					<p>날짜 : ${k.write_date.substring(0,10)}</p>
					<input type="hidden" name="b_idx" value="${k.b_idx}"> <input
						type="hidden" name="c_idx" value="${k.c_idx}"> <input
						type="button" value="댓글삭제" onclick="comm_delete(this.form)" />
					<%-- 실제는 로그인 성공 && 글쓴이만 댓글을 삭제할 수 있어야 한다. --%>
				</form>
			</div>
		</c:forEach>
	</div>
	
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













