package daangnmarket.daangntoyproject.post.service;

import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.model.ImageDto;
import daangnmarket.daangntoyproject.post.model.PostDetailDto;
import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.domain.View;
import daangnmarket.daangntoyproject.post.model.PostSaveDto;
import daangnmarket.daangntoyproject.post.repository.ImageRepository;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import daangnmarket.daangntoyproject.post.repository.ViewRepository;
import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUtils fileUtils;

    public List<PostListDto> getPosts(String searchText){

        // 객체를 가져와야 함.
        List<Post> posts = postRepository.findByPostTitleContaining(searchText);
        List<PostListDto> postDtos = new ArrayList<PostListDto>();
        int idx = 0;
        for(Post post:posts){
            Image topImage = imageRepository.findByPostIdResultOne(post.getPostId());
            postDtos.add(idx, new PostListDto(post));
            postDtos.get(idx).setImgUrl(topImage.getImgUrl());    // 대표이미지 가져오기
            idx++;
        }
        // 이미지 가져오기
        return postDtos;
    }

    @Transactional
    public PostDetailDto getPost(int pId, String userId) {
        Optional<Post> postEntity = postRepository.findById(pId);
        PostDetailDto detailDto = new PostDetailDto(postEntity);    // userDto 저장 안되고 userId만 저장됨
        detailDto.setUserDto(new UserDto(userRepository.findById(detailDto.getUserId()))); // Post에는 userDto가 없기 때문에 따로 user 상세정보를 찾아서 넣어준다.
        detailDto.setImgUrl(imageRepository.findByPostId(pId));     // 이미지 저장

        // 방문 확인
        int visit = _check_visit(pId, userId);
        if(visit == 0){ // 방문 x
            detailDto.setViewCnt(detailDto.getViewCnt() + 1);
            postEntity.get().setViewCnt(postEntity.get().getViewCnt() + 1);
            postRepository.save(postEntity.get());
        }
        return detailDto;
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
            Image topImage = imageRepository.findByPostIdResultOne(postEntity.getPostId());
            postListDto.add(idx, new PostListDto(postEntity));
            postListDto.get(idx).setImgUrl(topImage.getImgUrl());
            idx++;
        }
        return postListDto;
    }


    // 게시글 저장(추가 or 수정)
    @Transactional
    public ResponseEntity<Object> save(PostSaveDto saveDto, List<MultipartFile> images, HttpServletRequest request) throws IOException {
        PostDetailDto postDetailDto = null;
        if(saveDto.getPostId() == 0){   // 추가
            postDetailDto = new PostDetailDto(postRepository.save(saveDto.toEntity()));
        }else{                          // 수정
//            saveDto.toEntity(postRepository.findById(saveDto.getPostId()));
        }

        // 이미지 저장
        if(!images.isEmpty()){          // images가 비어있지 않다면
            List<ImageDto> imageDtos = FileUtils.imageUpload(images, postDetailDto.getPostId(), request);
            for (ImageDto imageDto : imageDtos){
                imageRepository.save(imageDto.toEntity());
            }
        }else{
            ImageDto imageDto = FileUtils.defaultIamge(saveDto.getPostId());
            imageRepository.save(imageDto.toEntity());
        }
        ResponseEntity<Object> res = new ResponseEntity<>(postDetailDto, HttpStatus.OK);
        return res;
    }
}
