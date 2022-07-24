package idx.lesson.generalBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("idx.lesson.generalBoot.dao")
public class GeneralBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(GeneralBootApplication.class, args);
  }

}
