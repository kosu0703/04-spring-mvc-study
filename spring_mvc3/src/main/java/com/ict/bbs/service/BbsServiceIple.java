package com.ict.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.bbs.dao.BbsDAO;
import com.ict.bbs.dao.BbsVO;
import com.ict.bbs.dao.CommentVO;

@Service
public class BbsServiceIple implements BbsService{

	@Autowired
	private BbsDAO bbsDAO;
	
	@Override
	public List<BbsVO> getBbsList() {
		return bbsDAO.getBbsList();
	}

	@Override
	public int getBbsInsert(BbsVO bvo) {
		return bbsDAO.getBbsInsert(bvo);
	}
	
	@Override
	public int getHitUpdate(String b_idx) {
		return bbsDAO.getHitUpdate(b_idx);
	}

	@Override
	public BbsVO getBbsDetail(String b_idx) {
		return bbsDAO.getBbsDetail(b_idx);
	}

	@Override
	public List<CommentVO> getCommList(String b_idx) {
		return bbsDAO.getCommList(b_idx);
	}

	@Override
	public int getCommInsert(CommentVO cvo) {
		return bbsDAO.getCommInsert(cvo);
	}

	@Override
	public int getCommDelete(String c_idx) {
		return bbsDAO.getCommDelete(c_idx);
	}
	
	@Override
	public int getBbsDelete(String b_idx) {
		return bbsDAO.getBbsDelete(b_idx);
	}

	@Override
	public int getTotal() {
		return bbsDAO.getTotal();
	}
	
	@Override
	public int getBbsUpdate(BbsVO bvo) {
		return bbsDAO.getBbsUpdate(bvo);
	}
	
}
