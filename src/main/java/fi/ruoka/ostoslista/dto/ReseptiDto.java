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
public class ReseptiDto {
    private Long id;
    private List<RuokaAineDto> ruokaAineet;
    private String ohje;
    private String nimi;
    private Instant added;
}