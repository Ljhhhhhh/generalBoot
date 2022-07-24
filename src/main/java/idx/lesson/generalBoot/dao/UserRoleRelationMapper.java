package idx.lesson.generalBoot.dao;

import idx.lesson.generalBoot.entity.Role;
import idx.lesson.generalBoot.entity.UserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleRelationMapper {
    /**
     * delete by primary key
     * @param userId primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(UserRoleRelation record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserRoleRelation record);

    List<Role> getRoleListByUserId(Integer id);
}