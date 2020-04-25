SELECT t.* FROM tag AS t,article_tag_ref AS tr WHERE tr.`article_id`=1 AND tr.`tag_id`=t.`id`

SELECT t.* FROM tag AS t,USER AS u WHERE t.`user_id`=u.`id` AND t.`user_id`=5

SELECT article_tag_ref.`tag_id`,COUNT(*) AS COUNT FROM article_tag_ref GROUP BY tag_id

SELECT t.*,COUNT(*) AS times FROM tag AS t,article_tag_ref AS tr WHERE tr.`tag_id`=t.`id` GROUP BY tr.`tag_id` ORDER BY times DESC LIMIT 0,13

SELECT t.*,COUNT(*) AS times FROM tag AS t,article_tag_ref AS tr,article AS a WHERE t.`id`=tr.`tag_id` AND tr.`article_id`=a.`id` AND a.`user_id`=5 GROUP BY t.`id` ORDER BY times DESC

SELECT COUNT(*) FROM tag

SELECT * FROM tag WHERE tag_name="高达"

SELECT c.* FROM category AS c,article_category_ref AS tc WHERE tc.`category_id`=c.`id` AND tc.`article_id`=1

SELECT c.* FROM category AS c,USER AS u WHERE c.`creator_id`=u.`id` AND u.`id`=1

SELECT c.*,COUNT(*) AS times FROM category AS c,article_category_ref AS tc,article AS a WHERE c.`id`=tc.`category_id` AND tc.`article_id`=a.`id` AND a.`user_id`=1 GROUP BY c.`id` ORDER BY times DESC

SELECT c.*,COUNT(*) AS times FROM category AS c,article_category_ref AS tc WHERE c.`id`=tc.`category_id` GROUP BY c.`id` ORDER BY times DESC

SELECT * FROM category WHERE create_time = "2020-02-11"

SELECT id,title,DATE_FORMAT(create_time,"%Y") AS YEAR FROM article ORDER BY YEAR DESC

SELECT id,COUNT(*) AS COUNT,DATE_FORMAT(create_time,"%Y-%m") AS create_time FROM article GROUP BY create_time ORDER BY create_time DESC

SELECT a.`id`,a.`user_id`,a.`title`,a.`view_count`,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,t.`tag_name`,t.`id` AS tag_id FROM article AS a,article_tag_ref AS tc,tag AS t WHERE a.`id`=tc.`article_id` AND tc.`tag_id`=t.`id` AND t.`id`=1 ORDER BY view_count DESC

SELECT a.`id`,a.`user_id`,a.`title`,a.`view_count`,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time FROM article AS a ORDER BY a.`view_count` DESC LIMIT 1

UPDATE article SET view_count=view_count+1 WHERE id=1


SELECT * FROM article;

SELECT FOUND_ROWS();

SELECT COUNT(*) AS COUNT FROM article AS a,article_tag_ref AS tr,tag AS t WHERE a.`id`=tr.`article_id` AND tr.`tag_id`=t.`id` AND t.`id`=1;

SELECT COUNT(*) AS COUNT FROM article AS a,article_category_ref AS tr,category AS c WHERE a.`id`=tr.`article_id` AND tr.`category_id`=c.`id` AND c.`id`=4;

SELECT COUNT(*) AS COUNT FROM article WHERE user_id=15;

SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id` ORDER BY create_time DESC;

SELECT c.*,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id` LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id` ORDER BY create_time DESC;

SELECT c.*,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c, USER AS u1,USER AS u2 WHERE c.`comment_user_id`=u1.`id` AND c.`comment_pid`=u2.`id` AND comment_article_id=1 LIMIT 10;

SELECT * FROM COMMENT WHERE comment_user_id=1 ORDER BY create_time DESC  LIMIT 0,1; 

SELECT * FROM COMMENT WHERE comment_pid=1;

SELECT * FROM COMMENT WHERE create_time="2020-02-18";

SELECT * FROM COMMENT WHERE comment_content LIKE "%论%" ORDER BY create_time DESC LIMIT 1,10 ;

SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c
LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id`
LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id`
  WHERE c.`comment_pid` IN(1,2,8,9) ORDER BY c.`create_time` DESC
  
 #用户的评论记录
SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c
LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id`
LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id`
WHERE c.`comment_user_id`=1 ORDER BY c.`create_time` DESC;

#用户收到的回复评论
SELECT c.* ,u1.`username` AS username,u2.`username` AS p_username FROM COMMENT AS c
LEFT JOIN USER AS u1 ON c.`comment_user_id`=u1.`id`
LEFT JOIN USER AS u2 ON c.`comment_pid`=u2.`id`
LEFT JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid`
WHERE cc.`comment_user_id`=1 ORDER BY c.`create_time` DESC;

SELECT COUNT(*) FROM COMMENT AS c
INNER JOIN COMMENT AS cc ON cc.`id`=c.`comment_pid` AND cc.`comment_user_id`=1


SELECT cc.* FROM COMMENT AS c RIGHT JOIN COMMENT AS cc ON cc.`comment_pid`=c.`id` WHERE c.`comment_user_id`=1;

SELECT c.* FROM COMMENT AS c LEFT JOIN COMMENT AS c2 ON c.`comment_pid`=c2.`id` WHERE c2.`comment_user_id`=1;	 

SELECT COUNT(*) FROM COMMENT WHERE comment_pid IN (1,2,8,9);

SELECT
        c.* ,
        u1.`username` AS username,
        u2.`username` AS p_username 
    FROM
        COMMENT AS c  
    LEFT JOIN
        USER AS u1 
            ON c.`comment_user_id`=u1.`id`  
    LEFT JOIN
        USER AS u2 
            ON c.`comment_pid`=u2.`id` WHERE c.`create_time` LIKE '%2020-02-19%' LIMIT 0,15;

SELECT t.* ,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content` AS comment_content FROM toast AS t LEFT JOIN USER AS u1 ON t.`toast_user_id`=u1.`id` LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` LEFT JOIN COMMENT AS c ON c.`id`=t.`toast_comment_id` WHERE t.`toast_type`=1 AND t.`toast_user_id`=1;

SELECT * FROM article WHERE id IN (3,5);

SELECT * FROM toast;

SELECT t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` FROM toast AS t LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` WHERE t.`is_read`=1 AND t.`toast_user_id`=1 ORDER BY t.`create_time` LIMIT 1000;

SELECT t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` FROM toast AS t LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` LEFT JOIN article AS a ON a.`id`=t.`toast_article_id` LEFT JOIN COMMENT AS c ON a.`id`=c.`comment_article_id` WHERE t.`is_read`=1 AND t.`toast_user_id`=1 AND t.`toast_comment_id`=c.`id` ORDER BY t.`create_time` LIMIT 1000;

SELECT t.*,u1.`username` AS username,u2.`username` AS sender_username,c.`comment_content`,a.`title` FROM toast AS t LEFT JOIN USER AS u1 ON u1.`id`=t.`toast_user_id` LEFT JOIN USER AS u2 ON u2.`id`=t.`toast_sender_id` LEFT JOIN COMMENT AS c ON t.`toast_comment_id`=c.`id` LEFT JOIN article AS a ON a.`id`=c.`comment_article_id` WHERE t.`is_read`=1;

SELECT COUNT(*) FROM toast WHERE is_read=1 AND toast_user_id=1;
UPDATE toast SET is_read=1 WHERE id IN (1,2,3,4);


