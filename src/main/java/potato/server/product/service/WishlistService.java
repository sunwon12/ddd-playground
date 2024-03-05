package potato.server.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import potato.server.common.CustomException;
import potato.server.common.ResponseForm;
import potato.server.common.ResultCode;
import potato.server.product.domain.Product;
import potato.server.product.domain.Wishlist;
import potato.server.product.repository.WishlistRepository;
import potato.server.security.auth.dto.AuthorityUserDTO;
import potato.server.user.domain.User;
import potato.server.user.service.UserService;
import java.util.List;

/**
 * @author: 박건휘
 * @since: 2023-11-25
 */
@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductService productService;
    private final UserService userService;

    public void addWishlist(Long productId, AuthorityUserDTO userDTO) {
        final Product product = productService.findProductByProductId(productId);
        final User user = userService.getUserById(userDTO.getId());
        wishlistRepository.save(new Wishlist(product, user));
    }

    public ResponseForm<List<?>> getWishlistByUserId(Long userId) {
        final List<Wishlist> allWishlist = wishlistRepository.findAllByUserId(userId);
        return new ResponseForm<>(allWishlist);
    }

    public void deleteWishlist(Long wishlistId, AuthorityUserDTO userDTO) {
        wishlistRepository.findById(wishlistId)
                .ifPresentOrElse(wishlistRepository::delete,
                        () -> new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ResultCode.WISHLIST_NOT_FOUND));
    }
}