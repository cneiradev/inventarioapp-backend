package com.inventario.app.controller;

import com.inventario.app.entity.Usuario;
import com.inventario.app.repository.UsuarioRepository;
import com.inventario.app.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioRepository repo;
    private final JwtService jwt;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioRepository repo, JwtService jwt, PasswordEncoder encoder) {
        this.repo = repo; this.jwt = jwt; this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return repo.findByEmail(email)
            .filter(u -> encoder.matches(password, u.getPassword()))
            .map(u -> ResponseEntity.ok(Map.of(
                "token", jwt.generate(u.getEmail(), u.getRol()),
                "user", Map.of("id", u.getId(), "nombre", u.getNombre(), "email", u.getEmail(), "rol", u.getRol(), "mustChangePassword", u.isMustChangePassword())
            )))
            .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenciales inválidas")));
    }
}