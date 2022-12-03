package daangnmarket.daangntoyproject.post.repository;

import daangnmarket.daangntoyproject.post.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "select img_id, post_id,img_name, img_url   from tb_image where post_id = :postId limit 1", nativeQuery = true)
    public Image findByPostIdResultOne(@Param(value = "postId") int postId);
    List<Image> findByPostId(int postId);
    void deleteByPostId(int pId);
}
