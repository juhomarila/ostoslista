package fi.ruoka.ostoslista.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.composite.ReseptiOstoWeekdayComposite;
import fi.ruoka.ostoslista.repository.ReseptiOstoRepository;

@Service
public class ReseptiOstoBusinessImpl implements ReseptiOstoBusiness {

    @Autowired
    private ReseptiOstoRepository reseptiOstoRepository;

    @Override
    public ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year) {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByYear(year);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new ReseptiOstoComposite();
    }

    @Override
    public ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month) {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByYearAndMonth(year, month);
        if (!results.isEmpty()) {
            return objectToReseptiOstoComposite(results.get(0));
        }
        return new ReseptiOstoComposite();
    }

    @Override
    public List<ReseptiOstoWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday() {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByWeekday();
        if (!results.isEmpty()) {
            List<ReseptiOstoWeekdayComposite> reseptiOstoComposites = new ArrayList<>();
            results.forEach(result -> reseptiOstoComposites.add(objectToReseptiOstoWeekdayComposite(result)));
            return reseptiOstoComposites;
        }
        return Arrays.asList(new ReseptiOstoWeekdayComposite());
    }

    @Override
    public List<Integer> findAllAvailableYears() {
        return reseptiOstoRepository.findAllAvailableYears();
    }

    private ReseptiOstoComposite objectToReseptiOstoComposite(Object[] result) {
        Long reseptiEntityId = (Long) result[0];
        Long count = (Long) result[1];
        ReseptiOstoComposite reseptiOstoComposite = new ReseptiOstoComposite();
        reseptiOstoComposite.setReseptiEntityId(reseptiEntityId);
        reseptiOstoComposite.setCount(count);
        return reseptiOstoComposite;
    }

    private ReseptiOstoWeekdayComposite objectToReseptiOstoWeekdayComposite(Object[] result) {
        Long reseptiEntityId = (Long) result[0];
        Long count = (Long) result[1];
        BigDecimal weekday = (BigDecimal) result[2];
        ReseptiOstoWeekdayComposite reseptiOstoWeekdayComposite = new ReseptiOstoWeekdayComposite(); 
        reseptiOstoWeekdayComposite.setReseptiEntityId(reseptiEntityId);
        reseptiOstoWeekdayComposite.setCount(count);
        reseptiOstoWeekdayComposite.setWeekday(weekday);
        return reseptiOstoWeekdayComposite;
    }
}
