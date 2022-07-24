package idx.lesson.generalBoot.config;

import idx.lesson.generalBoot.component.JwtAuthenticationTokenFilter;
import idx.lesson.generalBoot.component.RestAuthenticationEntryPoint;
import idx.lesson.generalBoot.component.RestfulAccessDeniedHandler;
import idx.lesson.generalBoot.dao.RoleMapper;
import idx.lesson.generalBoot.dto.UserDto;
import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.entity.User;
import idx.lesson.generalBoot.entity.UserRoleRelation;
import idx.lesson.generalBoot.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource
  UserService userService;

  @Resource
  RoleMapper roleMapper;

  @Resource
  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

  @Resource
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            // 由于使用的是JWT，我们这里不需要csrf
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // 基于token，所以不需要session
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,
                    // 允许对于网站静态资源的无授权访问
                    "/",
                    "/*.html",
                    "/favicon.ico",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js",
                    "/swagger-resources/**",
                    "/v2/api-docs/**"
            )
            .permitAll()
            .mvcMatchers("/", "/authorize/**")
//            .antMatchers("/ /login", "/admin/register")
            // 对登录注册要允许匿名访问
            .permitAll()
            .antMatchers("/brand/**").hasRole("ADMIN")
            .antMatchers(HttpMethod.OPTIONS)
            //跨域请求会先进行一次options请求
            .permitAll()
//                .antMatchers("/**")//测试时全部允许访问
//                .permitAll()
            .anyRequest()// 除上面外的所有请求全部需要鉴权认证
            .permitAll();
    // 禁用缓存
    httpSecurity.headers().cacheControl();
    // 添加JWT filter
    httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    //添加自定义未授权和未登录结果返回
    httpSecurity.exceptionHandling()
            .accessDeniedHandler(restfulAccessDeniedHandler)
            .authenticationEntryPoint(restAuthenticationEntryPoint);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
            .passwordEncoder(passwordEncoder());
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    // 允许跨域访问的主机
    configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Collections.singletonList("*"));
    configuration.addExposedHeader(ConfigConsts.JWT_HEADER);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
    return new JwtAuthenticationTokenFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public UserDetailsService userDetailsService() {
    //获取登录用户信息
    return username -> {
      User user = userService.getUserByUsername(username);
      if (user != null) {
        List<String> roles = userService.getUserRoleList(user.getId());
//        List<Role> roles = userRoleRelation
//                .stream()
//                .map(relation -> roleMapper.getRoleNameById(relation.getRoleId()))
//                .collect(Collectors.toList());
        UserDto userDetail = new UserDto(user, roles);
        return userDetail;
      }
      throw new UsernameNotFoundException("用户名或密码错误");
    };
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
