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
public class StatsWeekdayComposite {
    private Long entityId;
    private Long count;
    private BigDecimal weekday;
}
