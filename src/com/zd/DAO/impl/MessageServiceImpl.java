package com.zd.DAO.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.zd.DAO.MessageDAO;
import com.zd.Entity.Message;
import com.zd.Service.MessageService;
import com.zd.Util.MyUtil;
import com.zd.VO.MessageList;
import com.zd.VO.MessageVO;

@Service("MessageService")
@Transactional
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageDAO messageDAO;

	@Override
	public Message insert(Message message) {
		message.setCreate_time(MyUtil.NowTime());
		messageDAO.insert(message);
		if (message.getId() != 0) {
			return message;
		}
		return null;
	}

	@Override
	public List<MessageList> getMessageList(int user_id) {
		return messageDAO.getMessageList(user_id, 0, 0);
	}

	@Override
	public List<MessageVO> getMessage(int user_id, int p_id) {
		return messageDAO.getMessage(user_id, p_id);
	}

	@Override
	public Integer isMessage(int user_id) {
		return messageDAO.isMessage(user_id);
	}

	@Override
	@Async
	public Integer setIsRead(int user_id, int p_id) {
		return messageDAO.setIsRead(user_id, p_id);
	}

}
