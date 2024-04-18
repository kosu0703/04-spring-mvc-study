package com.ict.bbs.service;

import java.util.List;

import com.ict.bbs.dao.BbsVO;
import com.ict.bbs.dao.CommentVO;

public interface BbsService {
	
	//	리스트
	public List<BbsVO> getBbsList();
	//	삽입
	public int getBbsInsert(BbsVO bvo);
	//	상세보기
	public BbsVO getBbsDetail(String b_idx);
	//	조회수 증가
	public int getHitUpdate(String b_idx);
	//	댓글 가져오기
	public List<CommentVO> getCommList(String b_idx);
	//	댓글 작성
	public int getCommInsert(CommentVO cvo);
	//	댓글 삭제
	public int getCommDelete(String c_idx);
	
	//	페이지처리
	public int getTotal();
	
	//	삭제
	public int getBbsDelete(String b_idx);
	//	수정
	public int getBbsUpdate(BbsVO bvo);
}
