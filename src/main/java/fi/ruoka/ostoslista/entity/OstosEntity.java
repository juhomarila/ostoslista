package fi.ruoka.ostoslista.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OstosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String tuote;

    @NotNull
    private Long maara;

    @NotBlank
    private String yksikko;

    @ManyToOne
    @JoinColumn(name = "OstosListaEntity_id")
    private OstosListaEntity ostosLista;

    private Boolean ostettu;
}
