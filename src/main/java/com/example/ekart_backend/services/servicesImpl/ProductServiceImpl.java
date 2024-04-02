package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.entities.Category;
import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.payloads.CategoryDto;
import com.example.ekart_backend.payloads.ImageResponse;
import com.example.ekart_backend.payloads.ProductDto;
import com.example.ekart_backend.repositories.CategoryRepo;
import com.example.ekart_backend.repositories.ProductRepo;
import com.example.ekart_backend.services.serviceInterface.FileUploadService;
import com.example.ekart_backend.services.serviceInterface.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDto createProduct(ProductDto productDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).get();
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        Product savedProduct = this.productRepo.save(product);
//
        System.out.println(this.modelMapper.map(savedProduct.getCategory(), CategoryDto.class));
        System.out.println(this.modelMapper.map(savedProduct, ProductDto.class));
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepo.findById(productId).get();
        product.setProductName(productDto.getProductName());
        product.setProductCost(productDto.getProductCost());
        product.setProductDiscount(productDto.getProductDiscount());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductQuantity(productDto.getProductQuantity());
        Product savedProduct = this.productRepo.save(product);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = this.productRepo.findById(productId).get();
        this.productRepo.delete(product);
    }

    @Override
    public void deleteProduct(ProductDto productDto) {
        this.productRepo.delete(this.modelMapper.map(productDto, Product.class));
    }

    @Override
    public ProductDto getSingleProduct(Integer userId) {
        Product product = this.productRepo.findById(userId).get();
        List<ImageResponse> response = new ArrayList<>();
        product.getProductImage().forEach((image)->{
            try {
                InputStream in = this.fileUploadService.getImage(path, image);
                response.add(new ImageResponse(image, in.readAllBytes()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ProductDto finalProduct = this.modelMapper.map(product, ProductDto.class);
        finalProduct.setImages(response);
        return finalProduct;
    }

    @Override
    public List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String keyword) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> allProducts = new ArrayList<>();
        if(keyword.equals("")) {
            allProducts = this.productRepo.findAll(pageable).toList();
        }
        else{
            allProducts = this.productRepo.findAllByProductNameContainingIgnoreCase(keyword, pageable).getContent();
        }
        List<ProductDto> dtoList = allProducts.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        for(ProductDto products : dtoList){
            List<ImageResponse> imagesList = new ArrayList<>();
            products.getProductImage().forEach((image)->{
                try {
                    InputStream in = this.fileUploadService.getImage(path, image);
                    imagesList.add(new ImageResponse(image, in.readAllBytes()));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            });
            products.setImages(imagesList);
        }

        return dtoList;
    }

    @Override
    public String uploadProductImage(MultipartFile file, Integer productId) throws IOException {
        Product product = this.productRepo.findById(productId).get();
        String imageName = this.fileUploadService.uploadImage(path, file);
        product.getProductImage().add(imageName);
        this.productRepo.save(product);
        System.out.println("The path name is  : " + path);
        return imageName;
    }

    @Override
    public List<ImageResponse> getAllImages(Integer productId) {
        List<String> allImages = this.productRepo.getAllImagesById(productId);
        List<ImageResponse> output = new ArrayList<>();
        for(String imageName : allImages){
            ImageResponse img = new ImageResponse();
            img.setImageName(imageName);
            try {
                InputStream in = this.fileUploadService.getImage(path, imageName);
                img.setContent(in.readAllBytes());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            output.add(img);
        }
        return output;
    }

    @Override
    public ProductDto createProductWithImages(Integer categoryId, ProductDto productDto, MultipartFile[] files) throws IOException {
        Category category = this.categoryRepo.findById(categoryId).get();
        Product product = this.modelMapper.map(productDto, Product.class);

        product.setCategory(category);

        Product savedProduct = this.productRepo.save(product);

        for(MultipartFile file : files){
            String imageName = this.fileUploadService.uploadImage(path, file);
            product.getProductImage().add(imageName);
        }

        Product updatedProduct = this.productRepo.save(product);
        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }

//    @Override
//    public List<ProductDto> getAllProductsByKeyword(String keyword, Integer pageIndex, Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageIndex, pageSize);
//        List<Product> allProductsByKeyword = this.productRepo.findProductByProductNameContainingIgnoreCase(keyword, pageable);
//        return null;
//    }

    public Integer countProduct() {
        List<Product> allProducts = this.productRepo.findAll();
        return allProducts.size();
    }
}
