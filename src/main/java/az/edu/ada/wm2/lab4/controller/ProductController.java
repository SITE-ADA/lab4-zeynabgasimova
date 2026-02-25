package az.edu.ada.wm2.lab4.controller;

import az.edu.ada.wm2.lab4.model.Product;
import az.edu.ada.wm2.lab4.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // Constructor injection
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products -> 201 CREATED
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product created = productService.createProduct(product);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/products -> 200 OK
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/products/{id} -> 200 OK or 404 NOT FOUND
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PUT /api/products/{id} -> 200 OK or 404 NOT FOUND
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, product));
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/products/{id} -> 204 NO CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET /api/products/filter/expiring?date=YYYY-MM-DD -> 200 OK
    @GetMapping("/filter/expiring")
    public ResponseEntity<List<Product>> getProductsExpiringBefore(@RequestParam LocalDate date) {
        try {
            return ResponseEntity.ok(productService.getProductsExpiringBefore(date));
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // GET /api/products/filter/price?min=10&max=100 -> 200 OK
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam BigDecimal min,
                                                                 @RequestParam BigDecimal max) {
        try {
            return ResponseEntity.ok(productService.getProductsByPriceRange(min, max));
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}