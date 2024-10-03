package fi.ruoka.ostoslista.dto;

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
    private String ruokaAine;
    private Long maara;
    private String yksikko;
}
