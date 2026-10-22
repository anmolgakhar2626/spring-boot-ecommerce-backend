package com.Ecommerce.Sp_ecomerce.Service;

import com.Ecommerce.Sp_ecomerce.PayLoad.ProductDTO;
import com.Ecommerce.Sp_ecomerce.PayLoad.ProductResponse;
import com.Ecommerce.Sp_ecomerce.Repostiory.CategoryRepository;
import com.Ecommerce.Sp_ecomerce.Repostiory.Product_repostiory;
import com.Ecommerce.Sp_ecomerce.exceptions.ResourceNotFoundException;
import com.Ecommerce.Sp_ecomerce.model.Category;
import com.Ecommerce.Sp_ecomerce.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Product_repostiory productRepostiory;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "CategoryId",
                                categoryId
                        )
                );
Product product=modelMapper.map(productDTO,Product.class);
        product.setCategory(category);
        product.setImage("default.png");

        double specialPrice = product.getPrice()
                - ((product.getDiscount() * 0.01) * product.getPrice());

        product.setSpecialPrice(specialPrice);

        Product savedProduct = productRepostiory.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllproduct() {
       List<Product> products= productRepostiory.findAll();
       List<ProductDTO>productDTOS=products.stream()
               .map(product -> modelMapper.map(product,ProductDTO.class)).toList();
       ProductResponse productResponse=new ProductResponse();
       productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category",
                                "CategoryId",
                                categoryId
                        )
                );
        List<Product>products=productRepostiory.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO>productDTOS=products.stream().map(product ->
                modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductByKeyword(String keyword) {
        List<Product>products=productRepostiory.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<ProductDTO>productDTOS=products.stream().map(product ->
                modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO UpadateProductById(ProductDTO productDTO, Long productId) {
        Product productFromDb=productRepostiory.findById(productId) .orElseThrow(
                ()->new ResourceNotFoundException("Product","ProductId",productId)
        );
        Product product=modelMapper.map(productDTO,Product.class);

        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct=productRepostiory.save(productFromDb);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteById(Long productId) {
        Product deleteProduct=productRepostiory.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("Product","ProductId",productId)
        );
       productRepostiory.delete(deleteProduct);
       return modelMapper.map(deleteProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {

      //Get the product from DB
        Product productFromDb=productRepostiory.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        //Upload image to server
//
//        Get filee name of uploaded imag
        String path="images/";
        String fileName=uploadImage(path,image);
//       Updating the new file name to product
        productFromDb.setImage(fileName);
//        save the updated product
        Product updateProduct=productRepostiory.save(productFromDb);
//      return DTO after mapping product to DTO
        return modelMapper.map(updateProduct,ProductDTO.class);




    }

    private String uploadImage(String path, MultipartFile file) throws IOException {
        // Get the file name of current file or orignal file
        String originalFileName=file.getOriginalFilename();
        // Generate a unique file name
      String randomId=UUID.randomUUID().toString();
//mat.jpg-->1234-->1234.jpg
      String fileName=randomId.concat(originalFileName.
        substring((originalFileName.lastIndexOf('.'))));
    String filePath=path+ File.separator+fileName;

//Check if path exist and create
        File folder=new File(path);
        if(!folder.exists())
            folder.mkdir();
        // Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //returning file name
        return fileName;
    }
}
