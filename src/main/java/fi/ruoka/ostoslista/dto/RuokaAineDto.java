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
public class RuokaAineDto {
    private Long id;
    @NotBlank
    private String ruokaAine;
    @NotNull
    private Long maara;
    @NotBlank
    @Pattern(regexp = "l|dl|ml|cl|tl|rl|kg|g|kpl|pss|rs|pkt|prk|", message = "Allowed values for yksikko are: l, dl, ml, cl, tl, rl, kg, g, kpl, pss, rs, pkt, prk.")
    private String yksikko;
}
