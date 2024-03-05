package potato.server.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.file.FileCreateRequest;
import potato.server.file.FileService;
import potato.server.product.domain.Product;
import potato.server.product.domain.ProductImage;
import potato.server.product.dto.request.ProductImageCreateRequest;
import potato.server.product.dto.request.ProductImageDeleteRequest;
import potato.server.product.dto.response.ProductImageCreateResponse;
import potato.server.product.repository.ProductImageRepository;
import potato.server.product.repository.ProductRepository;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final FileService fileService;
    private final ProductRepository productRepository;

    @Transactional
    public ProductImageCreateResponse createProductImage(ProductImageCreateRequest request) {
        Product product = null;
        String preSignedUrl = fileService.getPreSignedUrl(request.getFileName());

        if(request.getProductId() != null)
            product = productRepository.getReferenceById(request.getProductId());

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .link(preSignedUrl)
                .build();
        productImageRepository.save(productImage);

        return new ProductImageCreateResponse(preSignedUrl, productImage.getId());
    }

    @Transactional
    public void deleteProductImage(ProductImageDeleteRequest productImageDeleteRequest) {
        fileService.deleteFile(productImageDeleteRequest.getFilePath());

        productImageRepository.deleteById(productImageDeleteRequest.getProductImageId());
    }
}
