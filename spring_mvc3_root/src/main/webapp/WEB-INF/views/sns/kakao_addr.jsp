<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>다음 주소 API</title>
</head>
<body>

	<form action="kakao_addr_ok.do" method="post">
		<input type="text" id="postcode" name="postcode" placeholder="우편번호">
		<input type="button" onclick="execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="address" name="address" placeholder="주소">
		<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소"> <input
			type="text" id="extraAddress" name="extraAddress" placeholder="참고항목"> <input
			type="submit">
	</form>

	<!-- 주소찾아서 input 에 값을 넣어주고 DB 가기 -->
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript">
		function execDaumPostcode() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
					let addr = ''; // 주소 변수
					let extraAddr = ''; // 참고 항목 변수

					//	사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다
					if (data.userSelectedType === 'R') {
						//	도로명 주소
						addr = data.roadAddress;
					} else {
						//	지번 주소
						addr = data.jibunAddress;
					}

					//	사용자가 선택한 주소가 도로명 일때 참고 항목
					if (data.userSelectedType === 'R') {
						//	법정 '동' 이 있을 경우 추가한다. (법정 '리' 는 제외)
						//	법정 '동' 의 경우 마지막 문자가 "동/로/가" 로 끝난다.
						if (data.bname !== ''
								&& /[동|로|가]$/g.test(data.bname)) {
							extraAddr += data.bname;
						}

						//	건물명이 있고, 공동주택일 경우 추가한다.
						if (data.buildingName !== ''
								&& data.apartment === 'Y') {
							//	extraAddr 이 널이 아니면 콤마 , 를 붙여주고 추가 / 널이면 바로 추가
							extraAddr += (extraAddr !== '' ? ', '
									+ date.buildingName
									: date.buildingName);
						}

						//	표시할 참고항목이 있을 경우
						if (extraAddr !== '') {
							extraAddr = '(' + extraAddr + ')';
						}
						document.getElementById("extraAddress").value = extraAddr;
					} else {
						//	지번주소 일때는 참소 항목 안넣어준다.
						document.getElementById("extraAddress").value = '';
					}
							
					//	우편번호와 주소 정보를 해당 필드에 넣는다
					document.getElementById("postcode").value = data.zonecode;
					document.getElementById("address").value = addr;
							
					//	커서를 상세주소 필드로 이동
					document.getElementById("detailAddress").focus();
							
				}

			}).open();
		}
	</script>


</body>
</html>








