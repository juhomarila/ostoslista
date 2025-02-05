package fi.ruoka.ostoslista.composite;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReseptiOstoComposite {
    private Long reseptiEntityId;
    private Long count;
}
