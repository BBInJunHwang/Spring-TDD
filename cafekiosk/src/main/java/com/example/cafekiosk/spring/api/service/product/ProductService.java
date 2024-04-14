package com.example.cafekiosk.spring.api.service.product;

import com.example.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.example.cafekiosk.spring.api.service.product.reponse.ProductResponse;
import com.example.cafekiosk.spring.domain.product.Product;
import com.example.cafekiosk.spring.domain.product.ProductRepository;
import com.example.cafekiosk.spring.domain.product.ProductSellingStatus;
import com.example.cafekiosk.spring.domain.product.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        // productNumber 부여
        // 001, 002.. 고유 3자리 부여
        // DB에서 마지막 저장된 Product의 상품번호 가져와서 +1
        // ex) 009 -> - 010
        String nextProductNumber = createNextProductNumber();
        return ProductResponse.builder()
                .productNumber(nextProductNumber)
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProduct();
        if(latestProductNumber == null){
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        // 자동으로 패딩 처리 해줌
        return String.format("%03d", nextProductNumberInt);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return products.stream().map(ProductResponse::of).toList();
    }



}
