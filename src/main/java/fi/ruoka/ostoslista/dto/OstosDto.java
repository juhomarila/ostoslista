package fi.ruoka.ostoslista.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "l|kg|kpl|g|pss|rs|pkt", message = "Allowed values for yksikko are: l, kg, kpl, g, pss, rs, pkt.")
    private String yksikko;
    private Long ostosListaId;
    private Boolean ostettu;
}
