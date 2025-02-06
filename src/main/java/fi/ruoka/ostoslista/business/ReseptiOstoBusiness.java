package fi.ruoka.ostoslista.business;

import java.util.List;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.composite.ReseptiOstoWeekdayComposite;

public interface ReseptiOstoBusiness {

    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year);

    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month);

    List<ReseptiOstoWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday();

    List<Integer> findAllAvailableYears();
}
