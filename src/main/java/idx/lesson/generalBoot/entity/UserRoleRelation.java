package idx.lesson.generalBoot.entity;

import lombok.Data;
@Data
public class UserRoleRelation {
    /**
    * 用户id
    */
    private Integer userId;

    /**
    * 角色id
    */
    private Integer roleId;
}