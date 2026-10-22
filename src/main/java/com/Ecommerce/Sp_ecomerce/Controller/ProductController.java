package com.Ecommerce.Sp_ecomerce.Controller;

import com.Ecommerce.Sp_ecomerce.PayLoad.ProductDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.ProductResponse;
import com.Ecommerce.Sp_ecomerce.Service.ProductService;
import com.Ecommerce.Sp_ecomerce.model.Category;
import com.Ecommerce.Sp_ecomerce.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


@GetMapping("/public/products")
    public ResponseEntity<ProductResponse>getALLproducts(){
        ProductResponse productResponse=productService.getAllproduct();

         return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

@GetMapping("/public/Categories/{categoryId}/products")
    public ResponseEntity<ProductResponse>getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse=productService.getProductById(categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
@GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse>getProductByKeywoard(@PathVariable String keyword){
        ProductResponse productResponse=productService.getProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);
    }
    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @RequestBody ProductDTO productDTO,
            @PathVariable("categoryId") Long categoryId) {

        ProductDTO savedproductDTO = productService.addProduct(productDTO, categoryId);

        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
    }

@PutMapping("/admin/products/{ProductId}")
    public ResponseEntity<ProductDTO>UpdateProductById(@RequestBody ProductDTO productDTO,@PathVariable Long ProductId){
        ProductDTO UpdatedProduct=productService.UpadateProductById(productDTO,ProductId);
        return new ResponseEntity<>(UpdatedProduct,HttpStatus.OK);
    }
    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>DeleteProduct(@PathVariable Long productId){
        ProductDTO deleteProduct=productService.deleteById(productId);
        return new ResponseEntity<>(deleteProduct,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public  ResponseEntity<ProductDTO>updateProductImage(@PathVariable
                                                             Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updateProduct=productService.updateProductImage(productId,image);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }
}
