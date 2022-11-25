package daangnmarket.daangntoyproject.post.controller;

import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.service.PostService;
import daangnmarket.daangntoyproject.user.controller.AccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping(value = "posts")
public class PostController {


    @Autowired
    private PostService postService;

    @GetMapping(value = "")
    public String getPost(@RequestParam(required = false) String searchText
                         , Model model){

        List<PostListDto> posts = postService.getPosts(searchText);
        model.addAttribute("posts", posts);

        return "/post/post-list";
    }


}
