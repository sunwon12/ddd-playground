package potato.server.product.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.file.FileCreateRequest;
import potato.server.product.dto.request.ProductImageCreateRequest;
import potato.server.product.dto.request.ProductImageDeleteRequest;
import potato.server.product.dto.response.ProductImageCreateResponse;
import potato.server.product.service.ProductImageService;

/**
 * @author 정순원
 * @since 2024-01-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/image")
public class ProductImageApi {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseForm<ProductImageCreateResponse> createProductImage(@RequestBody ProductImageCreateRequest request) {
        return new ResponseForm<>(productImageService.createProductImage(request));
    }

    @DeleteMapping
    public ResponseForm deleteProductImage(@RequestBody ProductImageDeleteRequest productImageDeleteRequest) {
        productImageService.deleteProductImage(productImageDeleteRequest);
        return new ResponseForm<>();
    }
}
