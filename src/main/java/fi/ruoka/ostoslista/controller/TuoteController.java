package fi.ruoka.ostoslista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.elasticsearch.SearchClickTrackingService;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.service.TuoteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tuotteet")
@CrossOrigin("*")
public class TuoteController {

    @Autowired
    private TuoteService tuoteService;

    @Autowired
    private SearchClickTrackingService searchClickTrackingService;

    private final OstosListaLogger logger;

    public TuoteController(OstosListaLogger logger) {
        this.logger = logger;
    }

    @GetMapping("/yksikot")
    public ResponseEntity<?> getYksikot() {
        logger.getLogStart("getYksikot");
        var vsr = tuoteService.getYksikot();
        logger.getLogEnd("getYksikot");
        return vsr.getT().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vsr.getT());
    }

    @PostMapping
    public ResponseEntity<?> addTuote(@Valid @RequestBody TuoteDto dto) {
        logger.postLogStart("addTuote");
        var vsr = tuoteService.addTuote(dto);
        logger.postLogEnd("addTuote");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<?> getAllTuotteet() {
        logger.getLogStart("getAllTuotteet");
        var vsr = tuoteService.getAllTuotteet();
        logger.getLogEnd("getAllTuotteet");
        return vsr.getT().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vsr.getT());
    }

    @GetMapping("/elastic")
    public ResponseEntity<?> getTuoteByTuote(@RequestParam String tuote) {
        logger.getLogStart("getTuoteByTuote");
        var vsr = tuoteService.getTuoteByTuote(tuote);
        logger.getLogEnd("getTuoteByTuote");
        return vsr.getT().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vsr.getT());
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveTuotteet() {
        logger.getLogStart("getActiveTuotteet");
        var vsr = tuoteService.getActiveTuotteet();
        logger.getLogEnd("getActiveTuotteet");
        return vsr.getT().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vsr.getT());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTuoteById(@PathVariable Long id) {
        logger.getLogStart("getTuoteById");
        var vsr = tuoteService.getTuoteById(id);
        logger.getLogEnd("getTuoteById");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTuote(@PathVariable Long id) {
        logger.deleteLogStart("deleteTuote");
        var vsr = tuoteService.deleteTuote(id);
        logger.deleteLogEnd("deleteTuote");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTuote(@PathVariable Long id, @Valid @RequestBody TuoteDto dto) {
        logger.putLogStart("updateTuote");
        var vsr = tuoteService.updateTuote(id, dto);
        logger.putLogEnd("updateTuote");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/select/{searchTerm}/{productId}")
    public ResponseEntity<Void> trackSearchClick(@PathVariable String searchTerm, @PathVariable Long productId) {
        searchClickTrackingService.trackClick(searchTerm, productId);
        return ResponseEntity.ok().build();
    }
}
