package uz.pdp.aootransfer_card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/")
public class HomeController {


    @GetMapping
    public ResponseEntity<?> getHomePage(){


        return ResponseEntity.ok("Welcome to My --- Card Transfer --- app");
    }

}
