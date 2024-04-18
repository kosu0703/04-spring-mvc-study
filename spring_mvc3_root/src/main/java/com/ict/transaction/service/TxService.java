package com.ict.transaction.service;

import com.ict.transaction.dao.TxVO;

public interface TxService {

	//	삽입
	public int getTxInsert(TxVO txvo) throws Exception;
		
}
