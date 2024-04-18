package com.ict.board.service;

import java.util.List;
import java.util.Map;

import com.ict.board.dao.BoardVO;

public interface BoardService {
	
public int getTotalCount();
	//	리스트 구버전
	public List<BoardVO> getBoardList();
	//	리스트
	public List<BoardVO> getBoardList(int offset, int limit);
	//	삽입
	public int getBoardInsert(BoardVO bovo);
	//	조회수
	public int getBoardHit(String bo_idx);
	//	상세보기
	public BoardVO getBoardDetail(String bo_idx);
	//	레벨 증가
	public int getLevUpdate(Map<String, Integer> map);
	//	답글 삽입
	public int getAnsInsert(BoardVO bovo);
	//	원글 삭제
	public int getBoardDelete(BoardVO bovo);
	//	원글 수정
	public int getBoardUpdate(BoardVO bovo);
	
	
}
