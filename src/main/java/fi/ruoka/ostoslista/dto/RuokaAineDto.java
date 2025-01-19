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
public class RuokaAineDto {
    private Long id;
    @NotBlank
    private String ruokaAine;
    @NotNull
    private Long maara;
    @NotBlank
    @ValidYksikko
    private String yksikko;
}
