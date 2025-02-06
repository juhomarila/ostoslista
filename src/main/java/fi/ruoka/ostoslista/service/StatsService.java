package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.composite.ReseptiOstoWeekdayComposite;

public interface StatsService {
    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year);

    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month);

    List<ReseptiOstoWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday();

    List<Integer> findAllAvailableReseptiYears();
}
