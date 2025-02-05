package fi.ruoka.ostoslista.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.ReseptiOstoBusiness;
import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private ReseptiOstoBusiness reseptiOstoBusiness;

    @Override
    public ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year) {
        return reseptiOstoBusiness.findMostBoughtReseptiIdAndCountByYear(year);
    }

    @Override
    public List<Integer> findAllAvailableReseptiYears() {
        return reseptiOstoBusiness.findAllAvailableYears();
    }

}
