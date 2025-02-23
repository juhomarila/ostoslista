package fi.ruoka.ostoslista.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private boolean valmis;
    private List<OstosDto> ostokset;
    private List<Long> reseptiId;
    private Instant created;
    private Instant modified;
    private Integer version;
}
