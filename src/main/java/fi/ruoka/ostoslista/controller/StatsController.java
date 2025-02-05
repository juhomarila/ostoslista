package fi.ruoka.ostoslista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.service.StatsService;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin("*")
public class StatsController {

    @Autowired
    private StatsService statsService;

    private final OstosListaLogger logger;

    public StatsController(OstosListaLogger logger) {
        this.logger = logger;
    }

    @GetMapping("/mostbought/{year}")
    public ResponseEntity<ReseptiOstoComposite> getMostBoughtReseptiIdAndCountByYear(@PathVariable Integer year) {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByYear");
        var reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByYear(year);
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByYear");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.getReseptiEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/reseptiyears")
    public ResponseEntity<Integer[]> getAllAvailableReseptiYears() {
        logger.getLogStart("getAllAvailableReseptiYears");
        var years = statsService.findAllAvailableReseptiYears();
        logger.getLogEnd("getAllAvailableReseptiYears");
        return new ResponseEntity<>(years.toArray(Integer[]::new), years.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
