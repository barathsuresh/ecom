package com.ecom.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecom.user.dto.AddressDTO;
import com.ecom.user.dto.UserRequest;
import com.ecom.user.dto.UserResponse;
import com.ecom.user.model.Address;
import com.ecom.user.model.User;
import com.ecom.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    // private List<User> userList = new ArrayList<>();
    // private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers() {
        log.debug("Fetching all users from database");
        List<UserResponse> users = userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
        log.info("Found {} users", users.size());
        return users;
    }

    public void addUser(UserRequest userRequest) {
        log.debug("Adding new user: {}", userRequest.getEmail());
        User user = new User();
        updateUserFromRequest(user, userRequest);
        User savedUser = userRepository.save(user);
        log.info("User saved successfully with id: {}", savedUser.getId());
    }

    public Optional<UserResponse> fetchUser(String id) {
        log.debug("Fetching user with id: {}", id);
        Optional<UserResponse> userResponse = userRepository.findById(id)
                .map(this::mapToUserResponse);
        if (userResponse.isPresent()) {
            log.debug("User found: {}", id);
        } else {
            log.debug("User not found: {}", id);
        }
        return userResponse;
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        log.debug("Attempting to update user: {}", id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    log.debug("User found, updating fields for: {}", id);
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    log.info("User updated successfully: {}", id);
                    return true;
                }).orElseGet(() -> {
                    log.warn("Cannot update - user not found: {}", id);
                    return false;
                });
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
