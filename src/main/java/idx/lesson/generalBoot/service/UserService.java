package idx.lesson.generalBoot.service;

import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.entity.User;

import java.util.List;

public interface UserService {
  /**
   * 注册功能
   */
  User register(User umsAdminParam);

  /**
   * 登录功能
   * @param username 用户名
   * @param password 密码
   * @return 生成的JWT的token
   */
  String login(String username, String password);

  /**
   * 根据用户名获取用户信息
   * @param username
   * @return
   */
  User getUserByUsername(String username);

  /**
   * 根据用户id获取用户角色
   * @param id
   * @return
   */
  List<Role> getUserRoleList(Integer id);
}
