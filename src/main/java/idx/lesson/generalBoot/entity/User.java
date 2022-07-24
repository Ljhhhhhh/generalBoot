package idx.lesson.generalBoot.entity;

import lombok.Data;

@Data
public class User {
    /**
    * 用户id
    */
    private Integer id;

    /**
    * 用户名
    */
    private String username;

    /**
    * 用户密码
    */
    private String password;

    /**
    * 昵称
    */
    private String nickname;

    /**
    * 手机号
    */
    private String mobile;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 是否启用 1=启用 0=禁用
    */
    private Byte status;

    /**
    * 创建时间
    */
    private Long createTime;
}