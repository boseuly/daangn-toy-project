package daangnmarket.daangntoyproject.post.repository;

import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findByPostTitleContaining(String searchText);

    @Query(value = "select p from Post p " +
            "join fetch p.region r " +
            "where r.addressName like %:region%")
    public List<Post> findByRegionName_RegionName(String region);



//    @Query(value = "insert into tb_post(seller_id, post_title, post_content, created_at, updated_at, status, deleted_yn, price, price_proposal_yn, region_id, category_id, view_cnt, like_cnt) " +
//            "values (:#{#post.userId},:#{#post.postTitle}, :#{#post.postContent},:#{#post.createdAt},:#{#post.updatedAt}, :#{#post.status}, :#{#post.deletedYn}, :#{#post.price}, :#{#post.proposalYn}, :#{#post.region.regionId}, :#{#post.category.categoryId}, :#{#post.viewCnt}, :#{#post.likeCnt})",nativeQuery = true)
//    @Modifying
//    @Transactional
//    public int postSave(@Param(value = "post") Post post, @Param(value = "userId") String userId);

}