SELECT
        t.*,
        u1.`username` AS username,
        u2.`username` AS sender_username,
        c.`comment_content`,
        a.`title` 
    FROM
        toast AS t 
    LEFT JOIN
        USER AS u1 
            ON u1.`id`=t.`toast_user_id` 
    LEFT JOIN
        USER AS u2 
            ON u2.`id`=t.`toast_sender_id` 
    LEFT JOIN
        COMMENT AS c 
            ON t.`toast_comment_id`=c.`id` 
    LEFT JOIN
        article AS a 
            ON a.`id`=c.`comment_article_id` 
    WHERE
        t.`is_read`=1 
        AND t.`toast_user_id`=1 
    ORDER BY
        t.`create_time` LIMIT 0,
        30
        
        
        SELECT
        t.*,
        u1.`username` AS username,
        u2.`username` AS sender_username,
        c.`comment_content`,
        a.`title` 
    FROM
        toast AS t 
    LEFT JOIN
        USER AS u1 
            ON u1.`id`=t.`toast_user_id` 
    LEFT JOIN
        USER AS u2 
            ON u2.`id`=t.`toast_sender_id` 
    LEFT JOIN
        COMMENT AS c 
            ON t.`toast_comment_id`=c.`id` 
    LEFT JOIN
        article AS a 
            ON a.`id`=c.`comment_article_id` 
    WHERE
        t.`is_read`=1 
        AND t.`toast_user_id`=5
    ORDER BY
        t.`create_time` DESC LIMIT 1,
        30

SELECT * FROM COMMENT WHERE comment_pid=1

SELECT * FROM COMMENT WHERE comment_user_id=1

SELECT * FROM COMMENT WHERE comment_pid IN (1,2,8,9);

#获取用户的点赞记录
SELECT l.*,a.`is_article` AS article_type,a.`content`,a.`title`,u.`username` FROM likes AS l LEFT JOIN article AS a ON a.`id`=l.`likes_article_id` LEFT JOIN USER AS u ON u.`id`=l.`likes_user_id` WHERE l.`status`=1 AND l.`likes_user_id`=1 ORDER BY l.`update_time` DESC LIMIT 0,100;

#获取文章的点赞记录
SELECT l.*,a.`is_article` AS article_type,a.`content`,a.`title`,u.`username` FROM likes AS l LEFT JOIN article AS a ON a.`id`=l.`likes_article_id` LEFT JOIN USER AS u ON u.`id`=l.`likes_user_id` WHERE l.`status`=1 AND l.`likes_article_id`=1 ORDER BY l.`update_time` DESC LIMIT 0,100;

#查看用户对某文章的点赞记录
SELECT * FROM Likes WHERE likes_article_id=1 AND likes_user_id=1;

#获取文章及其点赞数
SELECT *,COUNT(SELECT * FROM likes WHERE likes.`likes_article_id`=1) FROM article WHERE id=1;

#获取用户对文章的点赞记录
SELECT * FROM likes WHERE likes_user_id=1 AND likes_article_id IN(1,2,5)

SELECT c.* FROM category AS c,article_category_ref AS tc WHERE tc.`category_id`=c.`id` AND tc.`article_id`=1

DELETE FROM article_category_ref WHERE category_id=3

SELECT COUNT(*) FROM article_category_ref WHERE category_id=1

SELECT t.* FROM tag AS t,article_tag_ref AS tr WHERE tr.`article_id`=2 AND tr.`tag_id`=t.`id`

DELETE FROM article_tag_ref WHERE article_id=1

SELECT COUNT(*) FROM article_tag_ref WHERE article_id=1

DELETE FROM article_tag_ref WHERE tag_id=1

#添加文章分类关系
INSERT INTO article_category_ref(article_id,category_id) VALUES(1,1)

#添加文章标签关系
INSERT INTO article_tag_ref(article_id,tag_id) VALUES(1,1);

#批量添加文章标签关系
INSERT INTO article_tag_ref(article_id,tag_id) VALUES(1,3),(1,6)

SELECT a.`id`,a.`title`,a.`user_id`,a.`create_time`,a.`view_count`,COUNT(c.`id`) AS COUNT FROM article AS a LEFT JOIN COMMENT AS c ON a.`id`=c.`comment_article_id`  GROUP BY c.`comment_article_id` ORDER BY COUNT DESC;

