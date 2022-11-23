package daangnmarket.daangntoyproject.board.domain;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@ToString
public class Region {
    @Id
    private int regionId;

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "region_1depth_name")
    private String firstDepth;
    @Column(name = "region_2depth_name")
    private String secDepth;
    @Column(name = "region_3depth_name")
    private String thirdDepth;

    @Column(name = "region_latitude")
    private double latitude;
    @Column(name = "region_longitude")
    private double longitude;



}
