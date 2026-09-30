package com.Ecommerce.Sp_ecomerce.Service;

import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryResponse;

public interface
serviceCategory {
    CategoryResponse getCategories(Integer PageNo,Integer PageSize,String sortBy,String sortByorder);
    CategoryDTO createCategories(CategoryDTO categoryDTO);

    CategoryDTO deletecategory(Long categoryId);

    CategoryDTO updatecategories(CategoryDTO categoryDTO, Long categoryId);
}
