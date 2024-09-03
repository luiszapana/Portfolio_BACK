package com.luis.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.luis.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	ApplicationContext context;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authHeader = request.getHeader("Authorization");
			String token = null;
			String username = null;
			
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				token = authHeader.substring(7);
				username = jwtProvider.getUsernameFromToken(token);
			}
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				//En lugar de UserDetailsServiceImpl, Telusko utilizo MyUserDetailsService
				UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(username);
				if (jwtProvider.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = 
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}	
		}catch (Exception e) {
			logger.error("Falló el método doFilterInternal");
		}
		filterChain.doFilter(request, response);		
	}
}
