package com.sparta.springcore.util;

import com.sparta.springcore.domain.Product;
import com.sparta.springcore.dto.ItemDto;
import com.sparta.springcore.repository.ProductRepository;
import com.sparta.springcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;

    // 초 분 시 일 월 주
    @Scheduled(cron = "0 0 1 * * *")
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");

        List<Product> productList = productRepository.findAll();
        for(int i = 0; i < productList.size(); i++) {
            TimeUnit.SECONDS.sleep(1); // 1초마다 한 상품 조회

            Product p = productList.get(i);
            String title = p.getTitle();

            String resultString = naverShopSearch.search(title);
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
            ItemDto itemDto = itemDtoList.get(0); // '타이틀'로 검색한 아이템 목록들 중 가장 처음 나온 아이템이 연관성 가장 높기 때문

            Long id = p.getId();
            productService.updateBySearch(id, itemDto);
        }
    }
}
