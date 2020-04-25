package com.zd.DAO;

import java.util.List;

import com.zd.Entity.Message;
import com.zd.VO.MessageList;
import com.zd.VO.MessageVO;

public interface MessageDAO {
	public Message insert(Message message);

	public List<MessageList> getMessageList(int user_id, int start, int limit);

	public List<MessageVO> getMessage(int user_id, int p_id);

	public Integer getMessageListCount(int user_id);

	public Integer isMessage(int user_id);

	public Integer setIsRead(int user_id, int p_id);
}
