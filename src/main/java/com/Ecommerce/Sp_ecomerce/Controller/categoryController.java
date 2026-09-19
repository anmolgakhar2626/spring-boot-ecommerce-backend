package com.Ecommerce.Sp_ecomerce.Controller;

import com.Ecommerce.Sp_ecomerce.Configur.AppConstant;
import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.CategoryResponse;
import com.Ecommerce.Sp_ecomerce.Service.Categoryservice;
import com.Ecommerce.Sp_ecomerce.Service.serviceCategory;
import com.Ecommerce.Sp_ecomerce.model.Category;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class categoryController {

    private Categoryservice categoryservice;

    public categoryController(Categoryservice categoryservice, serviceCategory serviceCategory) {
        this.categoryservice = categoryservice;

    }
    @GetMapping("/echo")
public ResponseEntity<String>echoMessage(@RequestParam(name = "Message",defaultValue = "Kive a bro",required = false)  String Message){
        return new ResponseEntity<>("Echoed Message "+Message,HttpStatus.OK);
}
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(name = "PageNo", defaultValue = AppConstant.PAGE_NUMBER) Integer PageNo,
            @RequestParam(name = "PageSize", defaultValue = AppConstant.PAGE_SIZE) Integer PageSize,
            @RequestParam (name="sortBy",defaultValue = AppConstant.SORT_CATEGORIES_BY) String SortBY,
            @RequestParam(name="sortOrder",defaultValue = AppConstant.SORT_BY_ORDER) String SortByorder) {

               CategoryResponse categoryResponse =
                categoryservice.getCategories(PageNo, PageSize,SortBY,SortByorder);

        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    //@RequestMapping(value="/api/public/categories",method = RequestMethod.POST)

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategories(@Valid @RequestBody CategoryDTO categoryDTO) {
       CategoryDTO saveCategory= categoryservice.createCategories(categoryDTO);
        return new ResponseEntity<>(saveCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deletecategory(@PathVariable Long categoryId) {

     CategoryDTO deletedCategory = categoryservice.deletecategory(categoryId);
        return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updatecategories(@RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId) {

        CategoryDTO saveCategory=  categoryservice.updatecategories(categoryDTO, categoryId);
        return new ResponseEntity<>(saveCategory, HttpStatus.OK);

    }

}
