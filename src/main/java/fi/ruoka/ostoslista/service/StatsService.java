package fi.ruoka.ostoslista.service;

import java.util.List;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;

public interface StatsService {
    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year);

    List<Integer> findAllAvailableReseptiYears();
}
