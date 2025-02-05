package fi.ruoka.ostoslista.business;

import java.util.List;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;

public interface ReseptiOstoBusiness {

    ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year);

    List<Integer> findAllAvailableYears();
}
