package com.zd.Service;

import java.util.List;

import com.zd.DTO.JSONResult;
import com.zd.Entity.SensitiveWord;

public interface SensitiveWordService {
	public SensitiveWord insert(SensitiveWord word);

	public Integer delete(SensitiveWord word);

	public List<SensitiveWord> get();

	public JSONResult<SensitiveWord> get(int page, int limit);

	public SensitiveWord getByWord(String word);

	public Integer getCount();

	public void resetSensitiveWord();
}
