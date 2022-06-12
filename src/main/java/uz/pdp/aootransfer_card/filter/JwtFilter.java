package uz.pdp.aootransfer_card.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.aootransfer_card.security.JwtProvider;
import uz.pdp.aootransfer_card.service.MyAuth;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Log4j2
public class JwtFilter extends OncePerRequestFilter {



    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    MyAuth myAuth;







    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain        ) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        log.info("There is request token : {}",token);

        if(token != null && token.startsWith("Bearer")){

            token = token.substring(7);
            //CHECK TOKEN FOR CORRECT FROM VALIDATION
            boolean validateToken = jwtProvider.validateToken(token);
            if (validateToken) {



                //GET USERNAME FROM TOKEN
                String username = jwtProvider.getUsername(token);

                //CHECK USERNAME FROM REPOSITORY
                UserDetails userDetails = myAuth.loadUserByUsername(username);

                //CREATE AUTHENTICATION FROMM USER-DETAILS
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

//                System.out.println("Hey  Hey I am here --->"+SecurityContextHolder.getContext().getAuthentication());

                //SET CLIENT TO SYSTEM
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println("Hey  Hey I am here --->"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());


            }
        }
                filterChain.doFilter(request, response);

    }


}
