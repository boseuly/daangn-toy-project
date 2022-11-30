package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoryDto {
    private int categoryId;
    private String categoryName;

    public CategoryDto(Category category){
        this.categoryId = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
    public List<CategoryDto> changeDto(List<Category> categorys){
        int idx = 0;
        List<CategoryDto> dto = new ArrayList<CategoryDto>();
        for (Category category:categorys){
            dto.add(idx,new CategoryDto(category));
            idx++;
        }
        return dto;
    }
}
