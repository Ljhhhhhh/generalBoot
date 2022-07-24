package idx.lesson.generalBoot.dao;

import idx.lesson.generalBoot.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(@Param("id") Integer id, @Param("username") String username);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(User record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    User selectByPrimaryKey(@Param("id") Integer id, @Param("username") String username);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    User selectByUsername(@Param("username") String username);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(User record);
}