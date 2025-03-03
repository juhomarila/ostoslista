package fi.ruoka.ostoslista.elasticsearch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateElasticsearchService {

    private final ElasticTuoteRepository elasticTuoteRepository;
    private final SearchClickTrackingRepository searchClickTrackingRepository;

    private static final Logger logger = LoggerFactory.getLogger(UpdateElasticsearchService.class);

    @Scheduled(fixedRate = 60000)
    public void updateSelectionCounts() {
        logger.info("Updating selection counts in Elasticsearch");

        List<SearchClickTracking> clickData = searchClickTrackingRepository.findAll();

        for (SearchClickTracking click : clickData) {
            elasticTuoteRepository.findById(click.getProductId()).ifPresent(document -> {
                document.setSelectionCount(click.getSelectionCount());
                elasticTuoteRepository.save(document);
            });
        }
    }
}

