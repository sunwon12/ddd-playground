package potato.server.product.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.product.domain.Product;
import potato.server.product.domain.ProductImage;
import potato.server.product.dto.request.ProductCreateRequest;
import potato.server.product.dto.request.ProductUpdateRequest;
import potato.server.product.dto.response.ProductResponse;
import potato.server.product.repository.ProductImageRepository;
import potato.server.product.repository.ProductRepository;

import java.util.List;

/**
 * @author: 박건휘
 * @since: 2023-10-11
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public void createProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .price(request.getPrice())
                .title(request.getTitle())
                .description(request.getDescription())
                .stock(request.getStock())
                .build();
        productRepository.save(product);

        mapProductToProductImage(request.getProductImageIds(), product);
    }

    private void mapProductToProductImage(List<Long> productImageIds, Product product) {
        for (Long productImageId : productImageIds) {
            ProductImage productImage = productImageRepository.findById(productImageId)
                    .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_IMAGE_NOT_FOUND));
            productImage.setProduct(product);
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


    @SneakyThrows
    public ProductResponse getProduct(Long productId) {
        try {
            final Product product = findProductByProductId(productId);
            product.addHit();
            return ProductResponse.builder()
                    .title(product.getTitle())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .build();
        } catch (ObjectOptimisticLockingFailureException e) {
            Thread.sleep(20);
        }

        return getProduct(productId);
    }

    public Product findProductByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productUpdateRequest.getProductId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));
        product.updateProduct(productUpdateRequest);
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAllProducts();
    }
}