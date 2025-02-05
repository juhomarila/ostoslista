package fi.ruoka.ostoslista.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class ReseptiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resepti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuokaAineEntity> ruokaAineet;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "resepti", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReseptiOstoEntity> reseptiOstot;

    @Column(columnDefinition = "TEXT")
    private String ohje;

    @NotBlank
    private String nimi;

    @NotNull
    private Instant added;

    @NotNull
    private Integer ostoKerrat;

    private String originalLink;

    private Instant created;

    private Instant modified;

    private Integer version;
}
