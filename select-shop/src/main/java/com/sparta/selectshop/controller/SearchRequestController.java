package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.ItemDto;
import com.sparta.selectshop.models.Product;
import com.sparta.selectshop.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchRequestController {

    private final NaverShopSearch naverShopSearch;

    @GetMapping("/api/search")
    public List<ItemDto> searchProduct(@RequestParam String query) {
        String result = naverShopSearch.search(query);
        return naverShopSearch.fromJSONtoItems(result);
    }

}
