package idx.lesson.generalBoot.service.impl;

import idx.lesson.generalBoot.dao.RoleMapper;
import idx.lesson.generalBoot.dao.UserMapper;
import idx.lesson.generalBoot.dao.UserRoleRelationMapper;
import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.entity.User;
import idx.lesson.generalBoot.service.UserService;
import idx.lesson.generalBoot.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Resource
  private UserRoleRelationMapper userRoleRelationMapper;

  @Resource
  private UserDetailsService userDetailsService;

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private JwtTokenUtil jwtTokenUtil;

  @Override
  public User register(User btAdminParam) {
//    User umsAdmin = new User();
//    BeanUtils.copyProperties(btAdminParam, umsAdmin);
//    umsAdmin.setCreateTime(new Date());
//    umsAdmin.setStatus(1);
//    //查询是否有相同用户名的用户
//    User adminList = getAdminByUsername(umsAdmin.getUsername());
//    if (adminList != null) {
//      return null;
//    }
//    //将密码进行加密操作
//    String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
//    umsAdmin.setPassword(encodePassword);
//    adminMapper.insert(umsAdmin);
//    return umsAdmin;
    return new User();
  }

  @Override
  public String login(String username, String password) {
    String token = null;
    try {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        throw new BadCredentialsException("密码不正确");
      }
      token = jwtTokenUtil.generateToken(userDetails);
    } catch (AuthenticationException e) {
      log.warn("登录异常:{}", e);
    }
    return token;
  }

  @Override
  public User getUserByUsername(String username) {
    return  userMapper.selectByUsername(username);
  }

  @Override
  public List<Role> getUserRoleList(Integer id) {
    return userRoleRelationMapper.getRoleListByUserId(id);
  }
}
