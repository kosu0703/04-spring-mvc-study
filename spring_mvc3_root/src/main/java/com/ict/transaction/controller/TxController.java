package com.ict.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.transaction.dao.TxVO;
import com.ict.transaction.service.TxService;

@Controller
public class TxController {
	
	@Autowired
	private TxService txService;
	
	@GetMapping("transaction_go.do")
	public ModelAndView getTxForm() {
		return new ModelAndView("transaction/tx_form");
	}
	
	@PostMapping("transaction_go_ok.do")
	public ModelAndView getTxOK(@ModelAttribute("txvo")TxVO txvo) {
		try {
			ModelAndView mv = new ModelAndView("transaction/tx_result");
			//	트랜잭션 처리 이전
			//	카드는 성공이 됐는데, 티켓은 실패가 됐다.
			//	이럴때 트랜잭션 처리를 해줘야 한다.
			int res = txService.getTxInsert(txvo);
			mv.addObject("res", res);
			return mv;
		} catch (Exception e) {
			System.out.println(e);
		}
		return new ModelAndView("transaction/error");
	}
	
}








