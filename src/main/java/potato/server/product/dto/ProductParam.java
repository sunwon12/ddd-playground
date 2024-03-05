package potato.server.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.product.domain.Product;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductParam {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer hit;

    public static ProductParam from(Product entity) {
        return new ProductParam(entity.getId(), entity.getTitle(), entity.getPrice(), entity.getDescription(), entity.getHit());
    }
}
