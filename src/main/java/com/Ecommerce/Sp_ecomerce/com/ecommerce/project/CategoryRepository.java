package com.Ecommerce.Sp_ecomerce.com.ecommerce.project;


import com.Ecommerce.Sp_ecomerce.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(@NotBlank(message = "Bhai name toh dal de") String category);
}