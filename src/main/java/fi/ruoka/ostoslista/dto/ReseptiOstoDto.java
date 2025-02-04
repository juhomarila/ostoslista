package fi.ruoka.ostoslista.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReseptiOstoDto {
    private Long id;
    private Instant ostoAika;
}
