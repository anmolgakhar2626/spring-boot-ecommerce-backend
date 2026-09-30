package com.Ecommerce.Sp_ecomerce.Service;

import com.Ecommerce.Sp_ecomerce.PayLoad.ProductDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.ProductResponse;
import com.Ecommerce.Sp_ecomerce.model.Category;
import com.Ecommerce.Sp_ecomerce.model.Product;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllproduct();

    ProductResponse getProductById(Long categoryId);

    ProductResponse getProductByKeyword(String keyword);

    ProductDTO UpadateProductById(ProductDTO productDTO, Long productId);

    ProductDTO deleteById(Long productId);
}

