package daangnmarket.daangntoyproject.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imgId;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id") // name : "어떤 컬럼으로 연결할지", referencedColumnName : 연결할 상대 테이블의 컬럼
    private Post post;

    @Column(name = "img_name")
    private String imgName;

    @Column(name = "img_url")
    private String imgUrl;




}
