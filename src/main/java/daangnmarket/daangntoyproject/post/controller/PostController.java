package daangnmarket.daangntoyproject.post.controller;

import daangnmarket.daangntoyproject.post.domain.Category;
import daangnmarket.daangntoyproject.post.domain.Region;
import daangnmarket.daangntoyproject.post.model.*;
import daangnmarket.daangntoyproject.post.repository.CategoryRepository;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import daangnmarket.daangntoyproject.post.repository.RegionRepository;
import daangnmarket.daangntoyproject.post.service.PostService;
import daangnmarket.daangntoyproject.user.controller.AccountController;
import daangnmarket.daangntoyproject.user.model.UserDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@MultipartConfig
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RegionRepository regionRepository;

    @GetMapping(value = "/posts")
    public String getPosts(@RequestParam(required = false) String searchText,
                          Model model){

        List<PostListDto> posts = postService.getPosts(searchText);
        model.addAttribute("posts", posts);

        return "/post/post-list";
    }

    @GetMapping(value = "/post")
    public String detail(@RequestParam(required = false) int pId,
                        Model model, HttpSession session){
        UserDto login = (UserDto) session.getAttribute("login");
        PostDetailDto post = postService.getPost(pId, login.getUserId());
        List<PostListDto> posts = postService.getPopularPosts(post.getRegion());    // 인기글 가져오기
        System.out.println("이미지 url 가져오기 : post image url = " + post.getImgUrl());

        model.addAttribute("post", post);
        model.addAttribute("posts", posts);
        return "/post/post-detail";
    }

    @GetMapping(value = "/post/form")
    public String form(Model model,
                    @RequestParam(required=false)String postId){
        List<CategoryDto> categories = new CategoryDto().changeDto(categoryRepository.findAll());
        List<RegionDto> regions = new RegionDto().changeDto(regionRepository.findAll());
        model.addAttribute("categories",categories);
        model.addAttribute("regions",regions);

        if(postId == null){
            // 글쓰기
            model.addAttribute("post", new PostSaveDto());    // 새로운 객체를 생성해서 전달
        }else {
            // 수정
            PostSaveDto saveDto = new PostSaveDto(postRepository.findById(Integer.parseInt(postId)));
            model.addAttribute("post", saveDto);
        }

        return "/post/post-form";
    }

    @PostMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<Object> add(@RequestPart(value = "saveDto") PostSaveDto saveDto,
                                      @RequestPart(value = "image") List<MultipartFile> files){
        System.out.println("controller - add 메소드 saveDto = "+saveDto);
        System.out.println("controller - files = " + files);

        postService.save(saveDto, files);

        return null;
    }



}
