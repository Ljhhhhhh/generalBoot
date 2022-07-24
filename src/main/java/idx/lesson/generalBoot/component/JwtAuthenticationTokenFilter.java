package idx.lesson.generalBoot.component;

import idx.lesson.generalBoot.config.ConfigConsts;
import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.service.UserService;
import idx.lesson.generalBoot.entity.User;
import idx.lesson.generalBoot.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  @Resource
  private UserDetailsService userDetailsService;

  @Resource
  private UserService userService;


  @Resource
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws ServletException, IOException {
    String authHeader = request.getHeader(ConfigConsts.JWT_HEADER);
    if (authHeader != null && authHeader.startsWith(ConfigConsts.JWT_PREFIX)) {
      // The part after "Bearer "
      String authToken = authHeader.substring(ConfigConsts.JWT_PREFIX.length());
      String username = jwtTokenUtil.getUserNameFromToken(authToken);
      log.info("checking username:{}", username);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
          log.info("username error");
        }
      }
    }
    chain.doFilter(request, response);
  }
}
