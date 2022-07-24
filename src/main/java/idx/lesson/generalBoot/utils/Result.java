package idx.lesson.generalBoot.utils;

import java.io.Serializable;

public class Result<T> implements Serializable {
  private Integer resultCode;
  private String message;
  private T data;

  public Result() {
  }

  public Result(Integer resultCode, String message) {
    this.resultCode = resultCode;
    this.message = message;
  }

  public Integer getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static <T> Result <T> success(T data) {
    Result<T> result = new Result<T>();
    result.setResultCode(ResultEnum.SUCCESS.getCode());
    result.setMessage(ResultEnum.SUCCESS.getMessage());
    result.setData(data);
    return result;
  }

  public static <T> Result <T> error(Integer resultCode, String message) {
    return new Result(resultCode, message);
  }

  @Override
  public String toString() {
    return "Result{" +
        "resultCode=" + resultCode +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }
}
