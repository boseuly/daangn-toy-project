package daangnmarket.daangntoyproject.post.controller;

import daangnmarket.daangntoyproject.post.domain.Category;
import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Region;
import daangnmarket.daangntoyproject.post.model.*;
import daangnmarket.daangntoyproject.post.repository.CategoryRepository;
import daangnmarket.daangntoyproject.post.repository.ImageRepository;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import daangnmarket.daangntoyproject.post.repository.RegionRepository;
import daangnmarket.daangntoyproject.post.service.PostService;
import daangnmarket.daangntoyproject.user.controller.AccountController;
import daangnmarket.daangntoyproject.user.model.UserDto;
import lombok.Getter;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                        Model model, HttpSession session){          // session : 조회때문에 필요

        UserDto login = (UserDto) session.getAttribute("login");
        PostDetailDto post = postService.getPost(pId, login.getUserId());
        List<PostListDto> posts = postService.getPopularPosts(post.getRegion());    // 인기글 가져오기

        model.addAttribute("post", post);
        model.addAttribute("posts", posts);
        return "/post/post-detail";
    }

    @GetMapping(value = "/post/form")
    public String form(Model model,
                    @RequestParam(required=false)String pId){

        List<CategoryDto> categories = new CategoryDto().changeDto(categoryRepository.findAll());
        List<RegionDto> regions = new RegionDto().changeDto(regionRepository.findAll());

        if(pId == null){
            // 글쓰기
            model.addAttribute("post", new PostSaveDto());    // 새로운 객체를 생성해서 전달
        }else {
            // 수정
            PostSaveDto saveDto = new PostSaveDto(postRepository.findById(Integer.parseInt(pId)));
            List<ImageDto> imageDtos = postService.getImages(Integer.parseInt(pId));
            model.addAttribute("post", saveDto);
            model.addAttribute("images", imageDtos);
        }

        model.addAttribute("categories",categories);
        model.addAttribute("regions",regions);

        return "/post/post-form";
    }

    @PostMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<Object> add(@RequestPart(value = "saveDto") PostSaveDto saveDto,
                                      @RequestPart(value = "image", required = false) List<MultipartFile> files) throws IOException {
        return postService.save(saveDto, files);
    }

    @PutMapping(value = "/post")
    @ResponseBody
    public ResponseEntity<Object> modify(@RequestPart(value = "saveDto") PostSaveDto saveDto,
                                      @RequestPart(value = "image", required = false) List<MultipartFile> files) throws IOException {
        System.out.println("게시글 수정 controller 전달 완료 saveDto = " + saveDto);
        System.out.println("게시글 수정 controller 전달 완료 image = " + files);

        return postService.modify(saveDto, files);
    }

    @DeleteMapping(value = "/post/image")
    @ResponseBody
    public ResponseEntity<Object> imageRemove(@RequestParam(value = "imgId")int imgId){
        return postService.deleteImage(imgId);
    }



}
