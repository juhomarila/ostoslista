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

import fi.ruoka.ostoslista.composite.StatsComposite;
import fi.ruoka.ostoslista.composite.StatsWeekdayComposite;
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

    @GetMapping("/mostbought/resepti/{year}")
    public ResponseEntity<StatsComposite> getMostBoughtReseptiIdAndCountByYear(@PathVariable Integer year) {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByYear");
        StatsComposite reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByYear(year);
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByYear");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.getEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/resepti/{year}/{month}")
    public ResponseEntity<StatsComposite> getMostBoughtReseptiIdAndCountByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByYearAndMonth");
        StatsComposite reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByYearAndMonth(year, month);
        logger.getLogEnd("getMostBoughtReseptiIdAndCountByYearAndMonth");
        return new ResponseEntity<>(reseptiOstoComposite, reseptiOstoComposite.getEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/resepti/weekdays")
    public ResponseEntity<List<StatsWeekdayComposite>> getMostBoughtReseptiIdAndCountByWeekday() {
        logger.getLogStart("getMostBoughtReseptiIdAndCountByWeekday");
        List<StatsWeekdayComposite> reseptiOstoComposite = statsService.findMostBoughtReseptiIdAndCountByWeekday();
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

    @GetMapping("/mostbought/tuote/{year}")
    public ResponseEntity<StatsComposite> getMostBoughtTuoteIdAndCountByYear(@PathVariable Integer year) {
        logger.getLogStart("getMostBoughtTuoteIdAndCountByYear");
        StatsComposite tuoteOstoComposite = statsService.findMostBoughtTuoteIdAndCountByYear(year);
        logger.getLogEnd("getMostBoughtTuoteIdAndCountByYear");
        return new ResponseEntity<>(tuoteOstoComposite, tuoteOstoComposite.getEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/tuote/{year}/{month}")
    public ResponseEntity<StatsComposite> getMostBoughtTuoteIdAndCountByYearAndMonth(@PathVariable Integer year, @PathVariable Integer month) {
        logger.getLogStart("getMostBoughtTuoteIdAndCountByYearAndMonth");
        StatsComposite tuoteOstoComposite = statsService.findMostBoughtTuoteIdAndCountByYearAndMonth(year, month);
        logger.getLogEnd("getMostBoughtTuoteIdAndCountByYearAndMonth");
        return new ResponseEntity<>(tuoteOstoComposite, tuoteOstoComposite.getEntityId() == null ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/mostbought/tuote/weekdays")
    public ResponseEntity<List<StatsWeekdayComposite>> getMostBoughtTuoteIdAndCountByWeekday() {
        logger.getLogStart("getMostBoughtTuoteIdAndCountByWeekday");
        List<StatsWeekdayComposite> tuoteOstoComposite = statsService.findMostBoughtTuoteIdAndCountByWeekday();
        logger.getLogEnd("getMostBoughtTuoteIdAndCountByWeekday");
        return new ResponseEntity<>(tuoteOstoComposite, tuoteOstoComposite.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/tuoteyears")
    public ResponseEntity<Integer[]> getAllAvailableTuoteYears() {
        logger.getLogStart("getAllAvailableTuoteYears");
        List<Integer> years = statsService.findAllAvailableTuoteYears();
        logger.getLogEnd("getAllAvailableReseptiYears");
        return new ResponseEntity<>(years.toArray(Integer[]::new), years.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }
}