#按评论数获取文章
SELECT a.`id`,a.`title`,a.`view_count`,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,u.`id`,u.`username`,COUNT(c.`id`) AS comment_count FROM article AS a LEFT JOIN USER AS u ON u.`id`=a.`user_id` LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` GROUP BY a.`id` ORDER BY comment_count DESC;

#获取文章列表(包括评论数、点赞数、浏览量) 评论数排序
SELECT  a.`id`,a.`title`,a.`view_count`
,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,a.`user_id`,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
GROUP BY a.`id` 
ORDER BY comment_count DESC;

#获取文章详细信息
SELECT  a.* ,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
WHERE a.`id`=1
GROUP BY a.`id` 

#获取文章列表(包括评论数、点赞数、浏览量) 点赞数排序
SELECT  a.`id`,a.`title`,a.`view_count`
,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,a.`user_id`,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
GROUP BY a.`id` 
ORDER BY likes_count DESC;

#获取文章列表(包括评论数、点赞数、浏览量) 浏览量排序
SELECT  a.`id`,a.`title`,a.`view_count`
,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,a.`user_id`,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
GROUP BY a.`id` 
ORDER BY a.`view_count` DESC;

#获取文章列表(所有)
SELECT  a.`id`,a.`title`,a.`view_count`
,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,a.`user_id`,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
GROUP BY a.`id` 
ORDER BY a.`create_time` DESC;

#获取有某标签的文章列表
SELECT a.`id`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`
,u.`username`,t.`id` AS tag_id,t.`tag_name`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count
FROM article AS a 
LEFT JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id`
LEFT JOIN tag AS t ON t.`id`=tr.`tag_id`
LEFT JOIN USER AS u ON u.`id`=a.`user_id`
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
WHERE t.`id`=7
GROUP BY a.`id`
ORDER BY comment_count DESC
LIMIT 100

#获取有某标签的文章的数量
SELECT COUNT(*) FROM article AS a INNER JOIN article_tag_ref AS tr ON tr.`article_id`=a.`id` INNER JOIN USER AS u ON u.`id`=a.`user_id` WHERE tr.`tag_id`=1 AND u.`id`=5

#获取有某分类的文章列表
SELECT a.`id`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`
,u.`username`,cat.`id` AS category_id,cat.`category_name`
,DATE_FORMAT(a.`create_time`,'%Y-%m-%d') AS create_time
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count
FROM article AS a 
LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id`
LEFT JOIN category AS cat ON cat.`id`=tc.`category_id`
LEFT JOIN USER AS u ON u.`id`=a.`user_id`
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
WHERE cat.`id`=1
GROUP BY a.`id`
ORDER BY likes_count DESC
LIMIT 0,20

#获取用户有某分类的文章列表
SELECT a.`id`,a.`title`,a.`summary`,a.`view_count`,a.`user_id`
,u.`username`,cat.`id` AS category_id,cat.`category_name`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count
FROM article AS a 
LEFT JOIN article_category_ref AS tc ON tc.`article_id`=a.`id`
LEFT JOIN category AS cat ON cat.`id`=tc.`category_id`
LEFT JOIN USER AS u ON u.`id`=a.`user_id`
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id`
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
WHERE cat.`id`=6 AND a.`user_id`=5
GROUP BY a.`id`
ORDER BY likes_count DESC
LIMIT 0,20

#获取有某分类的文章的数量
SELECT COUNT(*) FROM article AS a INNER JOIN article_category_ref AS tc ON a.`id`=tc.`article_id` INNER JOIN USER AS u ON u.`id`=a.`user_id` WHERE tc.`category_id`=6

#文章按年月归档
SELECT COUNT(*) AS COUNT,DATE_FORMAT(create_time,'%Y-%m') AS DATE FROM article GROUP BY DATE ORDER BY DATE DESC

