package potato.server.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import potato.server.product.domain.Product;

import java.math.BigDecimal;

/**
 * @author: 박건휘
 * @since: 2023-11-13
 */
@Builder
@Getter
@Data
public class ProductResponse {
    private String title;
    private BigDecimal price;
    private String description;
    private Integer hit;
    private Integer stock;
    private Integer version;

    @QueryProjection
    public ProductResponse(String title, BigDecimal price, String description, Integer stock, Integer version, Integer hit) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.version = version;
        this.hit = hit;
    }

    public static ProductResponse of(String title, BigDecimal price, String description, Integer stock, Integer version, Integer hit) {
        return ProductResponse.builder()
                .title(title)
                .price(price)
                .description(description)
                .stock(stock)
                .version(version)
                .hit(hit)
                .build();
    }

    public static ProductResponse of(Product product) {
        return new ProductResponse(
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getVersion(),
                product.getHit());
    }
}