package idx.lesson.generalBoot.entity;

import idx.lesson.generalBoot.controller.param.RegisterParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends RegisterParam {
    /**
    * 用户id
    */
    private Integer id;

    /**
    * 是否启用 1=启用 0=禁用
    */
    private Byte status;

    /**
    * 创建时间
    */
    private Long createTime;
}