package org.example.royaleanalytics.service;

import lombok.RequiredArgsConstructor;
import org.example.royaleanalytics.dto.request.RegistrationFormDTO;
import org.example.royaleanalytics.entity.User;
import org.example.royaleanalytics.exception.RegistrationException;
import org.example.royaleanalytics.mapper.UserMapper;
import org.example.royaleanalytics.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // TODO добавить проверку валидности введенного playerTag
    public void register(RegistrationFormDTO registrationFormDTO) {
        Map<String, String> errors = new HashMap<>();

        if (userRepository.existsByUsername(registrationFormDTO.username())) {
            errors.put("username", "Username is already taken");
        }
        if (userRepository.existsByPlayerTag(registrationFormDTO.playerTag())) {
            errors.put("playerTag", "Player tag is already taken");
        }
        if (!errors.isEmpty()) {
            throw new RegistrationException(errors);
        }
        User newUser = userMapper.toUser(registrationFormDTO);
        newUser.setPassword_hash(passwordEncoder.encode(registrationFormDTO.password()));
        userRepository.save(newUser);
    }
}
