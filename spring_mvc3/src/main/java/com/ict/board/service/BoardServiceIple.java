package com.ict.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.board.dao.BoardDAO;
import com.ict.board.dao.BoardVO;

@Service
public class BoardServiceIple implements BoardService{

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	public int getTotalCount() {
		return 0;
	}
	//	구버전
	@Override
	public List<BoardVO> getBoardList() {
		return boardDAO.getBoardList();
	}

	@Override
	public List<BoardVO> getBoardList(int offset, int limit) {
		return null;
	}

	@Override
	public int getBoardInsert(BoardVO bovo) {
		return boardDAO.getBoardInsert(bovo);
	}

	@Override
	public int getBoardHit(String bo_idx) {
		return 0;
	}

	@Override
	public BoardVO getBoardDetail(String bo_idx) {
		return null;
	}

	@Override
	public int getLevUpdate(Map<String, Integer> map) {
		return 0;
	}

	@Override
	public int getAnsInsert(BoardVO bovo) {
		return 0;
	}

	@Override
	public int getBoardDelete(BoardVO bovo) {
		return 0;
	}

	@Override
	public int getBoardUpdate(BoardVO bovo) {
		return 0;
	}
	
}
