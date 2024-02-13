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

public class RequestLogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Entering RequestLogFilter.doFilter().");
		Instant start = Instant.now();
		try {
			chain.doFilter(request, response);
		} catch (IOException | ServletException e) {
			// Handle exceptions if needed
			System.out.printf("Error in request processing: %s%n", e.getMessage());
			throw e; // Rethrow the exception to propagate it further if necessary
		} finally {
			logRequestDetails(request, response, start);
			System.out.println("Leaving RequestLogFilter.doFilter().");
		}
	}

	private void logRequestDetails(ServletRequest request, ServletResponse response, Instant start) {
		HttpServletRequest in = (HttpServletRequest) request;
		HttpServletResponse out = (HttpServletResponse) response;
		String length = out.getHeader("Content-Length");
		if (length == null || length.length() == 0)
			length = "-";

		long timeElapsed = Duration.between(start, Instant.now()).toMillis();
		System.out.printf("%s - - [%d] \"%s %s %s\" %d %s %s%n",
			    in.getRemoteAddr(), timeElapsed, in.getMethod(),
			    in.getRequestURI(), in.getProtocol(), out.getStatus(), length, start);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
