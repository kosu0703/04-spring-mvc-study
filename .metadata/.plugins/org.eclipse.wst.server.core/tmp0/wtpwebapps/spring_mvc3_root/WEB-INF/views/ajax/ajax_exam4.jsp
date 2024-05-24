<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공공데이터-대기오염정보</title>
<style type="text/css">
	table, th, td {
		border: 1px solid red;
		border-collapse: collapse;
		text-align: center;
		margin: 0 auto;
		padding: 10px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#result").empty();
		$.ajax({
			url : "dust_data2.do",
			method : "post", 
			dataType : "json",
			success : function(data) {
				//console.log(data);
				let table = "<table>";
				table += "<thead>"
				table += "<tr><th>측정소명</th><th>미세먼지(PM10) 농도</th><th>초미세먼지(PM2.5) 농도</th><th>아황산가스 농도</th><th>일산화탄소 농도</th><th>오존 농도</th><th>측정일시</th>"
				table += "</tr></thead>";
				table += "<tbody>";
				$.each(data.response.body.items , function(k, v) {
					//console.log(v.stationName)
					table += "<tr>";
					table += "<td>" + v.stationName + "</td>";
					table += "<td>" + v.pm10Value + "</td>";
					table += "<td>" + v.pm25Value + "</td>";
					table += "<td>" + v.so2Value + "</td>";
					table += "<td>" + v.coValue + "</td>";
					table += "<td>" + v.o3Value + "</td>";
					table += "<td>" + v.dataTime + "</td>";
					table += "</tr>";
				})
				table += "</tbody>";
				table += "</table>";
				$("#result").append(table);
			},
			error : function() {
				alert("실패");
			}
		});
	});	
</script>
</head>
<body>
	
	<div id="result"></div>
	
</body>
</html>