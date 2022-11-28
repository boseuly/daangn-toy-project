package daangnmarket.daangntoyproject.post.service;

import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.domain.Region;
import daangnmarket.daangntoyproject.post.model.PostDetailDto;
import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.model.View;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import daangnmarket.daangntoyproject.post.repository.ViewRepository;
import daangnmarket.daangntoyproject.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ViewRepository viewRepository;

    public List<PostListDto> getPosts(String searchText){

        // 객체를 가져와야 함.
        List<Post> posts = postRepository.findByPostTitleContaining(searchText);
        List<PostListDto> postDtos = new ArrayList<PostListDto>();
        int idx = 0;
        for(Post post:posts){
            postDtos.add(idx, new PostListDto(post));
            postDtos.get(idx).setImgUrl(post.topImageUrl());    // 대표이미지 가져오기
            idx++;
        }
        // 이미지 가져오기
        return postDtos;

    }

    @Transactional
    public PostDetailDto getPost(int pId, String userId) {
        Optional<Post> postEntity = postRepository.findById(pId);
        PostDetailDto postDto = new PostDetailDto(postEntity);

        System.out.println("userId = " + userId);
        // 방문 확인
        int visit = _check_visit(pId, userId);
        if(visit == 0){ // 방문 x
            postDto.setViewCnt(postDto.getViewCnt() + 1);
            postEntity.get().setViewCnt(postEntity.get().getViewCnt() + 1);
            postRepository.save(postEntity.get());
        }
        return postDto;
    }

    private int _check_visit(int pId, String userId){
        Optional<View> result = viewRepository.findByPostIdAndUserId(pId,userId);

        if(result.isEmpty()){   // 단순히 null이라고 하면 save 안 됨, isEmpty라고 해줘야 한다.
            View view = new View(pId, userId);
            viewRepository.save(view);
            return 0;   // 방문 x
        }else {
            return 1;   // 방문 o
        }
    }

    // 유명게시글 찾기
    public List<PostListDto> getPopularPosts(String region) {
        String[] regionList = region.split(" ");
        System.out.println(Arrays.stream(regionList).toArray());
        List<Post> posts = postRepository.findByRegionName_RegionName(regionList[0]);
        List<PostListDto> postListDto = new ArrayList<>();
        int idx = 0;
        for (Post postEntity: posts){
            postListDto.add(idx, new PostListDto(postEntity));
            idx++;
        }
        return postListDto;
    }
}
