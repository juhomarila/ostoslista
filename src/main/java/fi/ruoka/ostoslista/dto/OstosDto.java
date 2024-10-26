package fi.ruoka.ostoslista.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OstosDto {
    private Long id;
    private String tuote;
    private Long maara;
    private String yksikko;
    private Long ostosListaId;
    private Boolean ostettu;
}
