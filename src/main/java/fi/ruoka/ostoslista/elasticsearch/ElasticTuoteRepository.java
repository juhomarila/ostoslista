package fi.ruoka.ostoslista.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface ElasticTuoteRepository extends ElasticsearchRepository<TuoteDocument, Long> {
    List<TuoteDocument> findByTuoteContaining(String tuote);

    @Query("""
        {
          "function_score": {
            "query": {
              "match": {
                "tuote": {
                  "query": "?0",
                  "fuzziness": "AUTO:1,4"
                }
              }
            },
            "functions": [
              {
                "field_value_factor": {
                  "field": "selectionCount",
                  "factor": 5,
                  "modifier": "none",
                  "missing": 1
                }
              }
            ],
            "boost_mode": "sum"
          }
        }
    """)
    List<TuoteDocument> findByTuoteFuzzy(String tuote);
}
