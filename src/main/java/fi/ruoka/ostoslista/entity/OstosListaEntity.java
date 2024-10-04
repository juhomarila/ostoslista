package fi.ruoka.ostoslista.entity;

import java.util.List;
import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OstosListaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nimi;

    @NotNull
    private Instant paiva;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ostosLista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OstosEntity> ostokset;
}
