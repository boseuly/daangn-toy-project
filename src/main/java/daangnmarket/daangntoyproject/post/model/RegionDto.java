package daangnmarket.daangntoyproject.post.model;

import daangnmarket.daangntoyproject.post.domain.Category;
import daangnmarket.daangntoyproject.post.domain.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegionDto {
    private int regionId;
    private String addressName;

    public RegionDto(int regionId, String addressName){
        this.regionId = regionId;
        this.addressName = addressName;
    }
    public RegionDto(Region region){
        this.regionId = region.getRegionId();
        this.addressName = region.getAddressName();
    }
    public List<RegionDto> changeDto(List<Region> regions){
        int idx = 0;
        List<RegionDto> dto = new ArrayList<RegionDto>();
        for (Region region:regions){
            dto.add(idx,new RegionDto(region));
            idx++;
        }
        return dto;
    }

}
