package fi.ruoka.ostoslista.business;

import java.util.List;

import fi.ruoka.ostoslista.composite.StatsComposite;
import fi.ruoka.ostoslista.composite.StatsWeekdayComposite;

public interface StatsBusiness {

    StatsComposite findMostBoughtReseptiIdAndCountByYear(Integer year);

    StatsComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month);

    List<StatsWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday();

    List<Integer> findAllAvailableYearsForResepti();

    StatsComposite findMostBoughtTuoteIdAndCountByYear(Integer year);

    StatsComposite findMostBoughtTuoteIdAndCountByYearAndMonth(Integer year, Integer month);

    List<StatsWeekdayComposite> findMostBoughtTuoteIdAndCountByWeekday();

    List<Integer> findAllAvailableYearsForTuote();
}
