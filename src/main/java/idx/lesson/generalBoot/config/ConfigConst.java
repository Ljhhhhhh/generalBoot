package idx.lesson.generalBoot.config;

public class ConfigConst {
  public final static String JWT_HEADER = "Authorization";
  public final static String JWT_PREFIX = "Bearer ";
  public final static Long ACCESS_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;
  public final static String REDIS_JWT_KEY_PREFIX = "security:jwt:";
  public final static Integer USER_ENABLED = 1;
}
