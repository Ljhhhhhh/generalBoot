package idx.lesson.generalBoot.component;

import cn.hutool.json.JSONUtil;
import idx.lesson.generalBoot.utils.Result;
import idx.lesson.generalBoot.utils.ResultEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request,
                     HttpServletResponse response,
                     AccessDeniedException e) throws IOException, ServletException {
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    response.getWriter().println(JSONUtil.parse(Result.error(ResultEnum.ACCESS_DENIED.getCode(), ResultEnum.ACCESS_DENIED.getMessage())));
    response.getWriter().flush();
  }
}
