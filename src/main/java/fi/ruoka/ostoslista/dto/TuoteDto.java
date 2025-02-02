package fi.ruoka.ostoslista.dto;

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
public class TuoteDto {
    private Long id;
    @NotBlank
    private String tuote;
    @NotNull
    private Integer osasto;
    private double hinta;
    @NotBlank
    private String yksikko;
    @NotNull
    private Integer ostoKerrat;
    private Integer kplOstettu;
}
