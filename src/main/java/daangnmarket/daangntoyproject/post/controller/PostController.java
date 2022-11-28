package daangnmarket.daangntoyproject.post.controller;

import daangnmarket.daangntoyproject.post.model.PostDetailDto;
import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.service.PostService;
import daangnmarket.daangntoyproject.user.controller.AccountController;
import daangnmarket.daangntoyproject.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class PostController {


    @Autowired
    private PostService postService;

    @GetMapping(value = "posts")
    public String getPosts(@RequestParam(required = false) String searchText,
                          Model model){

        List<PostListDto> posts = postService.getPosts(searchText);
        model.addAttribute("posts", posts);

        return "/post/post-list";
    }

    @GetMapping(value = "post")
    public String detail(@RequestParam(required = false) int pId,
                        Model model, HttpSession session){
        UserDto login = (UserDto) session.getAttribute("login");
        PostDetailDto post = postService.getPost(pId, login.getUserId());
        // 인기글 가져와야 함
        List<PostListDto> posts = postService.getPopularPosts(post.getRegion());

        model.addAttribute("posts", posts);
        model.addAttribute("post", post);
        return "/post/post-detail";
    }

}
