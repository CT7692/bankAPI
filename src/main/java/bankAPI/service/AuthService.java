package bankAPI.service;

import bankAPI.payload.LoginDTO;
import bankAPI.payload.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);
    String register(RegisterDTO registerDTO, String role);
}
