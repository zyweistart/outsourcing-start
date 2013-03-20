package com.start.test.discover;

import com.start.test.BaseCls;

public class InvitationTest extends BaseCls {
	/**
	 * 发布
	 */
	public void testPost() throws Exception{
		url="invitation/post.do";
		params.put("content", "this is post main content!");
		params.put("longitude", "127.04382");
		params.put("latitude", "92.348");
		sendRequest();
	}
	/**
	 * 获取我的帖子列表
	 */
	public void testMyList() throws Exception{
		url="invitation/my.do";
		params.put("currentpage", "1");
		params.put("pagesize", "2");
		sendRequest();
	}
	/**
	 * 获取我的帖子列表
	 */
	public void testNearby() throws Exception{
		url="invitation/nearby.do";
		params.put("longitude", "127.04382");
		params.put("latitude", "92.348");
		params.put("currentpage", "1");
		params.put("pagesize", "2");
		accessKey=null;
		sendRequest();
	}
	/**
	 * 详细
	 */
	public void testLoad() throws Exception{
		url="invitation/load.do";
		params.put("code", "c3ed573c10800dfddc0e375e6a3613ff");
		accessKey=null;
		sendRequest();
	}
	/**
	 * 删除
	 */
	public void testRemove() throws Exception{
		url="invitation/remove.do";
		params.put("code", "f07fb661358dc9eeb3a19d88efb5cfce");
		sendRequest();
	}
	/**
	 * 评论
	 */
	public void testComment() throws Exception{
		url="invitation/comment.do";
		params.put("InvitationCode", "c3ed573c10800dfddc0e375e6a3613ff");
		params.put("content", "this is comment main");
		params.put("longitude", "127.04382");
		params.put("latitude", "92.348");
		sendRequest();
	}
	/**
	 * 获取帖子的评论列表
	 */
	public void testinvitcommlist() throws Exception{
		url="invitation/invitcommlist.do";
		params.put("InvitationCode", "c3ed573c10800dfddc0e375e6a3613ff");
		params.put("currentpage", "2");
		params.put("pagesize", "1");
		accessKey=null;
		sendRequest();
	}
}