#用户文章按年月归档
SELECT COUNT(*) AS COUNT,DATE_FORMAT(create_time,'%Y-%m') AS DATE FROM article WHERE user_id=1 GROUP BY DATE ORDER BY DATE DESC

#文章按年归档
SELECT COUNT(*) AS COUNT,DATE_FORMAT(create_time,'%Y') AS DATE FROM article GROUP BY DATE ORDER BY DATE DESC

#用户文章按年归档
SELECT COUNT(*) AS COUNT ,DATE_FORMAT(create_time,'%Y') AS DATE  FROM article WHERE user_id=1 GROUP BY DATE ORDER BY DATE DESC

#获取所有文章的阅读总量
SELECT SUM(view_count) AS SUM FROM Article ;

#获取用户的收藏列表
SELECT c.*,u.`username` AS username,a.`title`,a.`summary`,a.`view_count`
FROM collect AS c 
INNER JOIN article AS a ON a.`id`=c.`collect_article_id`
INNER JOIN USER AS u ON u.`id`=a.`user_id`
WHERE c.`collect_user_id`=5
ORDER BY c.`create_time` DESC
LIMIT 0,1

#获取收藏总数
SELECT COUNT(*) FROM collect 

#获取用户收藏总数
SELECT COUNT(*) FROM collect WHERE collect_user_id=5

#添加用户浏览历史
INSERT INTO history(history_article_id,history_user_id,create_time,access_time) VALUES(2,5,DATE_FORMAT(NOW(),'%y-%m-%d'),NOW()) ON DUPLICATE KEY UPDATE access_time=NOW();
;
#获取用户浏览历史列表
SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,"%Y-%m-%d") AS create_time
,a.`title`,u.`username`
FROM history AS h
INNER JOIN article AS a ON a.`id`=h.`history_article_id`
INNER JOIN USER AS u ON u.`id`=a.`user_id`
WHERE h.`history_user_id`=5
ORDER BY h.`create_time` DESC
LIMIT 1000;

#获取文章的浏览用户
SELECT h.`id`,h.`history_article_id`,h.`history_user_id`,DATE_FORMAT(h.`create_time`,"%Y-%m-%d") AS create_time
,a.`title`,u.`username`
FROM history AS h
LEFT JOIN article AS a ON a.`id`=h.`history_article_id`
LEFT JOIN USER AS u ON u.`id`=h.`history_user_id`
WHERE h.`history_article_id`=1
ORDER BY h.`create_time` DESC
LIMIT 100

#获取用户浏览记录数量
SELECT COUNT(*) FROM history WHERE history_user_id=5

#获取文章的浏览数
SELECT COUNT(*) FROM history WHERE history_article_id=12

#获取所有文章的总浏览数
SELECT COUNT(*) AS SUM FROM history ;

#用户添加关注
INSERT INTO follow(follow_user_id,follow_pid)VALUES(5,6)
;
#获取用户关注列表
SELECT f.*,u.`username` AS p_username,u.`image` AS p_image,u.`profile`
FROM follow AS f
INNER JOIN USER AS u ON u.`id`=f.`follow_pid`
WHERE f.`follow_user_id`=5
ORDER BY f.`id` DESC
LIMIT 1000;

#获取用户的关注人数
SELECT COUNT(*) FROM follow WHERE follow_user_id = 1

#获取用户的关注列表
SELECT f.* FROM follow AS f WHERE f.`follow_user_id`=5 ORDER BY f.`id` DESC
;


#获取用户关注的对象的文章
SELECT  a.`id`,a.`title`,a.`view_count`,a.`summary`
,DATE_FORMAT(a.`create_time`,"%Y-%m-%d") AS create_time,a.`user_id`,u.`username`
,COUNT(DISTINCT c.`id`) AS comment_count,COUNT(DISTINCT l.`id`) AS likes_count 
FROM article AS a 
LEFT JOIN USER AS u ON u.`id`=a.`user_id` 
LEFT JOIN COMMENT AS c ON c.`comment_article_id`=a.`id` 
LEFT JOIN likes AS l ON l.`likes_article_id`=a.`id` AND l.`status`=1
WHERE a.`user_id` IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=5)
GROUP BY a.`id` 
ORDER BY a.`create_time` DESC;

