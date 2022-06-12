package uz.pdp.aootransfer_card.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class MyAuth implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Get UserDetail
     * @param username
     * @return User
     * @throws UsernameNotFoundException "Username not found "
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /**
         * HandMade list of DB
         */

        List<User> users = new ArrayList<>(Arrays.asList(

                new User("Javohir", passwordEncoder.encode("0717"), new ArrayList<>()),
                new User("Mahmud", passwordEncoder.encode("forgot"), new ArrayList<>()),
                new User("Boy", passwordEncoder.encode("boy"), new ArrayList<>())

        ));


        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }

        log.error("ERROR : User name not found");
        throw new UsernameNotFoundException("User name not found");

    }

}
