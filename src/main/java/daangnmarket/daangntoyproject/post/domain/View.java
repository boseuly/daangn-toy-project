package daangnmarket.daangntoyproject.post.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "tb_view")
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "view_id")
    private int viewId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "user_id")
    private String userId;

    public View(int postId, String userId){
        this.postId = postId;
        this.userId = userId;
    }

    public View() {
    }
}
