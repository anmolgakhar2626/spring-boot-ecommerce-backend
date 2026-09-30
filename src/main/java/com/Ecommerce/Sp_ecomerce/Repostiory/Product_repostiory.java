package com.Ecommerce.Sp_ecomerce.Repostiory;

import com.Ecommerce.Sp_ecomerce.model.Category;
import com.Ecommerce.Sp_ecomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Product_repostiory extends JpaRepository<Product,Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category categoryId);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