#获取用户关注的对象的文章总数
SELECT COUNT(*) FROM article WHERE user_id IN (SELECT follow.`follow_pid` FROM follow WHERE follow_user_id=1)
;

#获取被关注列表
SELECT f.`id`,f.`follow_pid`,f.`follow_user_id`,u.`image` AS p_image,u.`username` AS p_username,u.`profile`
FROM follow AS f
INNER JOIN USER AS u ON u.`id`=f.`follow_user_id`
WHERE f.`follow_pid`=6
ORDER BY f.`id` DESC
LIMIT 1000;

#用户被关注数
SELECT COUNT(*) FROM follow WHERE follow_pid = 5

#获取用户列表
SELECT id,username,user_mail,image,register_time,PROFILE,gender FROM USER ORDER BY register_time DESC LIMIT 0,100

#按发表文章数获取用户列表
;
SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`username`,u.`user_mail`,COUNT(a.`id`) AS COUNT
FROM USER AS u
LEFT JOIN article AS a ON a.`user_id`=u.`id`
GROUP BY u.`id`
ORDER BY COUNT DESC
LIMIT 1000;

#按发表评论数获取用户列表
SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`username`,u.`user_mail`,COUNT(c.`id`) AS COUNT
FROM USER AS u
LEFT JOIN COMMENT AS c ON c.`comment_user_id`=u.`id`
GROUP BY u.`id`
ORDER BY COUNT DESC
LIMIT 1000;

#按阅读量获取用户列表
SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`username`,u.`user_mail`,COUNT(h.`id`) AS COUNT
FROM USER AS u
LEFT JOIN history AS h ON h.`history_user_id`=u.`id`
GROUP BY u.`id`
ORDER BY COUNT DESC
LIMIT 1000;

#按收藏数获取用户列表
SELECT u.`id`,u.`image`,u.`gender`,u.`profile`,u.`register_time`,u.`username`,u.`user_mail`,COUNT(c.`id`) AS COUNT
FROM USER AS u
LEFT JOIN collect AS c ON c.`collect_user_id`=u.`id`
GROUP BY u.`id`
ORDER BY COUNT DESC
LIMIT 1000;

#获取用户总数
SELECT COUNT(id) FROM USER;

#获取用户的被点赞总数
SELECT COUNT(*)
FROM likes AS l
INNER JOIN article AS a ON a.`id`=l.`likes_article_id`
INNER JOIN USER AS u ON u.`id`=a.`user_id` 
WHERE u.`id`=5

#搜索文章
SELECT id,user_id,title,view_count,create_time,last_edit_time,is_article,readOnly,summary 
FROM article 
WHERE title LIKE CONCAT("%","搜索","%") 
OR summary LIKE CONCAT("%","搜索","%") 
OR title LIKE CONCAT("%","答案","%") 
OR summary LIKE CONCAT("%","答案","%") 
ORDER BY create_time DESC

#搜索文章数量
SELECT COUNT(id) FROM article WHERE 
title LIKE CONCAT('%',"test",'%')
OR summary LIKE CONCAT('%',"test",'%');

#获取用户的私信列表
SELECT *
FROM message 
WHERE sender_id=1 OR receiver_id=1
GROUP BY sender_id,receiver_id
ORDER BY create_time DESC;

SELECT receiver_id AS friend_id,u.`username`,u.`image` FROM message AS m
LEFT JOIN USER AS u ON u.`id`=m.`receiver_id`
WHERE m.`sender_id`=1 GROUP BY receiver_id
UNION (SELECT sender_id,u.`username`,u.`image` FROM message AS m
LEFT JOIN USER AS u ON u.`id`=m.`sender_id`
WHERE m.`receiver_id`=1 GROUP BY sender_id) LIMIT 0,11;

