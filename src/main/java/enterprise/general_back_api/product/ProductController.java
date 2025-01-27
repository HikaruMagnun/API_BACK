package enterprise.general_back_api.product;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/saveProduct")
    public Product saveProduct(@Valid @RequestBody(required = true) Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam(required = true) Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/getProductsPaged")
    public Page<Product> getProductsPaged(
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "La página debe ser 0 o mayor") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "El tamaño debe ser mayor o igual a 1") @Max(value = 20, message = "Mayor a 20") int size) {

        return productService.getAllProductsPagenated(page, size);
    }

    @PutMapping("/updateProduct")
    public Product updateProduct(@Valid @RequestBody(required = true) Product product) {
        return productService.updateProduct(product.getID(), product);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestParam Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product ID not found: " + ex.getMessage()); // 404 Not Found
        }
    }

}
