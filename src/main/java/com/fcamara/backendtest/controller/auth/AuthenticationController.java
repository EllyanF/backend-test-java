package com.fcamara.backendtest.controller.auth;

import com.fcamara.backendtest.domain.User;
import com.fcamara.backendtest.dto.user.RegisterRequestDTO;
import com.fcamara.backendtest.repository.UserRepository;
import com.fcamara.backendtest.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = {"application/json", "application/xml"})
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.email()).isPresent()) throw new RuntimeException("");

        User newUser = new User(registerRequestDTO.email(), new BCryptPasswordEncoder().encode(registerRequestDTO.password()));

        userRepository.save(newUser);
    }


    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
}
