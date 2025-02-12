package fi.ruoka.ostoslista.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface ElasticTuoteRepository extends ElasticsearchRepository<TuoteDocument, Long> {
    List<TuoteDocument> findByTuoteContaining(String tuote);

    @Query("{\"match\": {\"tuote\": {\"query\": \"?0\", \"fuzziness\": \"AUTO\"}}}")
    List<TuoteDocument> findByTuoteFuzzy(String tuote);
}
