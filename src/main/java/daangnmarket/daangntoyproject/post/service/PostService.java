package daangnmarket.daangntoyproject.post.service;

import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

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
}
