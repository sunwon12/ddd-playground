package potato.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.user.domain.Address;
import potato.server.user.domain.User;
import potato.server.user.dto.request.CreateAddressRequest;
import potato.server.user.dto.request.UpdateAddressRequest;
import potato.server.user.dto.response.AddressResponse;
import potato.server.user.repository.AddressRepository;
import potato.server.user.repository.UserRepository;
import potato.server.user.spec.DefaultAddr;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional
    public AddressResponse createAddress(CreateAddressRequest createAddressRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
        Address address = Address.builder()
                .user(user)
                .addr(createAddressRequest.getAddr())
                .addrName(createAddressRequest.getAddrName())
                .recipient(createAddressRequest.getRecipient())
                .addZipcode(createAddressRequest.getAddZipcode())
                .userPhone(createAddressRequest.getUserPhone())
                .addrRequest(createAddressRequest.getAddrRequest())
                .addrDetail(createAddressRequest.getAddrDetail())
                .defaultAddr(DefaultAddr.N)
                .build();

        if (createAddressRequest.getDefaultAddr() == DefaultAddr.Y)
            changeDefaultAddress(address, user);
        addressRepository.save(address);
        return AddressResponse.of(address);
    }

    @Transactional
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    @Transactional
    public AddressResponse updateAddress(UpdateAddressRequest updateAddressRequest) {
        Address address = addressRepository.findById(updateAddressRequest.getAddressId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ADDRESS_NOT_FOUND));
        address.updateAddress(updateAddressRequest);
        if (updateAddressRequest.getDefaultAddr() == DefaultAddr.Y)
            changeDefaultAddress(address, address.getUser());
        return AddressResponse.of(address);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAllAddresses(String providerId) {
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
        return addressRepository.findAllByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public AddressResponse getDefaultAddress(String providerId) {
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
        Address address = addressRepository.findByUserIdAndDefaultAddr(user.getId(), DefaultAddr.Y).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ADDRESS_NOT_FOUND));
        return AddressResponse.of(address);
    }

    /**
     * 기본 주소지 변경
     */
    private void changeDefaultAddress(Address newAddress, User user) {
        Boolean hasDefaultAddress = hasDefaultAddress(user);
        if (Boolean.TRUE.equals(hasDefaultAddress)) {
            Address oldDefaultAddress = addressRepository.findByUserIdAndDefaultAddr(newAddress.getUser().getId(), DefaultAddr.Y).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ADDRESS_NOT_FOUND));
            oldDefaultAddress.updateDefaultAddr(DefaultAddr.N);
        }
        newAddress.updateDefaultAddr(DefaultAddr.Y);
    }

    private Boolean hasDefaultAddress(User user) {
        return addressRepository.existsByUserAndDefaultAddr(user, DefaultAddr.Y);
    }
}
