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
public class TokenDto {
    @NotNull
    private boolean success;
    @NotBlank
    private String message;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;

    public TokenDto(boolean success, String message, String accessToken, String refreshToken) {
        this.success = success;
        this.message = message;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
