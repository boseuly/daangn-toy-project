package daangnmarket.daangntoyproject.user.service;

import daangnmarket.daangntoyproject.chat.controller.ChatController;
import daangnmarket.daangntoyproject.chat.model.ChatRoomDto;
import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger =  LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findChatUsers(List<ChatRoomDto> chatRoomDtos){
        List<String> userIdList = new ArrayList<>();
        for(ChatRoomDto chatRoomDto:chatRoomDtos){
            userIdList.add(chatRoomDto.getSellerId());
            userIdList.add(chatRoomDto.getBuyerId());
        }
        List<UserDto> userDtos = new ArrayList<>();
        List<User> userEntity = userRepository.findByUserIdIn(userIdList);
        for(User user: userEntity){
            userDtos.add(new UserDto(user));
        }
        return userDtos;
    }
}
