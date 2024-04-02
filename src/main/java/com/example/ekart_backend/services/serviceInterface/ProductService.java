package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.payloads.ImageResponse;
import com.example.ekart_backend.payloads.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public ProductDto createProduct(ProductDto productDto, Integer categoryId);
    public ProductDto updateProduct(ProductDto productDto, Integer productId);
    public void deleteProduct(Integer productId);
    public void deleteProduct(ProductDto productDto);
    public ProductDto getSingleProduct(Integer userId);
    public List<ProductDto> getAllProducts(Integer pageNumber, Integer pageSize, String keyword);

    public String uploadProductImage(MultipartFile file, Integer productId) throws IOException;

    public List<ImageResponse> getAllImages(Integer productId);

    ProductDto createProductWithImages(Integer categoryId, ProductDto productDto, MultipartFile[] files) throws IOException;

}
