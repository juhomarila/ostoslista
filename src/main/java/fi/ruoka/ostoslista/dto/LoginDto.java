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
public class LoginDto {
    @NotNull
    private boolean success;
    @NotBlank
    private String message;
    @NotBlank
    private String token;

    public LoginDto(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }
}
