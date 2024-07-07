package com.srivath.product.services;

import com.srivath.product.DTOs.FakeStoreProductDTO;
import com.srivath.product.DTOs.ProductDTO;
import com.srivath.product.exceptions.ProductNotFoundException;
import com.srivath.product.models.Category;
import com.srivath.product.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{


    private RestTemplate restTemplate;


    @Autowired
    FakeStoreProductService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDTO [] dtolist = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDTO[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO dto : dtolist)
        {
            products.add(convertFakeStoreProductDTOToProduct(dto));
        }
        return products;
    }

    @Override
    public Product getProductbyId(long id) throws ProductNotFoundException {
        FakeStoreProductDTO dto = restTemplate.getForObject("https://fakestoreapi.com/products/"+ id, FakeStoreProductDTO.class);
        if (dto == null)
            throw new ProductNotFoundException("Product with id " + id + " is not found!!!");
        return convertFakeStoreProductDTOToProduct(dto);
    }

    public Product convertFakeStoreProductDTOToProduct(FakeStoreProductDTO dto)
    {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());
        product.setDescription(dto.getDescription());

        Category category = new Category();
        category.setName(dto.getCategory());
        product.setCategory(category);

        return product;
    }

    public FakeStoreProductDTO convertProductDTOToFakeStoreProductDTO( ProductDTO productDTO)
    {
        FakeStoreProductDTO dto = new FakeStoreProductDTO();
        //dto.setId(productDTO.getId());
        dto.setTitle(productDTO.getName());
        dto.setPrice(productDTO.getPrice());
        dto.setImage(productDTO.getImage());
        dto.setDescription(productDTO.getDescription());
        dto.setCategory(productDTO.getCategory());

        return dto;
    }

    @Override
    public List<Product> getAllProductsInCategory(String category) {
        FakeStoreProductDTO[] fakeStoreProductDTOs = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+ category, FakeStoreProductDTO[].class);

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDTO fakeStoreProductDTO: fakeStoreProductDTOs )
        {
            products.add(convertFakeStoreProductDTOToProduct(fakeStoreProductDTO));
        }

        return products;
    }

    @Override
    public Product addProduct(ProductDTO productDTO) {
        FakeStoreProductDTO dto = convertProductDTOToFakeStoreProductDTO(productDTO);
        FakeStoreProductDTO returnedDto = restTemplate.postForObject("https://fakestoreapi.com/products" , dto , FakeStoreProductDTO.class);
        return convertFakeStoreProductDTOToProduct(returnedDto);
    }


    //PATCH is not supported by Rest Template. Use PUT instead. Else GEt whole object and replace required fields and then PUT.
    // This is a limitation of RestTemplate. RestTemplate does not support PATCH. Use WebClient instead.
    // WebClient is a part of Spring WebFlux. It is a non-blocking, reactive client to perform HTTP requests, a part of Spring WebFlux.
//    @Override
//    public Product updateProduct(long id, ProductPriceDTO productPriceDTO) {
//        ProductPriceDTO dto = restTemplate.patchForObject("https://fakestoreapi.com/products/"+ id,  productPriceDTO, ProductPriceDTO.class);
//        Product product = new Product();
//        product.setId(dto.getId());
//        product.setPrice(dto.getPrice());
//        return product;
//    }


    @Override
    public Product replaceProduct(long id, ProductDTO productDTO) {
        // We can simply use put method but we want to return the Product as well
        //this.restTemplate.put("https://fakestoreapi.com/products/"+id, productDTO);
        String url = "https://fakestoreapi.com/products/"+id;
        FakeStoreProductDTO fakeStoreProductDTO = convertProductDTOToFakeStoreProductDTO(productDTO);
        fakeStoreProductDTO.setId(id);
        RequestCallback requestCallback = this.restTemplate.httpEntityCallback(fakeStoreProductDTO);
        HttpMessageConverterExtractor<FakeStoreProductDTO> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDTO.class, this.restTemplate.getMessageConverters());
        FakeStoreProductDTO  fakeStoreProductDTO1 = this.restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor);
        if (fakeStoreProductDTO != null)
            return convertFakeStoreProductDTOToProduct(fakeStoreProductDTO1);

        return null;
    }



    @Override
    public boolean removeProduct(long id) {

        this.restTemplate.delete("https://fakestoreapi.com/products/"+id);
        return true;
    }
}
