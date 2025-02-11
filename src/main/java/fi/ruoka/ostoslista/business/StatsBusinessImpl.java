package fi.ruoka.ostoslista.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.composite.StatsComposite;
import fi.ruoka.ostoslista.composite.StatsWeekdayComposite;
import fi.ruoka.ostoslista.repository.ReseptiOstoRepository;
import fi.ruoka.ostoslista.repository.TuoteOstoRepository;

@Service
public class StatsBusinessImpl implements StatsBusiness {

    @Autowired
    private ReseptiOstoRepository reseptiOstoRepository;

    @Autowired
    private TuoteOstoRepository tuoteOstoRepository;

    @Override
    public StatsComposite findMostBoughtReseptiIdAndCountByYear(Integer year) {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByYear(year);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new StatsComposite();
    }

    @Override
    public StatsComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month) {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByYearAndMonth(year, month);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new StatsComposite();
    }

    @Override
    public List<StatsWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday() {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByWeekday();
        if (!results.isEmpty()) {
            List<StatsWeekdayComposite> reseptiOstoComposites = new ArrayList<>();
            results.forEach(result -> reseptiOstoComposites.add(objectToReseptiOstoWeekdayComposite(result)));
            return reseptiOstoComposites;
        }
        return Arrays.asList(new StatsWeekdayComposite());
    }

    @Override
    public List<Integer> findAllAvailableYearsForResepti() {
        return reseptiOstoRepository.findAllAvailableYears();
    }

    @Override
    public StatsComposite findMostBoughtTuoteIdAndCountByYear(Integer year) {
        List<Object[]> results = tuoteOstoRepository.findMostBoughtTuoteIdAndCountByYear(year);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new StatsComposite();
    }

    @Override
    public StatsComposite findMostBoughtTuoteIdAndCountByYearAndMonth(Integer year, Integer month) {
        List<Object[]> results = tuoteOstoRepository.findMostBoughtTuoteIdAndCountByYearAndMonth(year, month);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new StatsComposite();
    }

    @Override
    public List<StatsWeekdayComposite> findMostBoughtTuoteIdAndCountByWeekday() {
        List<Object[]> results = tuoteOstoRepository.findMostBoughtTuoteIdAndCountByWeekday();
        if (!results.isEmpty()) {
            List<StatsWeekdayComposite> tuoteOstoComposites = new ArrayList<>();
            results.forEach(result -> tuoteOstoComposites.add(objectToReseptiOstoWeekdayComposite(result)));
            return tuoteOstoComposites;
        }
        return Arrays.asList(new StatsWeekdayComposite());
    }

    @Override
    public List<Integer> findAllAvailableYearsForTuote() {
        return tuoteOstoRepository.findAllAvailableYears();
    }

    private StatsComposite objectToReseptiOstoComposite(Object[] result) {
        Long entityId = (Long) result[0];
        Long count = (Long) result[1];
        StatsComposite ostoComposite = new StatsComposite();
        ostoComposite.setEntityId(entityId);
        ostoComposite.setCount(count);
        return ostoComposite;
    }

    private StatsWeekdayComposite objectToReseptiOstoWeekdayComposite(Object[] result) {
        Long entityId = (Long) result[0];
        Long count = (Long) result[1];
        BigDecimal weekday = (BigDecimal) result[2];
        StatsWeekdayComposite ostoWeekdayComposite = new StatsWeekdayComposite(); 
        ostoWeekdayComposite.setEntityId(entityId);
        ostoWeekdayComposite.setCount(count);
        ostoWeekdayComposite.setWeekday(weekday);
        return ostoWeekdayComposite;
    }
}
