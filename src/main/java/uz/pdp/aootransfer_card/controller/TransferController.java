package uz.pdp.aootransfer_card.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import uz.pdp.aootransfer_card.filter.JwtFilter;
import uz.pdp.aootransfer_card.payload.Income;
import uz.pdp.aootransfer_card.payload.Outcome;
import uz.pdp.aootransfer_card.payload.User;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.aootransfer_card.service.MyAuth;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/cabinet/main")
public class TransferController {

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    MyAuth myAuth;



    List<User> userList = new ArrayList<>(
            Arrays.asList(
                new User("Javohir",50_000,"12345j","password_j",new ArrayList<>(),new ArrayList<>()),
                new User("Mahmud",100_000,"12345m","password_m",new ArrayList<>(),new ArrayList<>()),
                new User("Boy",10_000_000,"12345b","password_b",new ArrayList<>(),new ArrayList<>())
            )
    );


    @PostMapping("/send")
    public ResponseEntity<?> makeTransfer(@RequestBody Outcome outcome) {
        if(outcome.getCash()>100000) return ResponseEntity.status(401).body("Out of limited transaction ");




        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        if (outcome.getFromWhomCardId().equals(outcome.getToWhomCardId()))
            return ResponseEntity.status(401).body("You can't send to your card");

       User currentClient = null;int cId=0;
       User currentUser = null;int uId=0;


        //Set user and client to object by card id
        for (int i=0;i<userList.size();i++) {
            if(userList.get(i).getCardId().equals(outcome.getToWhomCardId())) {currentClient=userList.get(i);cId=i;}
            if(userList.get(i).getCardId().equals(outcome.getFromWhomCardId())) {currentUser=userList.get(i);uId=i;}
        }


        //Check User and Client card id to exist
        if (currentUser != null) {
            if (!currentUser.getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                return ResponseEntity.status(401).body("Card is not yours! Enter your card id");
            if (currentClient!= null) {

                if (currentUser.getCurrentCash() > outcome.getCash()) {

                    outcome.setDate(new Date());
                    userList.get(uId).getOutcomes().add(outcome);
                    userList.get(uId).setCurrentCash(userList.get(uId).getCurrentCash()-outcome.getCash());

                    Income income=new Income();
                    income.setDate(new Date());
                    income.setFromWhomCardId(userList.get(uId).getCardId());
                    income.setCash(outcome.getCash());
                    userList.get(cId).setCurrentCash(userList.get(cId).getCurrentCash()+outcome.getCash());
                    userList.get(cId).getIncomes().add(income);

                    System.out.println(userList.get(uId));
                    System.out.println(userList.get(cId));
                    return ResponseEntity.ok("Successfully transacted");
                } else
                  return   ResponseEntity.status(401).body("Your balance is not enough to accomplish transaction! Replenish your account");

            } else ResponseEntity.status(404).body("Please Enter valid card id");
        } else ResponseEntity.status(404).body("Please Enter valid card id");


        return ResponseEntity.ok("Send method not work");
    }



    @GetMapping("/outcomes")
    public ResponseEntity<?> getOutcomes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getOutcomes());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/incomes")
    public ResponseEntity<?> getIncomes(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getIncomes());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        for (User user : userList) {
            if (user.getUsername().equals(username)){
                return ResponseEntity.ok(user.getCurrentCash());
            }
        }
        return ResponseEntity.notFound().build();
    }



    @GetMapping()
    public ResponseEntity<?> mainPage() {
        return ResponseEntity.ok("Welcome MyPay app \n\n Note !!! \n  .Cash transaction can't be more than 100 000 sum \n .Enter exist card id \n .Check your balance before transaction");

    }

}
