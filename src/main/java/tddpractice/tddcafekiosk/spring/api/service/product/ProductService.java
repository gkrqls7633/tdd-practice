package tddpractice.tddcafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tddpractice.tddcafekiosk.spring.api.service.product.response.ProductResponse;
import tddpractice.tddcafekiosk.spring.domain.product.Product;
import tddpractice.tddcafekiosk.spring.domain.product.ProductRepository;
import tddpractice.tddcafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

}
