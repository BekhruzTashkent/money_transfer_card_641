package uz.pdp.aootransfer_card.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outcome {

    private int id;
    private Date date;
    private String fromWhomCardId;
    private String toWhomCardId;
    private long cash;

}
