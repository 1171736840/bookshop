package com.pc.util;

import java.io.IOException;
import java.util.Properties;

public class ProjectCfg {
	//系统换行符
	public static final String NEW_LINE = System.getProperty("line.separator");
	//数据库分页查询参数，每页显示多少条记录
	private static int pageSize=10;
	//图片保存基础路径
	private static String picSavedBasePath;
	//图片访问基础Url
	private static String picAccessBaseUrl;
	//图书封面子文件夹
	private static String bookCoverSubDir;
	//图书每页评论数
	private static int bookCommentPageSize = 10;

	public static int getBookCommentPageSize() {
		return bookCommentPageSize;
	}

	public static void setBookCommentPageSize(int bookCommentPageSize) {
		ProjectCfg.bookCommentPageSize = bookCommentPageSize;
	}

	static {
		Properties p = new Properties();
		try {
			p.load(ProjectCfg.class.getClassLoader().getResourceAsStream("project.properties"));
			pageSize = Integer.parseInt(p.getProperty("pageSize"));
			bookCommentPageSize = Integer.parseInt(p.getProperty("bookCommentPageSize"));
			picSavedBasePath = p.getProperty("picSavedBasePath");
			picAccessBaseUrl = p.getProperty("picAccessBaseUrl");
			bookCoverSubDir = p.getProperty("bookCoverSubDir");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int getPageSize() {
		return pageSize;
	}

	public static String getPicSavedBasePath() {
		return picSavedBasePath;
	}

	public static String getPicAccessBaseUrl() {
		return picAccessBaseUrl;
	}

	public static String getBookCoverSubDir() {
		return bookCoverSubDir;
	}
	
}
