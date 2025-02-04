package fi.ruoka.ostoslista.dto;

import java.time.Instant;
import java.util.List;

import jakarta.validation.Valid;
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
public class ReseptiDto {
    private Long id;
    @NotNull
    @Valid
    private List<RuokaAineDto> ruokaAineet;
    @NotBlank
    private String ohje;
    @NotBlank
    private String nimi;
    @NotNull
    private Integer ostoKerrat;
    private Instant added;
    private String originalLink;
    private List<ReseptiOstoDto> reseptiOstot;
}