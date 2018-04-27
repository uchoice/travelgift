package net.uchoice.travelgift.vote.intercepter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

//@Order(1)
//@WebFilter(filterName = "authFilter", urlPatterns = "/vote/*")
public class AuthFilter implements Filter {

	public static final String AUTH_KEY = "authorization";

	private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String authKey = request.getHeader(AUTH_KEY);
		log.info("req -> {} : {}, authorization: {}", request.getMethod(), request.getServletPath(), authKey);
		if (!StringUtils.isEmpty(authKey)) {
			chain.doFilter(req, resp);
		} else {
			HttpServletResponse response = (HttpServletResponse) resp;
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			resp.getWriter().append("非法请求，请先关注公众号“礼游记”，并通过公众号内菜单参加活动！");
			resp.getWriter().flush();
			resp.getWriter().close();
		}
	}

	@Override
	public void destroy() {

	}

}
