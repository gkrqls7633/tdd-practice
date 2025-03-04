package tddpractice.tddcafekiosk.spring.domain.product;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
//@SpringBootTest
//@DataJpaTest
class ProductRepositoryTest {

//    @Autowired
//    private ProductRepository productRepository;

    @Test
    void testMethod() {
        assertThat(1).isEqualTo(1);
    }

//
}
