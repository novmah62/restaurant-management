package com.novmah.restaurantmanagement.service.impl;

import com.novmah.restaurantmanagement.constant.ApiConstant;
import com.novmah.restaurantmanagement.dto.request.ChangePasswordRequest;
import com.novmah.restaurantmanagement.dto.request.LoginRequest;
import com.novmah.restaurantmanagement.dto.request.RefreshTokenRequest;
import com.novmah.restaurantmanagement.dto.request.RegisterRequest;
import com.novmah.restaurantmanagement.dto.response.AuthenticationResponse;
import com.novmah.restaurantmanagement.dto.response.EmailDetails;
import com.novmah.restaurantmanagement.entity.Role;
import com.novmah.restaurantmanagement.entity.User;
import com.novmah.restaurantmanagement.entity.VerificationToken;
import com.novmah.restaurantmanagement.entity.status.UserStatus;
import com.novmah.restaurantmanagement.exception.ResourceNotFoundException;
import com.novmah.restaurantmanagement.repository.RoleRepository;
import com.novmah.restaurantmanagement.repository.UserRepository;
import com.novmah.restaurantmanagement.repository.VerificationTokenRepository;
import com.novmah.restaurantmanagement.security.JwtProvider;
import com.novmah.restaurantmanagement.service.AuthService;
import com.novmah.restaurantmanagement.service.MailService;
import com.novmah.restaurantmanagement.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final MailService mailService;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, VerificationTokenRepository verificationTokenRepository, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService, MailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
        this.mailService = mailService;
    }

    @Override
    public String signup(RegisterRequest registerRequest) {
        Set<Role> roles = Collections.singleton(roleRepository.findById(ApiConstant.ROLE_ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role ID", ApiConstant.ROLE_ADMIN.toString())));
        User user = userRepository.save(User.builder()
                .name(registerRequest.getName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .status(UserStatus.INACTIVE)
                .enabled(false)
                .build());

        String token = generateVerificationToken(user);
        log.info("VERIFICATION TOKEN: {}", token);
        mailService.sendEmailAlert(EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your account has been successfully created.\n" +
                        "Your Account Details: \n" +
                        "Name: " + user.getName() + "\n" +
                        "Email: " + user.getEmail() + "\n" +
                        "please click on the below url to activate your account : " +
                        "http://localhost:8080/api/v1/auth/accountVerification/" + token)
                .build());
        return "Your account has been created successfully. Please check your email to activate your account";
    }
    public String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .expiryDate(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .user(user).build();
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Override
    public String verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new ResourceNotFoundException("Invalid token")));
        return "Account Activated Successfully";
    }
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        String email = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", loginRequest.getEmail()));
        user.setStatus(UserStatus.ONLINE);
        userRepository.save(user);
        log.info("{}", authentication.getAuthorities());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .email(loginRequest.getEmail()).build();
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenEmail(refreshTokenRequest.getEmail());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .email(refreshTokenRequest.getEmail()).build();
    }

    @Override
    public String logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = userRepository.findByEmail(refreshTokenRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", refreshTokenRequest.getEmail()));
        user.setStatus(UserStatus.OFFLINE);
        userRepository.save(user);
        return "Refresh Token Deleted Successfully!!!";
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = this.getCurrentUser();
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Wrong password");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmationPassword())) {
            throw new ResourceNotFoundException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Override
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getSubject())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid account"));
    }
}
