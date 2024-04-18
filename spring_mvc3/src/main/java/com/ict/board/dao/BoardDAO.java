package com.ict.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int getTotalCount() {
		return 0;
	}
	//	구버전
	public List<BoardVO> getBoardList() {
		try {
			return sqlSessionTemplate.selectList("board.board_list");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public List<BoardVO> getBoardList(int offset, int limit) {
		return null;
	}

	public int getBoardInsert(BoardVO bovo) {
		try {
			return sqlSessionTemplate.insert("board.board_insert", bovo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}

	public int getBoardHit(String bo_idx) {
		return 0;
	}

	public BoardVO getBoardDetail(String bo_idx) {
		return null;
	}

	public int getLevUpdate(Map<String, Integer> map) {
		return 0;
	}

	public int getAnsInsert(BoardVO bovo) {
		return 0;
	}

	public int getBoardDelete(BoardVO bovo) {
		return 0;
	}

	public int getBoardUpdate(BoardVO bovo) {
		return 0;
	}
}
