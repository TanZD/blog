package com.zd.DAO.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zd.DAO.MessageDAO;
import com.zd.Entity.Message;
import com.zd.VO.MessageList;
import com.zd.VO.MessageVO;

@Repository("MessageDAOImpl")
public class MessageDAOImpl extends BaseDAOImpl implements MessageDAO {

	@Override
	public Message insert(Message message) {
		getSession().save(message);
		return message;
	}

	@Override
	public List<MessageList> getMessageList(int user_id, int start, int limit) {
		String sql = "SELECT * FROM ( SELECT message.`id`, receiver_id,content,create_time,u.`username`,u.`image`,isRead, "
				+ " CASE WHEN sender_id=:u_id"
				+ " THEN '发送方' ELSE '接收方' END flag "
				+ " FROM message "
				+ " LEFT JOIN USER AS u ON u.`id`=receiver_id"
				+ " WHERE sender_id=:u_id"
				+ " UNION"
				+ " SELECT message.`id`,sender_id,content,create_time,u.`username`,u.`image`,isRead,"
				+ " CASE WHEN sender_id=:u_id"
				+ " THEN '发送方' ELSE '接收方' END flag"
				+ " FROM message "
				+ " LEFT JOIN USER AS u ON u.`id`=sender_id"
				+ " WHERE receiver_id=:u_id"
				+ " ORDER BY  create_time DESC,id DESC )ta"
				+ " GROUP BY receiver_id"
				+ " ORDER BY create_time DESC";
		return getSession().createSQLQuery(sql).setParameter("u_id", user_id).addEntity(MessageList.class).list();
	}

	@Override
	public List<MessageVO> getMessage(int user_id, int p_id) {
		String sql="SELECT m.*,r_u.`username` AS receiver,r_u.`image` AS r_image,s_u.`image` AS s_image,s_u.`username` AS sender,"
				+ " CASE WHEN receiver_id=:user_id"
				+ " THEN 1 ELSE 0 END flag"
				+ " FROM message AS m"
				+ " LEFT JOIN USER AS r_u ON r_u.`id`=m.`receiver_id`"
				+ " LEFT JOIN USER AS s_u ON s_u.`id`=m.`sender_id`"
				+ " WHERE (sender_id=:user_id AND receiver_id=:p_id) OR (receiver_id=:user_id AND sender_id=:p_id)"
				+ " ORDER BY create_time;";
		return getSession().createSQLQuery(sql).setParameter("user_id", user_id).setParameter("p_id", p_id)
				.addEntity(MessageVO.class).list();
	}

	@Override
	public Integer getMessageListCount(int user_id) {
		return null;
	}

	@Override
	public Integer isMessage(int user_id) {
		String sql = "SELECT COUNT(*) FROM message WHERE receiver_id=:u_id AND isRead=0";
		Object object = getSession().createSQLQuery(sql).setParameter("u_id", user_id).list().get(0);
		return Integer.valueOf(object.toString());
	}

	@Override
	public Integer setIsRead(int user_id, int p_id) {
		String sql = "UPDATE Message SET isRead=1 WHERE receiver_id=:u_id AND sender_id=:p_id";
		return getSession().createQuery(sql).setParameter("u_id", user_id).setParameter("p_id", p_id).executeUpdate();
	}

}
