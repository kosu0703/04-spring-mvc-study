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
			url : "dust_data.do",
			method : "post", 
			dataType : "xml",
			success : function(data) {
				console.log(data);
				let table = "<table>";
				table += "<thead>"
				table += "<tr><th>측정소명</th><th>미세먼지(PM10) 농도</th><th>초미세먼지(PM2.5) 농도</th><th>아황산가스 농도</th><th>일산화탄소 농도</th><th>오존 농도</th><th>측정일시</th>"
				table += "</tr></thead>";
				table += "<tbody>";
				
				$(data).find("item").each(function() {
					let stationName = $(this).find("stationName").text();
					let pm10Value = $(this).find("pm10Value").text();
					let pm25Value = $(this).find("pm25Value").text();
					let so2Value = $(this).find("so2Value").text();
					let coValue = $(this).find("coValue").text();
					let o3Value = $(this).find("o3Value").text();
					let dataTime = $(this).find("dataTime").text();
					table += "<tr>";
					table += "<td>" + stationName + "</td>";
					table += "<td>" + pm10Value + "</td>";
					table += "<td>" + pm25Value + "</td>";
					table += "<td>" + so2Value + "</td>";
					table += "<td>" + coValue + "</td>";
					table += "<td>" + o3Value + "</td>";
					table += "<td>" + dataTime + "</td>";
					table += "</tr>";
				});
				
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