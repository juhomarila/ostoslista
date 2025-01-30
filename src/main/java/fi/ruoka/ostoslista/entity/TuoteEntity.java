package fi.ruoka.ostoslista.entity;

import java.util.List;

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
public class TuoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tuote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TuoteOstoEntity> tuoteOstot;

    @NotBlank
    private String tuote;

    @NotNull
    private Integer osasto;

    private double hinta;

    @NotBlank
    private String yksikko;

    @NotNull
    private Integer ostoKerrat;
}
