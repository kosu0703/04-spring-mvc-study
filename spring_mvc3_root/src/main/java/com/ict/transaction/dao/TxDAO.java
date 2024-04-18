package com.ict.transaction.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class TxDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	//	루트 컨텍스트에서 bean 으로 생성한거 가져다 사용
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	public int getTxInsert(TxVO txvo) throws Exception {
		/*
		//	트랜잭션 처리 이전
		int result = 0;
		result += sqlSessionTemplate.insert("tx.card_insert", txvo);
		result += sqlSessionTemplate.insert("tx.ticket_insert", txvo);
		return result;
		*/
		
		//	트랜잭션 처리
		int result = 0;
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			result += sqlSessionTemplate.insert("tx.card_insert", txvo);
			result += sqlSessionTemplate.insert("tx.ticket_insert", txvo);
			transactionManager.commit(status);
			System.out.println("결제성공, 발권성공");
			return result;
		} catch (Exception e) {
			transactionManager.rollback(status);
			System.out.println("결제취소, 발권취소");
		}
		return -1;
	}
	
	
}







