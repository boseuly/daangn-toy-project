package daangnmarket.daangntoyproject.post.service;

import daangnmarket.daangntoyproject.post.model.ImageDto;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {
    public static List<ImageDto> imageUpload(List<MultipartFile> images, int postId, HttpServletRequest request) throws IOException {
        List<ImageDto> imageDtos = new ArrayList<ImageDto>();
        ImageDto imageDto = null;
        String imgName = null;
        String savePath = "C:/Users/user1/daangn-toy-project/build/resources/main/static/images/"; // file 생성할 때 사용

        int idx = 0;
        for (MultipartFile mf : images) {

            imageDto = new ImageDto();
            imgName = UUID.randomUUID() + mf.getOriginalFilename(); // 중복 방지

            // db 저장
            imageDto.setPostId(postId);
            imageDto.setImgName(imgName);
            imageDto.setImgUrl("/images/" + imgName);
            System.out.println("FileUtils - imageDto = " + imageDto);
            imageDtos.add(idx, imageDto);

            // file 생성
            File newFile = new File(savePath + imgName);
            if(!newFile.exists()){
                newFile.mkdirs();
            }
            mf.transferTo(newFile);

            idx++;
        }

        return imageDtos;
    }
    public static ImageDto defaultIamge(int postId){
        ImageDto imageDto = new ImageDto();
        imageDto.setImgUrl("/images/img_digital.png");
        imageDto.setImgName("img_digital.png");
        imageDto.setPostId(postId);

        return imageDto;
    }
}

