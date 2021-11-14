package com.sparta.springcore.domain;

import com.sparta.springcore.dto.ProductRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    @Column(nullable = false)
    private Long userId;

    // 여러개의 상품이 하나의 폴더에 들어갈 수 있고, (Product A, B, C in Folder A)
    // 여러개의 폴더가 한 개의 상품에 들어갈 수 있다. (Folder A, B, C in Product A)
    @ManyToMany
    private List<Folder> folderList;

    public Product(ProductRequestDto requestDto, Long userId) {
        this.userId = userId;
        this.title = requestDto.getTitle();
        this.image = requestDto.getImage();
        this.link = requestDto.getLink();
        this.lprice = requestDto.getLprice();
        this.myprice = 0;
    }

    public void updateMyPrice(int myprice) {
        this.myprice = myprice;
    }

    public void addFolder(Folder folder) {
        this.folderList.add(folder);
    }
}
