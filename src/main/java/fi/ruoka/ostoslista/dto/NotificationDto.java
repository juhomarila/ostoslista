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
public class NotificationDto {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
}
