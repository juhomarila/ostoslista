package fi.ruoka.ostoslista.composite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StatsComposite {
    private Long entityId;
    private Long count;
}
