package com.pc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.pc.entity.User;

public class Userfilter implements Filter {
	String [] exclude;//排出的选项，不经过过滤器
	String [] excludeFolder;//排出的文件夹，不经过过滤器
	String [] HTMLFolder;//要返回HTML的文件夹
    public Userfilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		String url = request.getRequestURI();//获取访问的url
		String ctxPath = request.getContextPath();//获取网站上下文路径
		System.out.println(url);
		System.out.println(ctxPath);
		
		boolean needFilter = true;//默认需要过滤
		
		
		for(int i = 0; i < exclude.length; i++) {
			if (! "".equals(exclude[i]) && url.equals(ctxPath + exclude[i])) {//检测到为排出项，不经过过滤的项则修改为不过滤
				needFilter = false;
				break;
				
			}
		}
		
		
		if (needFilter) {
			for(int i = 0; i < excludeFolder.length; i++) {
				if (! "".equals(excludeFolder[i]) && url.startsWith(ctxPath + excludeFolder[i] + "/")) {//检测到为排出项，不经过过滤的项则修改为不过滤
					needFilter = false;
					break;
					
				}
			}
		}
		
		if (needFilter) {//如果需要进行过滤
			//检测用户是否登录
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute("user");
			if(user == null) {
				
				
				for(int i = 0; i < HTMLFolder.length; i++) {
					if (! "".equals(HTMLFolder[i]) && url.startsWith(ctxPath + HTMLFolder[i] + "/")) {//检测是否要返回HTML页面
						//response.sendRedirect();//重定向到未登录的json请求
						request.setAttribute("code", "100");//传递代码，执行相应的操作
						request.setAttribute("title", "未登录");//传递代码，执行相应的操作
						request.setAttribute("pageTiele", "用户未登录");//传递代码，执行相应的操作
						request.setAttribute("pageMessage", "登录后即可继续。");//传递代码，执行相应的操作
						request.getRequestDispatcher("/jsp/blank").forward(request, response);
						return;
						
					}
				}
				
				response.sendRedirect(ctxPath + "/notLogin");//重定向到未登录的json请求
				return;
			}
		}

		chain.doFilter(servletRequest, servletResponse);//跳转到下一个过滤器
	}

	public void init(FilterConfig fConfig) throws ServletException {//过滤器初始化
		//获取web.xml中配置的排出选项，不经过过滤的路径
		System.out.println(fConfig.getInitParameter("filter"));
		System.out.println(fConfig.getInitParameter("filterFolder"));
		exclude = fConfig.getInitParameter("exclude").split(",");	//这里需要注意的是空字符串split后返回的不是空数组，而是有一个成员，为空字符串，
		excludeFolder = fConfig.getInitParameter("excludeFolder").split(",");
		HTMLFolder = fConfig.getInitParameter("HTMLFolder").split(",");
	}

}
