package tddpractice.tddcafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where selling_status in ('SELLING', 'HOLD');
     */
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);


    /**
     * select *
     * from product
     * where product_number in ('001', '002');
     */
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
