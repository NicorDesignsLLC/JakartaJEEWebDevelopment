package com.nicordesigns.filters;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class RequestLogFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        Instant start = Instant.now();
        try
        {
            chain.doFilter(request, response);
        }
        finally
        {
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            
            HttpServletRequest in = (HttpServletRequest)request;
            HttpServletResponse out = (HttpServletResponse)response;
            String length = out.getHeader("Content-Length");
            if(length == null || length.length() == 0)
                length = "-";
            System.out.println(in.getRemoteAddr() + " - - [" + timeElapsed + "]" +
                    " \"" + in.getMethod() + " " + in.getRequestURI() + " " +
                    in.getProtocol() + "\" " + out.getStatus() + " " + length +
                    " " + start);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void destroy() { }
}
