package daangnmarket.daangntoyproject.post.repository;

import daangnmarket.daangntoyproject.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public List<Post> findByPostTitleContaining(String searchText);

    @Query(value = "select p from Post p " +
            "join fetch p.region r " +
            "where r.addressName like %:region%")
    public List<Post> findByRegionName_RegionName(String region);

}
