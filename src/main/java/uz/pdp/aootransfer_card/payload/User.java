package uz.pdp.aootransfer_card.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
 private String username;
 private long currentCash;
 private String cardId;
 private String cardPassword;
 private List<Outcome> outcomes;
 private List<Income> incomes;

}
