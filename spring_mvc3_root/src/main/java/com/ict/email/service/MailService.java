package com.ict.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	//	컨트롤러에서 호출
	public void sendEmail(String randomNumber, String toMail) {
		try {
			MailHandler sendMail = new MailHandler(mailSender);
			//	메일 제목
			sendMail.setSubject("[ICT EDU 인증 메일입니다.");
			//	메일 내용
			sendMail.setText(""
                    + "<table><tbody>"
                    + "<tr><td><h2>ICT EDU 메일 인증</h2></td></tr>"
                    + "<tr><td><h3>ICT EDU</h3></td></tr>"
                    + "<tr><td><font size='20px'>인증번호 안내입니다.</font></td></tr>"
                    + "<tr><td></td></tr>"
                    + "<tr><td>안녕하세요 인증번호가 생성되었습니다.</td></tr>"
                    + "<tr><td></td></tr>"
                    + "<tr><td><font size='20px'>확인번호 : "+ randomNumber +"</font></td></tr>"
                    + "</tbody></table>");
			//	보내는 사람
			sendMail.setFrom("ictedu@gmail.com", "test");
			//	받는 사람
			sendMail.setTO(toMail);
			//	보내기
			sendMail.send();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
