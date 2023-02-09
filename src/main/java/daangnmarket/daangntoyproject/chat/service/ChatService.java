package daangnmarket.daangntoyproject.chat.service;

import daangnmarket.daangntoyproject.chat.controller.ChatController;
import daangnmarket.daangntoyproject.chat.domain.ChatContent;
import daangnmarket.daangntoyproject.chat.domain.ChatMessage;
import daangnmarket.daangntoyproject.chat.domain.ChatRoom;
import daangnmarket.daangntoyproject.chat.model.ChatContentDto;
import daangnmarket.daangntoyproject.chat.model.ChatRoomDto;
import daangnmarket.daangntoyproject.chat.repository.ChatContentRepository;
import daangnmarket.daangntoyproject.chat.repository.ChatRoomRepository;
import daangnmarket.daangntoyproject.post.domain.Image;
import daangnmarket.daangntoyproject.post.domain.Post;
import daangnmarket.daangntoyproject.post.model.PostListDto;
import daangnmarket.daangntoyproject.post.repository.ImageRepository;
import daangnmarket.daangntoyproject.post.repository.PostRepository;
import daangnmarket.daangntoyproject.user.domain.User;
import daangnmarket.daangntoyproject.user.model.UserDto;
import daangnmarket.daangntoyproject.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private static final Logger logger =  LoggerFactory.getLogger(ChatService.class);

    @Autowired
    private ChatContentRepository chatContentRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    public List<ChatContentDto> findChatContents(String loginId , int pId) {
        logger.info("findChatContents - service(buyerId={}, pId={})", loginId, pId);
        // 만약 buyerId 와 pId에 해당하는 roomId가 없다면 room을 생성해준다.
        ChatRoomDto chatRoomDto = null;
        ChatRoom chatRoom = chatRoomRepository.findByBuyerIdOrSellerIdAndPostId(loginId,loginId, pId);  // buyerId나 sellerId가 loginId와 같아야 함
        if(chatRoom == null){
            Post postEntity = postRepository.findById(pId).orElse(null);   // sellerId 찾기
            chatRoom = new ChatRoom(postEntity.getUserId(),loginId, pId);       // 새로운 방을 만들어준 뒤
            chatRoom =  chatRoomRepository.save(chatRoom);                      // entity 다시 받아옴
            chatRoomDto = new ChatRoomDto(chatRoom);
        }else {
            chatRoomDto = new ChatRoomDto(chatRoom);
        }

        List<ChatContentDto> chatContentDtos = new ArrayList<ChatContentDto>();
        // 해당 roomId에 해당하는 content는 다 가져와야 한다.
        List<ChatContent> chatContents = chatContentRepository.findByRoomIdOrderByCreateDate(chatRoomDto.getRoomId());
        int idx = 0;
        for (ChatContent content:chatContents){
            chatContentDtos.add(idx, new ChatContentDto(content));
            // user정보 넣어주기
            User user = userRepository.findById(content.getUserId()).orElse(null);
            UserDto userDto = new UserDto(user);
            chatContentDtos.get(idx).setUserDto(userDto);
            idx++;
        }
        return chatContentDtos;
    }

    // login 유저의 chatRooms 찾기
    public List<ChatRoomDto> findChatRooms(String loginId) {    // loginId가 포함된 모든 chatRoom을 찾아야 함

        List<ChatRoom> chatRooms = chatRoomRepository.findByBuyerIdOrSellerId(loginId, loginId);
        List<ChatRoomDto> chatRoomDtos = new ArrayList<ChatRoomDto>();
        int idx = 0;
        for (ChatRoom chatRoom : chatRooms){
            chatRoomDtos.add(idx, new ChatRoomDto(chatRoom));
            Image topImage = imageRepository.findByPostIdResultOne(chatRoom.getPostId());
            chatRoomDtos.get(idx).setProdImgUrl(topImage.getImgUrl());  // 만약 게시글이 삭제되면 NullPointException 발생
            idx++;
        }
        // user객체 넣어야 됨
        List<UserDto> userDtos = findChatUsers(chatRoomDtos);
        logger.info("사용자 전달받음 userDtos={}", userDtos);

        for(int i = 0; i < chatRoomDtos.size(); i++){
            for (int j = 0; j < userDtos.size(); j++){
                if(chatRoomDtos.get(i).getBuyerId().equals(userDtos.get(j).getUserId())){
                    chatRoomDtos.get(i).setBuyerUserDto(userDtos.get(j));
                    logger.info("사용자 buyer 넣어줌 - chatRoomDtos={}", chatRoomDtos.get(i));
                }
                if(chatRoomDtos.get(i).getSellerId().equals(userDtos.get(j).getUserId())){
                    chatRoomDtos.get(i).setSellerUserDto(userDtos.get(j));
                    logger.info("사용자 seller 넣어줌 - chatRoomDtos={}", chatRoomDtos.get(i));
                }
            }
        }

        return chatRoomDtos;
    }

    public List<UserDto> findChatUsers(List<ChatRoomDto> chatRoomDtos){
        logger.info("findChatUsers(chatRoomDtos={})", chatRoomDtos);
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
        logger.info("findChatUsers(userDtos={})", userDtos);

        // 중복제거
        for(int i = 0; i < userDtos.size(); i++){
            for (int j = i+1; j < userDtos.size(); j++){
                if(userDtos.get(i).getUserId() == userDtos.get(j).getUserId()){
                    userDtos.remove(j);
                }
            }
        }
        logger.info("중복제거 findChatUsers(userDtos={})", userDtos);

        return userDtos;
    }

    // 채팅 내용 저장하기 -> roomId, userId, content 저장
    public void saveChatContent(ChatMessage chatMessage) {
        // 메시지를 저장해줘야 함
        ChatContent chatContent = new ChatContent(chatMessage.getRoomId(), chatMessage.getUserId(), chatMessage.getMessage());
        ChatContent result = chatContentRepository.save(chatContent);
        if(result == null){
            chatMessage.setResult(false);   // 저장 실패
        }else {
            User user = userRepository.findById(chatMessage.getUserId()).orElse(null);
            chatMessage.setResult(true);    // 저장 성공
            chatMessage.setImgUrl(user.getImgUrl());
        }
    }
}
