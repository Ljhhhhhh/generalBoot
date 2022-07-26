package idx.lesson.generalBoot.controller.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginParam {
  @ApiModelProperty("用户名")
  @NotEmpty(message = "用户名不能为空")
  private String username;

  @ApiModelProperty("用户密码")
  @NotEmpty(message = "密码不能为空")
  private String password;
}
