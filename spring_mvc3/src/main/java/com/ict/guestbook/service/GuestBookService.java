package com.ict.guestbook.service;

import java.util.List;

import com.ict.guestbook.dao.GuestBookVO;

public interface GuestBookService {
	
	//	전체보기
	public List<GuestBookVO> getGuestBookList();
	//	삽입
	public int getGuestBookInsert(GuestBookVO gvo);
	//	상세보기
	public GuestBookVO getGuestBookDetail(String idx);
	//	삭제
	public int getGuestBookDelete(String idx);
	//	수정
	public int getGuestBookUpdate(GuestBookVO gvo);
}
