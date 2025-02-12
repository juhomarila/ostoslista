package fi.ruoka.ostoslista.elasticsearch;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.repository.TuoteRepository;

@Service
public class ElasticTuoteService {

    private final ElasticTuoteRepository elasticTuoteRepository;
    private final TuoteRepository tuoteRepository;

    public ElasticTuoteService(ElasticTuoteRepository elasticTuoteRepository, TuoteRepository tuoteRepository) {
        this.elasticTuoteRepository = elasticTuoteRepository;
        this.tuoteRepository = tuoteRepository;
    }

    public void syncToElasticsearch() {
        List<TuoteDocument> documents = tuoteRepository.findAll().stream()
                .map(entity -> {
                    TuoteDocument doc = new TuoteDocument();
                    doc.setId(entity.getId());
                    doc.setTuote(entity.getTuote());
                    return doc;
                })
                .collect(Collectors.toList());

        elasticTuoteRepository.saveAll(documents);
    }
}
