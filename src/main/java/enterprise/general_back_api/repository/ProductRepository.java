package enterprise.general_back_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import enterprise.general_back_api.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
