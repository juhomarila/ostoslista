package fi.ruoka.ostoslista.elasticsearch;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchClickTrackingService {

    private final SearchClickTrackingRepository repository;

    public void trackClick(String searchTerm, Long productId) {
        Optional<SearchClickTracking> existingEntry = repository.findBySearchTermAndProductId(searchTerm, productId);

        if (existingEntry.isPresent()) {
            System.out.println("Incrementing selection count for product " + productId + " with search term " + searchTerm);
            SearchClickTracking tracking = existingEntry.get();
            tracking.setSelectionCount(tracking.getSelectionCount() + 1);
            repository.save(tracking);
        } else {
            SearchClickTracking newTracking = new SearchClickTracking();
            newTracking.setSearchTerm(searchTerm);
            newTracking.setProductId(productId);
            newTracking.setSelectionCount(1);
            repository.save(newTracking);
        }
    }
}

