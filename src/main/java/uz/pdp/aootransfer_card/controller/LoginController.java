package uz.pdp.aootransfer_card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.aootransfer_card.payload.LoginDto;
import uz.pdp.aootransfer_card.security.JwtProvider;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<?> loginToServer(@RequestBody LoginDto loginDto){

        try {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsername(),
                    loginDto.getPassword()
            ));

            String token=jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);

        }catch (Exception e){
            return ResponseEntity.status(401).body("Username or password incorrect");

        }



    }
}
