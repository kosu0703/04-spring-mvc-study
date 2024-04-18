package com.ict.bbs.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<BbsVO> getBbsList() {
		try {
			return sqlSessionTemplate.selectList("bbs.bbslist");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getBbsInsert(BbsVO bvo) {
		try {
			return sqlSessionTemplate.insert("bbs.bbsinsert", bvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -10;
	}
	
	public int getHitUpdate(String b_idx) {
		try {
			return sqlSessionTemplate.update("bbs.bbshitupdate", b_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -10;
	}
	
	public BbsVO getBbsDetail(String b_idx) {
		try {
			return sqlSessionTemplate.selectOne("bbs.bbsdetail", b_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public List<CommentVO> getCommentList(String b_idx) {
		try {
			return sqlSessionTemplate.selectList("bbs.commlist", b_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getCommentInsert(CommentVO cvo) {
		try {
			return sqlSessionTemplate.insert("bbs.comminsert", cvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -10;
	}
	
	public int getCommentDelete(String c_idx) {
		try {
			return sqlSessionTemplate.delete("bbs.commdelete", c_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -10;
	}
	
	public int getTotalCount() {
		try {
			//	**검색결과가 하나면 selectOne
			return sqlSessionTemplate.selectOne("bbs.count");
		} catch (Exception e) {
			System.out.println(e);
		}
		return -10;
	}
	
	public List<BbsVO> getBbsList(int offset, int limit) {
		try {
			//	offset 과 limit 은 VO 에 없기때문에 map 을 만들어서 넘겨줘야한다.
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("offset", offset);
			map.put("limit", limit);
			//	파라미터 타입을 맵으로 주면 
			//	마이바티스는 맵에서 알아서 꺼내쓴다
			return sqlSessionTemplate.selectList("bbs.list", map);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public int getBbsDelete(String b_idx) {
		try {
			//return sqlSessionTemplate.delete("bbs.bbsdelete", b_idx);
			return sqlSessionTemplate.update("bbs.bbsdelete", b_idx);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	public int getBbsUpdate(BbsVO bvo) {
		try {
			return sqlSessionTemplate.update("bbs.bbsupdate", bvo);
		} catch (Exception e) {
			System.out.println(e);
		}
		return -1;
	}
	
	
	
}
