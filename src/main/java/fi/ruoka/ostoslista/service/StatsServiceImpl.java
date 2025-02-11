package fi.ruoka.ostoslista.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.StatsBusiness;
import fi.ruoka.ostoslista.composite.StatsComposite;
import fi.ruoka.ostoslista.composite.StatsWeekdayComposite;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsBusiness statsBusiness;

    @Override
    public StatsComposite findMostBoughtReseptiIdAndCountByYear(Integer year) {
        return statsBusiness.findMostBoughtReseptiIdAndCountByYear(year);
    }

    @Override
    public StatsComposite findMostBoughtReseptiIdAndCountByYearAndMonth(Integer year, Integer month) {
        return statsBusiness.findMostBoughtReseptiIdAndCountByYearAndMonth(year, month);
    }   

    @Override
    public List<StatsWeekdayComposite> findMostBoughtReseptiIdAndCountByWeekday() {
        return statsBusiness.findMostBoughtReseptiIdAndCountByWeekday();
    }

    @Override
    public List<Integer> findAllAvailableReseptiYears() {
        return statsBusiness.findAllAvailableYearsForResepti();
    }

    @Override
    public StatsComposite findMostBoughtTuoteIdAndCountByYear(Integer year) {
        return statsBusiness.findMostBoughtTuoteIdAndCountByYear(year);
    }

    @Override
    public StatsComposite findMostBoughtTuoteIdAndCountByYearAndMonth(Integer year, Integer month) {
        return statsBusiness.findMostBoughtTuoteIdAndCountByYearAndMonth(year, month);
    }   

    @Override
    public List<StatsWeekdayComposite> findMostBoughtTuoteIdAndCountByWeekday() {
        return statsBusiness.findMostBoughtTuoteIdAndCountByWeekday();
    }

    @Override
    public List<Integer> findAllAvailableTuoteYears() {
        return statsBusiness.findAllAvailableYearsForTuote();
    }

}
