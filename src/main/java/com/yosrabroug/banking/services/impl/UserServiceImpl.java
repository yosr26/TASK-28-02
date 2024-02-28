package com.yosrabroug.banking.services.impl;

import com.yosrabroug.banking.config.JwtUtil;
import com.yosrabroug.banking.dto.AccountDto;
import com.yosrabroug.banking.dto.AuthenticationRequest;
import com.yosrabroug.banking.dto.AuthenticationResponse;
import com.yosrabroug.banking.dto.UserDto;
import com.yosrabroug.banking.models.Role;
import com.yosrabroug.banking.models.User;
import com.yosrabroug.banking.repositories.RoleRepository;
import com.yosrabroug.banking.repositories.UserRepository;
import com.yosrabroug.banking.services.AccountService;
import com.yosrabroug.banking.services.UserService;
import com.yosrabroug.banking.validators.ObjectsValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // on n'a plus besoin d'écrire @Autowired
    // on a besoin de la repository pour communiquer avec la BD
    private final UserRepository repository;
    private final AccountService accountService;
    // on a besoin de l'object validator pour valider les objets
    private final ObjectsValidator<UserDto> validator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtils;
    private final AuthenticationManager authManager;
    private final RoleRepository roleRepository;
    private static final String ROLE_USER = "ROLE_USER";



    @Override
    public Integer save(UserDto dto) {
        validator.validate(dto);
        //on doit transformer le dto en une entité car repository prend user
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repository.save(user);
        return user.getId();
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
        //findall retourne une liste de users mais on veut retourner des dto
                .stream()
                // map(u -> UserDto.fromEntity(u)
                .map(UserDto :: fromEntity)
                .collect(Collectors.toList()); //on collecte une liste de userdto
    }

    @Override
    public UserDto findById(Integer id) {

        return repository.findById(id)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No user was found with this ID:" + id));
    }

    @Override
    public void delete(Integer id) {
        //todo check before delete
        repository.deleteById(id);

    }

    @Override
    @Transactional
    public Integer ValidateAccount(Integer id) {
        User user= repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(true);
        //create a bank account
        AccountDto account = AccountDto.builder()
                .user(UserDto.fromEntity(user))
                .build();
        accountService.save(account);
        repository.save(user);
        return user.getId();

    }

    @Override
    public Integer InvalidateAccount(Integer id) {

        User user= repository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("No user was found for user account validation"));
        user.setActive(false);
        repository.save(user);
        return user.getId();

    }

    @Override
    @Transactional
    public AuthenticationResponse register(UserDto dto) {
        validator.validate(dto);
        User user = UserDto.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(
                findOrCreateRole(ROLE_USER)
        );
        var savedUser = repository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", savedUser.getId());
        claims.put("fullName", savedUser.getFirstname()+" "+savedUser.getLastname());
        String token = jwtUtils.generateToken(savedUser);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final User user = repository.findByEmail(request.getEmail()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("fullName", user.getFirstname()+" "+user.getLastname());
        final String token = jwtUtils.generateToken(user, claims);
        return AuthenticationResponse.builder()
                        .token(token)
                        .build();

    }

    private Role findOrCreateRole(String roleName){
        Role role = roleRepository.findByName(roleName)
                .orElse(null);
        if (role == null){
            return roleRepository.save(
                    Role.builder()
                            .name(roleName)
                            .build()
            );
        }
        return role;
    }

}
