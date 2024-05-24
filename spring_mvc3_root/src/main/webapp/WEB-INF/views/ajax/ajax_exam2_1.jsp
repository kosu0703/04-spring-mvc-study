<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    span { width: 150px; color: red;}
    input{border:1px solid red;}
    table{width: 80%; margin: 0 auto;}
    table,th,td {
    	border: 1px solid gray; 
    	text-align: center;
    	border-collapse: collapse;
    }
    h2{text-align: center;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		function getList2() {
			$.ajax({
				url : "getAjaxList2.do",
				method : "post", // 다른방식 => type : "post"
				dataType : "json",
				success : function(data) {
					console.log(data)
					let tbody = "";
					
					$.each(data, function(k, v) {
						//console.log(k);
						//console.log(v);
						tbody += "<tr>";
						tbody += "<td>" + v.m_idx + "</td>";
						tbody += "<td>" + v.m_id + "</td>";
						tbody += "<td>" + v.m_pw + "</td>";
						tbody += "<td>" + v.m_name + "</td>";
						tbody += "<td>" + v.m_age + "</td>";
						tbody += "<td>" + v.m_reg + "</td>";
						
						//	삭제하기 위해서 idx 를 가져가야한다.
						//	button 은 누르면 submit 되서 새로고침되기때문에
						//	input 타입으로 바꾸자
						tbody += "<td><input type='button' value='삭제' id='del' name='" 
									+ v.m_idx + "'></td>";
						tbody += "</tr>";
					})
					
					$("#tbody").append(tbody);
				},
				error : function() {
					alert("읽기실패");
				}
			});	
		}
		
		//	아이디를 눌렀다 땠을때, 아이디가 넘어가서 DB 에 있는지 없는지 검사
		//	날라갈때 아이디를 가져가서, 검사는 자바에서 하고, 결과만 가져온다.
		//	ajax 에서 파라미터값이 날라간다.
		$("#m_id").keyup(function() {
			$.ajax({
				url : "getAjaxIdChk.do" ,			// 	서버주소
				data : "m_id=" + $("#m_id").val() ,	//	서버에 보낼때 같이 가는 데이터 (파라미터)
				method : "post" ,					//	전달방식
				dataType : "text" ,					//	가져오는 결과 타입
				success : function(data) {
					let m_id = $("#m_id").val();
					//console.log(m_id);
					
					if(data == '1' && m_id !=''){
						//	중복된 아이디가 없으면서 아이디 입력창이 안비었으면
						//	사용가능
						$("#join_btn").removeAttr("disabled");
						$("span").text("사용가능");
					}else if (data == '0') {
						//	중복된 아이디가 있음
						//	사용불가
						$("#join_btn").attr("disabled", "disabled");
						$("span").text("사용불가");
					}else if (m_id == ''){
						//	아이디 입력창이 비어있으면
						$("#join_btn").attr("disabled", "disabled");
						$("span").text("중복여부");
					}
				},
				error : function() {
					alert("읽기 실패");
				}
			});	
		});
		
		//	data 가 여러개 일때는 직렬화를 사용한다.
		//	serialize() => 직렬화, form 태그 안에 있는 요소들만 받음
		$("#join_btn").click(function() {
			let param = $("#myform").serialize();
			$.ajax({
				url : "getAjaxJoin.do",
				data : param,
				method : "post",
				dataType : "text",
				success : function(data) {
					if (data == 0) {
						alert("가입실패");
					}else if (data == 1) {
						$(".reset").val("");
						$("#join_btn").attr("disabled", "disabled");
						$("span").text("중복여부");
						//	가입성공
						$("#tbody").empty();
						//	인풋타입일 때는 다시 리스트를 불러줘야한다.
						getList2();
					}
				},
				error : function() {
					alert("가입실패");
				}
			});
		});
		
		//	삭제버튼을 누르면 삭제
		//	선택자 selector 를 넣어주자
		//	테이블 안에 있는 아이디가 del 인놈을 클릭하면
		$("table").on("click", "#del", function() {
			$.ajax({
				url : "getAjaxDelete.do" ,					// 	서버주소
				data : "m_idx=" + $(this).attr("name") ,	//	서버에 보낼때 가는 파라미터
				method : "post" ,							//	전달방식
				dataType : "text" ,							//	가져오는 결과 타입
				success : function(data) {
					if (data == 0) {
						//	경고창도 새로고침 되기때문에 안쓰는게 좋긴하다. 
						alert("삭제실패");
					}else if (data == 1) {
						//	삭제성공
						$("#tbody").empty();
						//	인풋타입일 때는 다시 리스트를 불러줘야한다.
						getList2();
					}
				}
			});	
		});
		
		//	다 읽고나서 function 실행
		getList2();
	});
</script>
</head>
<body>
	
	<h2>Ajax 를 이용한 DB 처리</h2>
	<hr>
	<h2> 회원 정보 입력하기 </h2>
    <form name="myform" id="myform" autocomplete="off">
        <table>
            <thead>
                <tr>
                    <th>아이디</th><th>패스워드</th><th>이름</th><th>나이</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                    	<%-- id 키를 누르면 --%> 
                        <input type="text" size="14" name="m_id" id="m_id" class="reset">
                        <br><span>중복여부</span>
                    </td>
                    <td><input type="password" size="8" name="m_pw" class="reset"></td>  
                    <td><input type="text" size="14" name="m_name" class="reset"></td>  
                    <td><input type="number" size="12" name="m_age" class="reset"></td>  
                </tr>
            </tbody>
            <tfoot>
                <tr><td colspan="7" align="center">
                	<%-- 인풋타입으로 바꾸자 --%>
                	<%-- button 은 submit 되기때문에 새로고침된다. --%>
                	<input type="button" value="가입하기" id="join_btn" disabled>
                </td></tr>
            </tfoot>
        </table>
    </form>
    <br /> <br /> <br />
    <h2> 회원 정보 보기 </h2>
    <div>
        <table id="list">
            <thead>
                <tr>
                    <th>번호</th><th>아이디</th><th>패스워드</th>
                    <th>이름</th><th>나이</th><th>가입날짜</th><th>삭제</th>
                </tr>
            </thead>
            <tbody id="tbody">
            	
            </tbody>
        </table>
    </div>
	
</body>
</html>