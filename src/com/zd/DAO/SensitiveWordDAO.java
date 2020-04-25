package com.zd.DAO;

import java.util.List;

import com.zd.Entity.SensitiveWord;

public interface SensitiveWordDAO {
	public SensitiveWord insert(SensitiveWord word);

	public Integer delete(SensitiveWord word);

	public Integer getCount();

	public List<SensitiveWord> get();

	public List<SensitiveWord> get(int start, int limit);

	public SensitiveWord getByWord(String word);
}
