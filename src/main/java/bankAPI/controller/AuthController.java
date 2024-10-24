package bankAPI.controller;

import bankAPI.payload.LoginDTO;
import bankAPI.payload.RegisterDTO;
import bankAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/{role}")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO,
                                           @PathVariable("role") String role) {
        String response = authService.register(registerDTO, role);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String response = authService.login(loginDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
