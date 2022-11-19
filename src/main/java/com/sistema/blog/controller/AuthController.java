package com.sistema.blog.controller;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegistryDTO;
import com.sistema.blog.entity.Role;
import com.sistema.blog.entity.User;
import com.sistema.blog.repository.RoleRepository;
import com.sistema.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(//objeto de autenticacion. Se creara con el nombre de usuario y la contraseña
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);//se guarda la autenticacion en el contexto de seguridad
        return new ResponseEntity<>("Ha iniciado sesion con exito!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody RegistryDTO registryDTO){
        if(userRepository.existsByUsername(registryDTO.getUsername())){//si este usuario ya existe
            return new ResponseEntity<>("El nombre de usuario ya existe!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registryDTO.getEmail())){//si este email ya existe
            return new ResponseEntity<>("El email ya existe!", HttpStatus.BAD_REQUEST);
        }

        //se crea el usuario. Se le asigna el rol de usuario, se encripta la contraseña y se guarda en la base de datos
        User user = new User();
        user.setNombre(registryDTO.getNombre());
        user.setUsername(registryDTO.getUsername());
        user.setEmail(registryDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registryDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();//se busca que el rol de usuario exista en la base de datos. En este caso, el rol de usuario es el de administrador
        user.setRoles(Collections.singleton(roles));//se le asigna el rol que debio ser encontrado

        userRepository.save(user);//se guarda el usuario en la base de datos
        return new ResponseEntity<>("El usuario se ha registrado con exito!", HttpStatus.OK);

    }
}
