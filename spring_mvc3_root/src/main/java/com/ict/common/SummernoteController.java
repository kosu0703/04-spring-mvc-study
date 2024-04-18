package com.ict.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class SummernoteController {
	
	@RequestMapping(value = "saveImg.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(ImgVO imgVO, HttpSession session){
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			MultipartFile file = imgVO.getS_file();
			
			String f_name = "";
			
			//	파일의 크기가 0 보다 클때만 = 파일이 있을때만
			if (file.getSize() > 0) {
				String path = session.getServletContext().getRealPath("/resources/upload");
				UUID uuid = UUID.randomUUID();
				f_name = uuid + "_" + file.getOriginalFilename();
				
				//	파일 업로드
				file.transferTo(new File(path, f_name));
				
				//	비동기(ajax) 요청이므로 저장된 파일의 경로와 파일명을 보낸다.
				map.put("path", "resources/upload");
				map.put("f_name", f_name);
				
				return map;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}













