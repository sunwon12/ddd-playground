package potato.server.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 박건휘
 * @since: 2023-11-13
 */
@Getter
@NoArgsConstructor
public class ProductCreateRequest {

    @NotBlank
    private String title;

    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotNull
    private Integer stock;
    private List<Long> productImageIds = new ArrayList<>();
}