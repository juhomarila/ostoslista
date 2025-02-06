package fi.ruoka.ostoslista.composite;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReseptiOstoWeekdayComposite {
    private Long reseptiEntityId;
    private Long count;
    private BigDecimal weekday;
}
