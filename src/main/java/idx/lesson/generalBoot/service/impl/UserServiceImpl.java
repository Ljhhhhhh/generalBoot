package idx.lesson.generalBoot.service.impl;

import idx.lesson.generalBoot.controller.param.RegisterParam;
import idx.lesson.generalBoot.dao.RoleMapper;
import idx.lesson.generalBoot.dao.UserMapper;
import idx.lesson.generalBoot.dao.UserRoleRelationMapper;
import idx.lesson.generalBoot.entity.User;
import idx.lesson.generalBoot.entity.UserRoleRelation;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleRelationMapper userRoleRelationMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User register(RegisterParam registerInfo) {
        User user = new User();
        BeanUtils.copyProperties(registerInfo, user);
        user.setCreateTime(System.currentTimeMillis());
        user.setStatus((byte) 1);
        // 查询是否有相同用户名的用户
        User userList = userMapper.selectByUsername(user.getUsername());
        if (userList != null) {
            return null;
        }
        // 将密码进行加密操作
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        userMapper.insert(user);
        return user;
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
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<String> getUserRoleList(Integer id) {
        List<UserRoleRelation> userRoleRelation = userRoleRelationMapper.getRoleListByUserId(id);
        List<String> roles = userRoleRelation
                .stream()
                .map(relation -> "ROLE_" + roleMapper.getRoleNameById(relation.getRoleId()).getRoleName())
                .collect(Collectors.toList());
        log.info("{}", roles.toString());
        return roles;
    }
}
