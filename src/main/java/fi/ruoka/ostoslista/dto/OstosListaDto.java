package fi.ruoka.ostoslista.dto;

import java.util.List;
import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OstosListaDto {
    private Long id;
    private String nimi;
    private Instant paiva;
    private List<OstosDto> ostokset;
}
