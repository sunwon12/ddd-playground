package potato.server.user.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.server.common.ResponseForm;
import potato.server.security.auth.dto.AuthorityUserDTO;
import potato.server.user.dto.request.CreateAddressRequest;
import potato.server.user.dto.request.UpdateAddressRequest;
import potato.server.user.dto.response.AddressResponse;
import potato.server.user.service.AddressService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/addresses")
@PreAuthorize("isAuthenticated()")
public class AddressAPI {

    private final AddressService addressService;

    @PostMapping
    public ResponseForm<AddressResponse> createAddress(@Valid @RequestBody CreateAddressRequest createAddressRequest,
                                                       @AuthenticationPrincipal AuthorityUserDTO userDTO) {
        return new ResponseForm<>(addressService.createAddress(createAddressRequest, userDTO.getId()));
    }

    @DeleteMapping("/{address-id}")
    public ResponseForm deleteAddress(@PathVariable("address-id") Long addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseForm<>();
    }

    @PutMapping
    public ResponseForm<AddressResponse> updateAddress(@Valid @RequestBody UpdateAddressRequest updateAddressRequest) {
        return new ResponseForm<>(addressService.updateAddress(updateAddressRequest));
    }

    @GetMapping("/all")
    public ResponseForm<List<AddressResponse>> getAllAddresses(@AuthenticationPrincipal AuthorityUserDTO userDTO) {
        return new ResponseForm<>(addressService.getAllAddresses(userDTO.getProviderId()));
    }

    @GetMapping("/default")
    public ResponseForm<AddressResponse> getDefaultAddress(@AuthenticationPrincipal AuthorityUserDTO userDTO) {
        return new ResponseForm<>(addressService.getDefaultAddress(userDTO.getProviderId()));
    }
}
