package org.itzheng.utils.db.bean.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.itzheng.utils.ArrayUtils;
import org.itzheng.utils.db.bean.base.BaseResult;

/**
 * 分享内容详情结果
 * 
 * @author WL001
 *
 */
public class ShareDetailsResult extends BaseResult<ShareDetailsResult.Item> {
	/**
	 * 获取一个实例,初始化一些数据，避免空指针
	 * 
	 * @return
	 */
	public static ShareDetailsResult newInstance() {
		ShareDetailsResult result = new ShareDetailsResult();
		result.dsERP = new DsERPBean<ShareDetailsResult.Item>();
		result.dsERP.rows = new ArrayList<ShareDetailsResult.Item>();
		return result;
	}

	public static class Item {
		/**
		 * 基本信息，比如标题，发送人啥的，是否关注等
		 */
		public Map<String, Object> baseContent;
		/**
		 * 点赞数
		 */
		public int fFavoriteCount;
		/**
		 * 点赞列表
		 */
		public List<Map<String, Object>> likeUsers;
		/**
		 * 评论列表
		 */
		public List<Map<String, Object>> comments;
		/**
		 * 其他共享内容
		 */
		public List<Map<String, Object>> otherShares;

//		public static class LikeUsers {
//			/**
//			 * 总共收藏数量
//			 */
//			public Integer fFavoriteCount;
//			/**
//			 * 收藏人列表
//			 */
//			public List<Map<String, Object>> fUsers;
//		}
	}

	/**
	 * 获取item,就是当前操作的对象
	 * 
	 * @return
	 */
	private Item getItem() {
		Item item = null;
		if (ArrayUtils.isEmpty(dsERP.rows)) {
			item = new Item();
			this.dsERP.rows.add(item);
		} else {
			item = this.dsERP.rows.get(0);
		}
		return item;
	}

	/**
	 * 设置基本信息
	 * 
	 * @param rows
	 */
	public void setBaseInfo(List<Map<String, Object>> rows) {
		if (ArrayUtils.isEmpty(rows)) {
			return;
		}
		Map<String, Object> baseInfo = rows.get(0);
		Item item = getItem();
		item.baseContent = baseInfo;
	}

	/**
	 * 设置点赞人数列表
	 * 
	 * @param rows
	 */
	public void setLikeUsers(List<Map<String, Object>> rows) {
		getItem().likeUsers = rows;
	}

	/**
	 * 设置点赞人数数量
	 * 
	 * @param count
	 */
	public void setLikeUsersCount(int count) {
		getItem().fFavoriteCount = count;
	}

	/**
	 * 设置评论列表
	 * 
	 * @param rows
	 */
	public void setComments(List<Map<String, Object>> rows) {
		getItem().comments = rows;
	}

	/**
	 * 设置其他博客内容
	 * 
	 * @param rows
	 */
	public void setOtherBlogs(List<Map<String, Object>> rows) {
		getItem().otherShares = rows;
	}
}
