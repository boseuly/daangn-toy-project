package daangnmarket.daangntoyproject.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "boards")
public class BoardController {

    @GetMapping(value = "")
    public String getBoard(){
        return "/board/board-list";
    }


}
