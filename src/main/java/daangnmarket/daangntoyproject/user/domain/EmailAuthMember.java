package daangnmarket.daangntoyproject.user.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@Entity
@Getter
@Table(name = "tb_email_auth_member")
public class EmailAuthMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    @Column(name = "auth_key")
    private String authKey;
    @Column(name = "auth_status")
    private int authStatus;

    public EmailAuthMember(){}

    @Builder
    public EmailAuthMember(String email, String authKey, int authStatus){
        this.email = email;
        this.authKey = authKey;
        this.authStatus = authStatus;
    }


}
