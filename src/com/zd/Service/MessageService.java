package com.zd.Service;

import java.util.List;

import com.zd.Entity.Message;
import com.zd.VO.MessageList;
import com.zd.VO.MessageVO;

public interface MessageService {
	public Message insert(Message message);

	public List<MessageList> getMessageList(int user_id);

	public List<MessageVO> getMessage(int user_id, int p_id);

	public Integer isMessage(int user_id);

	public Integer setIsRead(int user_id, int p_id);
}
