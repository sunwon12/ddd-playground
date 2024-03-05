package potato.server.order.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potato.server.common.ResponseForm;
import potato.server.order.dto.request.OrderCreateRequest;
import potato.server.order.dto.response.OrderCreateResponse;
import potato.server.order.dto.response.OrderResponse;
import potato.server.order.service.OrderReadService;
import potato.server.order.service.OrderWriteService;
import potato.server.security.auth.dto.AuthorityUserDTO;

/**
 * @author: 허석문
 * @since: 2023-11-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderApi {

    private final OrderWriteService orderWriteService;
    private final OrderReadService orderReadService;

    @GetMapping("/{order-id}")
    public ResponseForm<OrderResponse> findOrder(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @PathVariable("order-id") Long orderId

    ) {
        return new ResponseForm<>(orderReadService.findOrder(userDTO.getId(), orderId));
    }

    @PostMapping
    public ResponseForm<OrderCreateResponse> createOrderWithCart(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid @RequestBody OrderCreateRequest orderCreateRequest
    ) {

        Long order = orderWriteService.createOrderWithCart(userDTO.getId(), orderCreateRequest);

        return new ResponseForm<>(
                new OrderCreateResponse(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(order)
                        .toUri())
        );
    }

    @DeleteMapping("/{order-id}")
    public ResponseForm<Void> deleteOrder(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @PathVariable("order-id") Long orderId
    ) {
        orderWriteService.deleteOrder(userDTO.getId(), orderId);
        return new ResponseForm<>();
    }
}
