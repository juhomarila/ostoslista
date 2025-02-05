package fi.ruoka.ostoslista.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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

    @NotNull
    private boolean valmis;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ostosLista", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("osastoId ASC, tuote ASC")
    private List<OstosEntity> ostokset;

    /* 
    -------------------------------
    As long as flyway does not support postgresql 17, we need to use the following workaround
    to create a table for the many-to-many relationship between OstosListaEntity and ReseptiEntity.
    When flyway starts support for postgresql 17, we can remove this workaround and write migration 
    script for reseptiId 
    -------------------------------
    */

    @ElementCollection
    @CollectionTable(name = "ostos_lista_resepti", joinColumns = @JoinColumn(name = "ostos_lista_id"))
    @Column(name = "resepti_id")
    private List<Long> reseptiId;

    private Instant created;

    private Instant modified;

    private Integer version;
}
