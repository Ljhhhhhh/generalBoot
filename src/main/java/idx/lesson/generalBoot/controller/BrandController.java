package idx.lesson.generalBoot.controller;

import idx.lesson.generalBoot.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "BrandController", description = "品牌管理")
@RequestMapping("/brand")
public class BrandController {
    @ApiOperation("获取所有品牌列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> getBrandList() {
        return Result.success("hello world");
    }
}
