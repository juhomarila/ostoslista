package fi.ruoka.ostoslista.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String nimi;
    private Instant paiva;
    private List<OstosDto> ostokset;
}
