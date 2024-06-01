package de.fortmeier.asset_management.iam;

import de.fortmeier.asset_management.iam.config.JwtService;
import de.fortmeier.asset_management.iam.requests.*;
import de.fortmeier.asset_management.iam.types.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service with business logic for all user operations.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class IamService {

    private final IamRepository iamRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder;

    @Value("${key.admin-password}")
    String adminPassword;

    private static final String ADMIN = "Admin";


    public Optional<User> findByUserName(String username) {
        return iamRepository.findByUserName(username);
    }


    @Transactional
    public void save(User user) {
        iamRepository.save(user);
    }

    @Transactional
    public void deleteUser(String userName) {
        User user = iamRepository.findByUserName(userName).orElseThrow(IllegalAccessError::new);
        iamRepository.delete(user);
    }

    @Transactional
    public void enableUser(String userName) {
        User user = iamRepository.findByUserName(userName).orElseThrow(IllegalAccessError::new);
        user.setEnabled(true);
        iamRepository.save(user);
    }

    public List<UserDto> findAll() {
        List<UserProjection> userProjections = iamRepository.findAllBy();
        List<UserDto> userDtos = new ArrayList<>();

        for (UserProjection userProjection : userProjections) {
            UserDto userDto = new UserDto(
                    userProjection.getFirstName(),
                    userProjection.getLastName(),
                    userProjection.getUserName(),
                    userProjection.getEnabled()
            );
            userDtos.add(userDto);
        }
        return userDtos;
    }


    /**
     * Authentication method, that proofs if the user exists and if the user is enabled.
     *
     * @param request with user credentials.
     * @return the response object, which includes the jwt.
     */
    @Transactional
    public AuthResponse authenticate(AuthRequest request) {

        var user = findByUserName(request.getUserName())
                .orElseThrow(IllegalStateException::new);

        if (!user.isEnabled()) throw new IllegalStateException("User not enabled");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }


    /**
     * Registers a user by the given user credentials.
     *
     * @param request contains the user credentials.
     */
    @Transactional
    public void register(RegistrationRequest request) {

        if (findByUserName(request.getUserName()).isPresent()) {
            throw new IllegalStateException();
        }

        User user = User.builder()
                .userName(request.getUserName())
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .role(Role.USER)
                .password(encoder.encode(request.getPassword()))
                .build();

        iamRepository.save(user);
    }


    /**
     * Registers the admin-user, if the user does not exist.
     */
    @Transactional
    public void registerAdmin() {

        if (findByUserName(ADMIN).isPresent()) return;

        User user = User.builder()
                .userName(ADMIN)
                .lastName(ADMIN)
                .firstName(ADMIN)
                .role(Role.ADMIN)
                .password(encoder.encode(adminPassword))
                .enabled(true)
                .build();

        System.out.println(adminPassword);

        iamRepository.save(user);
    }
}
