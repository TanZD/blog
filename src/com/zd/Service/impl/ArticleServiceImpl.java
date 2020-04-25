package com.zd.Service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zd.DAO.ArticleCategoryRef;
import com.zd.DAO.ArticleDAO;
import com.zd.DAO.ArticleTagRef;
import com.zd.DAO.CategoryDAO;
import com.zd.DAO.MediaDAO;
import com.zd.DAO.TagDAO;
import com.zd.DTO.ArticleDTO;
import com.zd.VO.DateCount;
import com.zd.VO.UserSimple;
import com.zd.DTO.JSONResult;
import com.zd.DTO.PageInfo;
import com.zd.DTO.UserDTO;
import com.zd.Entity.Article;
import com.zd.Entity.Category;
import com.zd.Entity.Media;
import com.zd.Entity.Tag;
import com.zd.Service.ArticleService;
import com.zd.Service.MediaService;
import com.zd.Service.RedisService;
import com.zd.Service.UserService;
import com.zd.Util.Msg;
import com.zd.Util.MyUtil;
import com.zd.Util.TextFilter;
import com.zd.VO.ADetailVO;
import com.zd.VO.ArticleSimple;
import com.zd.VO.ArticleVO;

@Service("ArticleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	TagDAO tagDAO;

	@Autowired
	ArticleCategoryRef articleCategoryRef;

	@Autowired
	ArticleTagRef articleTagRef;

	@Autowired
	MediaDAO mediaDAO;

	@Autowired
	RedisService redisService;

	@Autowired
	MediaService mediaService;

	@Autowired
	UserService userService;

	@Override
	public Article insert(ArticleDTO<Article> article) {
		// 保存文章先保存文章，再保存文章的分类,标签
		// 先保存文章，获取文章id
		articleDAO.insert(article.getArticle());
		// 文章保存成功
		if (article.getArticle().getId() != 0) {
			if (article.getCategory() != null) {
				// 保存文章的分类
				if (article.getCategory().size() != 0) {
					for (int i = 0; i < article.getCategory().size(); i++) {
						articleCategoryRef.add(article.getCategory().get(i).getId(), article.getArticle().getId());
					}
				}
			}
			if (article.getTag() != null) {
				// 保存文章的标签
				if (article.getTag().size() != 0) {
					for (int i = 0; i < article.getTag().size(); i++) {
						articleTagRef.add(article.getArticle().getId(), article.getTag().get(i).getId());
					}
				}
			}
		}
		return article.getArticle();
	}

	@Override
	public Article update(ArticleDTO<Article> article) {
		articleDAO.update(article.getArticle());
		if (article.getCategory() != null) {
			// 先删除原来的分类
			articleCategoryRef.deleteByArticleId(article.getArticle().getId());
			// 再重新保存文章的分类
			if (article.getCategory().size() != 0) {
				for (int i = 0; i < article.getCategory().size(); i++) {
					articleCategoryRef.add(article.getCategory().get(i).getId(), article.getArticle().getId());
				}
			}
		}
		if (article.getTag() != null) {
			// 先删除原来的标签
			articleTagRef.deleteByArticleId(article.getArticle().getId());
			// 再重新保存标签
			if (article.getTag().size() != 0) {
				for (int i = 0; i < article.getTag().size(); i++) {
					articleTagRef.add(article.getArticle().getId(), article.getTag().get(i).getId());
				}
			}
		}
		return article.getArticle();
	}

	@Override
	public Integer delete(int article_id) {
		return articleDAO.delete(article_id);
	}

	@Override
	public List<ArticleVO> getAll() {
		return articleDAO.getAll();
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> get(int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCount();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.get(pageInfo.getStartNum(), limit, order);
			List<ArticleDTO<ArticleVO>> data = tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<ArticleVO> getByUserId(int user_id) {
		return articleDAO.getByUserId(user_id);
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByUserId(int user_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByUserId(user_id);
		List<ArticleVO> modules = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = null;
			// 如果page=-1就不分页
			if (page == -1) {
				modules = this.getByUserId(user_id);
				pageInfo = null;
			} else {
				pageInfo = new PageInfo(page, limit, totalNum);
				modules = articleDAO.getByUserId(user_id, pageInfo.getStartNum(), limit, order);
			}
			List<ArticleDTO<ArticleVO>> data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<ArticleVO> getByCategoryId(int category_id) {
		return articleDAO.getByCategoryId(category_id);
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByCategoryId(int category_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByCategoryId(category_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getByCategoryId(category_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByUserIdAndCategory(int user_id, int category_id, int page, int limit,
			int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByUserIdAndCategory(user_id, category_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getByUserAndCategory(user_id, category_id, pageInfo.getStartNum(),
					limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByUserIdAndTag(int user_id, int tag_id, int page, int limit,
			int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByUserIdAndTag(user_id, tag_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getByUserAndTag(user_id, tag_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public List<ArticleVO> getByTagId(int tag_id) {
		return articleDAO.getByTagId(tag_id);
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByTagId(int tag_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByTagId(tag_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getByTagId(tag_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getByFollow(int user_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.getCountByFollow(user_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getByFollow(user_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public Integer getTotalCount() {
		return articleDAO.getCount();
	}

	@Override
	public Integer getCountBySearch(String words) {
		String[] key_word = words.split(" ");
		return articleDAO.getCountBySearch(key_word);
	}

	@Override
	public JSONResult<ArticleSimple> search(String words, int page, int limit) {
		JSONResult<ArticleSimple> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		String[] key_word = words.split(" ");
		System.out.println(key_word.toString());
		Integer totalNum = articleDAO.getCountBySearch(key_word);
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleSimple> data = articleDAO.search(key_word, pageInfo.getStartNum(), limit);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public Integer updateReadable(int article_id, int status) {
		return articleDAO.updateReadable(article_id, status);
	}

	@Override
	public List<Article> getOrderByComments(int limit) {
		return null;
	}

	@Override
	public List<Article> getOrderByComments(int page, int limit) {
		return null;
	}

	@Override
	public ArticleDTO<ADetailVO> getDetailById(int article_id) {
		ADetailVO article = articleDAO.getDetailById(article_id);
		if (article != null) {
			// 获取redis中的点赞数
			Integer redis_likes = redisService.getLikeCount(article.getId());
			// 从redis中获取点击量
			Integer redis_views = redisService.getViewsCount(article.getId());

			ADetailVO b = new ADetailVO();
			b.setComment_count(article.getComment_count());
			b.setCreate_time(article.getCreate_time());
			b.setId(article.getId());
			b.setLikes_count(article.getLikes_count() + redis_likes);
			b.setUser_id(article.getUser_id());
			b.setUsername(article.getUsername());
			b.setView_count(article.getView_count() + redis_views);
			b.setLast_edit_time(article.getLast_edit_time());
			b.setIs_article(article.getIs_article());
			// 过滤敏感词
			b.setTitle(TextFilter.doFilter(article.getTitle()));
			b.setSummary(TextFilter.doFilter(article.getSummary()));
			b.setContent(TextFilter.doFilter(article.getContent()));
			// 获取标签
			List<Category> category = categoryDAO.findByArticleId(article_id);
			// 获取分类
			List<Tag> tag = tagDAO.findByArticleId(article_id);
			// 如果是动态则获取其图片等信息
			List<Media> media = mediaService.getByArticle(article_id);
			// 获取用户信息
			UserDTO user_message = userService.getByUser(article.getUser_id());

			ArticleDTO<ADetailVO> data = new ArticleDTO<ADetailVO>(b, user_message, category, tag, media);
			return data;
		} else {
			return null;
		}
	}

	@Override
	public Article getById(int article_id) {
		return articleDAO.getById(article_id);
	}

	@Override
	public List<DateCount> ListByYear() {
		return articleDAO.ListByYear();
	}

	@Override
	public List<DateCount> ListByYearAndMonth() {
		return articleDAO.ListByYearAndMonth();
	}

	@Override
	public List<DateCount> ListByYearFromUserId(int user_id) {
		return articleDAO.ListByYearFromUserId(user_id);
	}

	@Override
	public List<DateCount> ListByYearAndMonthFromUserId(int user_id) {
		return articleDAO.ListByYearAndMonthFromUserId(user_id);
	}

	@Override
	public Integer getTotalViews() {
		return articleDAO.getTotalViews();
	}

	@Override
	public Article savePost(HttpServletRequest request, Article article, List<Tag> tagList, MultipartFile[] file,
			String name, int post_type, int pid) {
		articleDAO.insert(article);
		if (article.getId() != 0) {
			// 保存动态的标签
			if (tagList.size() != 0) {
				for (int i = 0; i < tagList.size(); i++) {
					articleTagRef.add(article.getId(), tagList.get(i).getId());
				}
			}
			if (post_type == -1) {
				Media p = mediaDAO.getById(pid);
				Media media = new Media();
				media.setFile_type(-1);
				media.setPid(pid);
				media.setFile_type(-1);
				media.setFile_path(p.getFile_path());
				mediaDAO.insert(media);
			} else {
				// 保存文件
				List<String> file_path = MyUtil.saveFile(request, file, post_type);
				for (String p : file_path) {
					Media media = new Media();
					media.setUser_id(article.getUser_id());
					media.setArticle_id(article.getId());
					media.setCreate_time(MyUtil.NowTime());
					media.setFile_type(post_type);
					media.setFile_path(p);
					media.setFile_name(name);
					mediaDAO.insert(media);
				}
			}
		}
		return article;
	}

	@Override
	public List<ArticleDTO<ArticleVO>> tranDTO(List<ArticleVO> modules) {
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		for (int i = 0; i < modules.size(); i++) {
			ArticleVO a = modules.get(i);
			// 获取redis中的点赞数
			Integer redis_likes = redisService.getLikeCount(a.getId());
			// 从redis中获取点击量
			Integer redis_views = redisService.getViewsCount(a.getId());
			ArticleVO b = new ArticleVO();
			b.setComment_count(a.getComment_count());
			b.setCreate_time(a.getCreate_time());
			b.setId(a.getId());
			b.setUser_id(a.getUser_id());
			b.setUsername(a.getUsername());
			b.setIs_article(a.getIs_article());
			// 结合redis和数据库中的数据
			b.setLikes_count(a.getLikes_count() + redis_likes);
			b.setView_count(a.getView_count() + redis_views);
			// 过滤敏感词
			b.setSummary(TextFilter.doFilter(a.getSummary()));
			b.setTitle(TextFilter.doFilter(a.getTitle()));
			// 获取文章的分类列表
			List<Category> category = categoryDAO.findByArticleId(a.getId());
			// 获取文章的标签列表
			List<Tag> tag = tagDAO.findByArticleId(a.getId());
			// 如果是动态则获取其图片等信息
			List<Media> media = mediaService.getByArticle(a.getId());

			// 获取用户信息
			UserSimple user_simple = userService.getUserSimple(a.getUser_id());
			UserDTO user_message = new UserDTO();
			user_message.setUsername(user_simple.getUsername());
			user_message.setUser_mail(user_simple.getUser_mail());
			user_message.setId(user_simple.getId());
			user_message.setGender(user_simple.getGender());
			user_message.setImage(user_simple.getImage());
			user_message.setIsAdmin(user_simple.getIsAdmin());
			user_message.setProfile(user_simple.getProfile());
			user_message.setRegister_time(user_simple.getRegister_time());

			ArticleDTO<ArticleVO> dto = new ArticleDTO<ArticleVO>(b, user_message, category, tag, media);

			data.add(dto);
		}
		return data;
	}

	@Override
	public void tranViewsFromRedis() {
		List<Article> articles = redisService.getViewsFromRedis();
		for (Article a : articles) {
			Integer t = articleDAO.updateViews(a.getId(), a.getView_count());
		}
	}

	@Override
	public Article saveOrUpdate(ArticleDTO<Article> article) {
		if (article.getArticle().getId() != 0) {
			update(article);
		} else {
			insert(article);
		}
		return article.getArticle();
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getPost(int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countByPost();
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getPost(pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> postByUserId(int user_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countPostByUserId(user_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.postByUserId(user_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> postByTag(int tag_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countPostByTag(tag_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.postByTag(tag_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> postByFollow(int follow_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countPostByFollow(follow_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.postByFollow(follow_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> getArticle(int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticle();
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.getArticle(pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByUserId(int user_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByUserId(user_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.ArticleByUserId(user_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByCategory(int cate_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByCategory(cate_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.ArticleByCategory(cate_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByTag(int tag_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByTag(tag_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.ArticleByTag(tag_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByFollow(int follow_id, int page, int limit, int order) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByFollow(follow_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> modules = articleDAO.ArticleByFollow(follow_id, pageInfo.getStartNum(), limit, order);
			data = this.tranDTO(modules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByTime(String time, int page, int limit) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByTime(time);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> mdoules = articleDAO.ArticleByTime(time, pageInfo.getStartNum(), limit);
			data = this.tranDTO(mdoules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}

	@Override
	public JSONResult<ArticleDTO<ArticleVO>> ArticleByTimeAndUserId(String time, int user_id, int page, int limit) {
		JSONResult<ArticleDTO<ArticleVO>> result = new JSONResult<>(Msg.OK, Msg.fail_code, Msg.success);
		Integer totalNum = articleDAO.countArticleByTimeAndUserId(time, user_id);
		List<ArticleDTO<ArticleVO>> data = new ArrayList<>();
		if (totalNum != 0) {
			PageInfo pageInfo = new PageInfo(page, limit, totalNum);
			List<ArticleVO> mdoules = articleDAO.ArticleByTimeAndUserId(time, user_id, pageInfo.getStartNum(), limit);
			data = this.tranDTO(mdoules);
			result = new JSONResult<>(Msg.OK, Msg.success_code, Msg.success, pageInfo, data);
		}
		return result;
	}
}