SELECT MAX(id) AS id,sender_id AS friend_id,COUNT(id) AS msgCount,content 
FROM message WHERE receiver_id = 1 AND isRead=0 GROUP BY sender_id;

#SELECT MAX(create_time) as create_time,id,receiver_id,sender_id,content
#from
#message
#where 
#receiver_id =1 or sender_id=1
#GROUP BY concat(receiver_id,sender_id);

#获取用户私信列表
SELECT * FROM (
SELECT message.`id`, receiver_id,content,create_time,u.`username`,u.`image`,isRead,
CASE WHEN sender_id=5
 THEN "发送方"
     ELSE "接收方" END flag
FROM message 
LEFT JOIN USER AS u ON u.`id`=receiver_id
WHERE sender_id=5
UNION
SELECT message.`id`,sender_id,content,create_time,u.`username`,u.`image`,isRead,
CASE WHEN sender_id=5
 THEN "发送方"
     ELSE "接收方" END flag
FROM message 
LEFT JOIN USER AS u ON u.`id`=sender_id
WHERE receiver_id=5
ORDER BY  create_time DESC,id DESC )ta
GROUP BY receiver_id
ORDER BY create_time DESC
LIMIT 20;

UPDATE  article SET  view_count=view_count +1 WHERE id=1
###
SELECT MAX(*) FROM (
SELECT COUNT(*) FROM message WHERE sender_id=1
GROUP BY receiver_id
UNION
SELECT COUNT(*) FROM message WHERE receiver_id=1
GROUP BY receiver_id
)t
;

#获取用户与某人的私信信息
SELECT m.*,r_u.`username` AS receiver,r_u.`image` AS r_image,s_u.`image` AS s_image,s_u.`username` AS sender,
CASE WHEN receiver_id=5
THEN 1 ELSE 0 END flag
FROM message AS m
LEFT JOIN USER AS r_u ON r_u.`id`=m.`receiver_id`
LEFT JOIN USER AS s_u ON s_u.`id`=m.`sender_id`
WHERE (sender_id=6 AND receiver_id=5) OR (receiver_id=6 AND sender_id=5)
ORDER BY create_time;

SELECT m.*,u.`username`,u.`image`,u2.`image` AS m_image,
CASE WHEN receiver_id=5 THEN 0 ELSE 1 END flag
FROM message AS m 
LEFT JOIN USER AS u ON u.`id`=m.`sender_id`
LEFT JOIN USER AS u2 ON u2.`id`=m.`receiver_id`
WHERE m.`sender_id`=6 AND m.`receiver_id`=5
UNION
SELECT m.*,u.`username`,u.`image`,u2.`image` AS m_image,
CASE WHEN m.`sender_id`=5 THEN 1 ELSE 0 END flag
FROM message AS m
LEFT JOIN USER AS u ON u.`id`=m.`receiver_id`
LEFT JOIN USER AS u2 ON u2.`id`=m.`sender_id`
WHERE m.`sender_id`=5 AND m.`receiver_id`=6
ORDER BY create_time


#获取用户是否有未读私信
SELECT COUNT(*) FROM message WHERE receiver_id=1 AND isRead=0

#设置私信已读
UPDATE message SET isRead=1 WHERE receiver_id=5 AND sender_id=6

#添加文件信息
INSERT INTO media(file_name,file_path,file_type,article_id)VALUES("文件名","文件路径filepath",1,0);
INSERT INTO media(file_name,file_path,file_type,article_id)VALUES("文件名","文件路径filepath",2,0);
INSERT INTO media(file_name,file_path,file_type,article_id)VALUES("文件名","文件路径filepath",3,0);

#按文件类型查询
SELECT * FROM media WHERE file_type=2 ORDER BY create_time DESC;

#按文件名和类型查找
SELECT * FROM media WHERE file_name LIKE CONCAT("%","件","%") AND file_type=2

