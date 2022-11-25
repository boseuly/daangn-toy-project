package daangnmarket.daangntoyproject.post.repository;

import daangnmarket.daangntoyproject.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
//    @Query(value = "select p.post_id, u.user_id, u.nickname, p.post_title, p.post_content, p.created_at, p.updated_at, p.status, p.deleted_yn, p.price, r.region_id, r.address_name, c.category_id, c.category_name, p.view_cnt, p.like_cnt" +
//                   "  from tb_post p" +
//                   " inner join tb_users u on p.seller_id = u.user_id" +
//                   " inner join tb_region r on p.region_id = r.region_id" +
//                   " inner join tb_category c on p.category_id = c.category_id" +
//                   " where p.post_title like '%:searchText%'" +
//                    "   or r.address_name like '%:searchText%'" +
//                    "order by p.create_at", nativeQuery = true)
    public List<Post> findByPostTitleContaining(String searchText);

}
