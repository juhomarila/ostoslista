package fi.ruoka.ostoslista.dto;

import fi.ruoka.ostoslista.enums.ValidYksikko;
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
public class OstosDto {
    private Long id;
    @NotBlank
    private String tuote;
    @NotNull
    private Long maara;
    @NotBlank
    @ValidYksikko
    private String yksikko;
    private Long ostosListaId;
    private Integer osastoId;
    private Boolean ostettu;
}