#按用户查找
SELECT media.*,user.`username` FROM media
LEFT JOIN USER ON user.`id`=media.`user_id`
WHERE user_id=1

#按文章查找
SELECT * FROM media WHERE article_id=0

#获取文件数量
SELECT COUNT(*) FROM media WHERE file_type=1;

#获取用户上传文件的数量
SELECT COUNT(*) FROM media WHERE user_id=1

#获取文章的文件数
SELECT COUNT(*) FROM media WHERE article_id=0 AND file_type=2

#按文件名过去数量
SELECT COUNT(*) FROM media WHERE file_name LIKE CONCAT('%',"文",'%') AND file_type=2

#批量修改文章浏览数
UPDATE article SET view_count = view_count;

#获取用户发表文章数
SELECT u.`username`,COUNT(a.`id`) AS COUNT
FROM USER AS u
LEFT JOIN article AS a ON a.`user_id`=u.`id`
GROUP BY u.`id`
ORDER BY COUNT DESC
LIMIT 5;

#按类别获取文章数
SELECT COUNT(a.`id`) AS COUNT,a.`id` AS article_id,c.`category_name`,c.`id` AS category_id
FROM article AS a,category AS c,article_category_ref AS tc
WHERE a.`id`=tc.`article_id` AND tc.`category_id`=c.`id`
GROUP BY c.`id`
ORDER BY COUNT DESC

#用户注册时间
SELECT COUNT(id),register_time
FROM USER 
GROUP BY register_time 
ORDER BY register_time DESC
LIMIT 1

SELECT
        id,
        user_id,
        title,
        view_count,
        create_time,
        last_edit_time,
        is_article,
        readOnly,
        summary 
    FROM
        article 
    WHERE
        title LIKE CONCAT('%',"中文",'%') 
        OR summary LIKE CONCAT('%',"中文",'%') 
        AND is_article=1 
    ORDER BY
        create_time DESC LIMIT 0,
        100;
        
UPDATE
        category 
    SET
        is_Order=666 
    WHERE
        id=1;
        
#返回分类列表
SELECT c.*,COUNT(tc.`category_id`) AS times 
FROM category AS c
LEFT JOIN article_category_ref AS tc
ON tc.`category_id`=c.`id`
GROUP BY c.`id`
ORDER BY times DESC
LIMIT 0,100

#返回标签列表
SELECT t.*,COUNT(tr.`tag_id`) AS times 
FROM tag AS t
LEFT JOIN article_tag_ref AS tr
ON tr.`tag_id`=t.`id`
GROUP BY t.`id`
ORDER BY times DESC
LIMIT 0,100

SELECT
        c.* ,
        u1.`username` AS username,
        u2.`username` AS p_username,
        a.`is_article`  
    FROM
        COMMENT AS c  
    LEFT JOIN
        USER AS u1 
            ON c.`comment_user_id`=u1.`id`  
    LEFT JOIN
        COMMENT AS cc 
            ON cc.`id`=c.`comment_pid`  
    LEFT JOIN
        USER AS u2 
            ON u2.`id`=cc.`comment_user_id`  
    LEFT JOIN
        article AS a 
            ON c.`comment_article_id`=a.`id`  
    WHERE
        c.`comment_article_id`=2
    ORDER BY
        create_time DESC LIMIT 1000


#按月获取文章列表
SELECT * FROM article WHERE DATE_FORMAT( create_time, '%Y%m' ) = DATE_FORMAT( "2019-12-1" , '%Y%m' )
SELECT * FROM article WHERE CONVERT(VARCHAR(10),create_time,"%Y%m")="2019-12"

SELECT * FROM article WHERE DATE_FORMAT(create_time,'%Y-%m')="2019-12" ORDER BY create_time DESC
SELECT COUNT(*) FROM article WHERE is_article=1 AND DATE_FORMAT(create_time,'%Y-%m')="2019-12" AND user_id=1