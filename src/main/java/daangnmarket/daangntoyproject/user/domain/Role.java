package daangnmarket.daangntoyproject.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "role_name")
    private String roleName;


    @ManyToMany(mappedBy = "roles", cascade=CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Role(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

}
