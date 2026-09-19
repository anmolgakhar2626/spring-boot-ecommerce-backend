package com.Ecommerce.Sp_ecomerce.Service;

import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryResponse;
import com.Ecommerce.Sp_ecomerce.com.ecommerce.project.CategoryRepository;
import com.Ecommerce.Sp_ecomerce.exceptions.ApiException;
import com.Ecommerce.Sp_ecomerce.exceptions.ResourceNotFoundException;
import com.Ecommerce.Sp_ecomerce.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class Categoryservice implements serviceCategory {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponse getCategories(Integer PageNo,Integer PageSize,String sortBy,String sortByorder) {
        Sort sortByanOrder=sortByorder.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable Pagedetails= PageRequest.of(PageNo,PageSize,sortByanOrder);
        Page<Category>categoryPage=categoryRepository.findAll(Pagedetails);
        List<Category> getCategoryAll = categoryPage.getContent();

        List<CategoryDTO> categoryDTOList = getCategoryAll.stream().map(category -> modelMapper.
                map(category, CategoryDTO.class)).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElement(categoryPage.getTotalElements());
        categoryResponse.setTotalpages((long) categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        if (getCategoryAll.isEmpty()) throw new ApiException("No category is created now");
        return categoryResponse;

    }

    @Override
    public CategoryDTO createCategories(CategoryDTO categoryDTO) {
        Category category=modelMapper.map(categoryDTO,Category.class);
        Category savecategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if(savecategory!=null)throw new ApiException("category with the name "+category.getCategoryName()+" already Exists");
       Category saveCategory= categoryRepository.save(category);
       return modelMapper.map(saveCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO deletecategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category","categoryId",categoryId)
                );

          categoryRepository.delete(category);

        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updatecategories(CategoryDTO categoryDTO, Long categoryId) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "categoryId", categoryId)
                );
        existingCategory.setCategoryName(categoryDTO.getCategoryName());

        Category saveCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(saveCategory, CategoryDTO.class);
    }
}