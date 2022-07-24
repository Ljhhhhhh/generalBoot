package idx.lesson.generalBoot.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BizException extends RuntimeException {

  private String message;
  private Integer code;

  public BizException(String message, Integer code) {
    super(message);
    this.message = message;
    this.code = code;
  }

  public static void fail(String message, Integer code) {
    throw new BizException(message, code);
  }
}