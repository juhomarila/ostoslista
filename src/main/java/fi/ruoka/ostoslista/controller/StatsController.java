package fi.ruoka.ostoslista.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.composite.ReseptiOstoWeekdayComposite;
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
        ReseptiOstoComposite reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByYear(year);
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByYear");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.getReseptiEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/{year}/{month}")
    public ResponseEntity<ReseptiOstoComposite> getMostBoughtReseptiIdAndCountByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByYearAndMonth");
        ReseptiOstoComposite reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByYearAndMonth(year, month);
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByYearAndMonth");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.getReseptiEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/weekdays")
    public ResponseEntity<List<ReseptiOstoWeekdayComposite>> getMostBoughtReseptiIdAndCountByWeekday() {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByWeekday");
        List<ReseptiOstoWeekdayComposite> reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByWeekday();
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByWeekday");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/reseptiyears")
    public ResponseEntity<Integer[]> getAllAvailableReseptiYears() {
        logger.getLogStart("getAllAvailableReseptiYears");
        List<Integer> years = statsService.findAllAvailableReseptiYears();
        logger.getLogEnd("getAllAvailableReseptiYears");
        return new ResponseEntity<>(years.toArray(Integer[]::new), years.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
