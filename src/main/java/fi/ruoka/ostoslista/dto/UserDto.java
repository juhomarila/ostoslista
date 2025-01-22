package fi.ruoka.ostoslista.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    @NotBlank
    private String kayttajatunnus;
    @NotBlank
    private String salasana;
}
