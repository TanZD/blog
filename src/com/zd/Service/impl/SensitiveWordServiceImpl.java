package com.zd.Service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.DAO.SensitiveWordDAO;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.Entity.SensitiveWord;
import com.zd.Service.SensitiveWordService;
import com.zd.Util.Msg;
import com.zd.Util.TextFilter;

@Service("SensitiveWordServiceImpl")
@Transactional
public class SensitiveWordServiceImpl implements SensitiveWordService {
	@Autowired
	SensitiveWordDAO sensitiveWordDAO;

	@Override
	public SensitiveWord insert(SensitiveWord word) {
		SensitiveWord old = this.getByWord(word.getWord());
		if (old == null) {
			SensitiveWord s = sensitiveWordDAO.insert(word);
			if (s != null) {
				// 添加成功后要更新敏感词子树
				// List<String> newWord = new ArrayList<String>();
				// newWord.add(s.getWord());
				// TextFilter.addSensitiveWord(newWord);
				resetSensitiveWord();
			} else {
				return null;
			}
			return s;
		} else {
			return null;
		}
	}

	@Override
	public Integer delete(SensitiveWord word) {
		Integer result = sensitiveWordDAO.delete(word);
		if (result != 0) {
			resetSensitiveWord();
		}
		return result;
	}

	@Override
	public List<SensitiveWord> get() {
		return sensitiveWordDAO.get();
	}

	@Override
	public JSONResult<SensitiveWord> get(int page, int limit) {
		JSONResult<SensitiveWord> result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success);
		Integer totalNum = sensitiveWordDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<SensitiveWord> data = sensitiveWordDAO.get(pageInfo.getStartNum(), limit);
			result.setModels(data);
		}
		return result;
	}

	@Override
	public SensitiveWord getByWord(String word) {
		return sensitiveWordDAO.getByWord(word);
	}

	@Override
	public Integer getCount() {
		return sensitiveWordDAO.getCount();
	}

	@Override
	@Async
	public void resetSensitiveWord() {
		TextFilter.reset();
	}

}
