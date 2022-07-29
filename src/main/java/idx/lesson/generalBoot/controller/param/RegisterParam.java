package idx.lesson.generalBoot.controller.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterParam {
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    private String email;
}
