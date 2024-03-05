package potato.server.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;

/**
 * @author 박건휘
 * @since 2023-08-09
 */
@Entity
@Getter
@NoArgsConstructor
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Builder
    private Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
    }

    public static Category createCategory(String name){
        return Category.builder()
                .name(name)
                .build();
    }

    public static Category createSubCategory(String name, Category category){
        return Category.builder()
                .name(name)
                .parent(category)
                .build();
    }
}