package com.nicordesigns.site.filters;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.CloseableThreadContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

public class LoggingFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        try (CloseableThreadContext.Instance ctc = CloseableThreadContext.push("id", UUID.randomUUID().toString())) {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            if (session != null) {
                ctc.put("username", (String) session.getAttribute("username"));
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void destroy()
    {

    }
}
