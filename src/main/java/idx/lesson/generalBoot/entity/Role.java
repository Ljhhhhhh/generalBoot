package idx.lesson.generalBoot.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class Role implements GrantedAuthority {
    /**
    * 角色id
    */
    private Integer id;

    /**
    * 角色名称
    */
    private String roleName;

    @Override
    public String getAuthority() {
        return this.getRoleName();
    }
}