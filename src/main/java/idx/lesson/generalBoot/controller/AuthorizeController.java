package idx.lesson.generalBoot.controller;

import idx.lesson.generalBoot.controller.param.LoginParam;
import idx.lesson.generalBoot.controller.param.RegisterParam;
import idx.lesson.generalBoot.entity.User;
import idx.lesson.generalBoot.service.UserService;
import idx.lesson.generalBoot.utils.Result;
import idx.lesson.generalBoot.utils.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "AuthorizeController")
@Slf4j
@RequestMapping("/authorize")
public class AuthorizeController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public Result<String> login(@RequestBody LoginParam loginParam) {
        String token = userService.login(loginParam.getUsername(), loginParam.getPassword());
        if (token == null) {
            return Result.error(ResultEnum.USERNAME_PASSWORD_ERROR.getCode(), ResultEnum.USERNAME_PASSWORD_ERROR.getMessage());
        }
        return Result.success(token);
    }

    @ApiOperation(value = "注册用户")
    @PostMapping(value = "/register")
    @ResponseBody
    public Result<Integer> register(@RequestBody RegisterParam registerParam) {
        User user = userService.register(registerParam);
        if (user != null) {
            return Result.success(user.getId());
        }
        return Result.error(ResultEnum.USER_EXIST.getCode(), ResultEnum.USER_EXIST.getMessage());
    }

    @ApiOperation(value = "分配角色")
    @PostMapping(value = "/setRole")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Boolean> setRole() {
        return Result.success(true);
    }
}
