package fi.ruoka.ostoslista.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.composite.ReseptiOstoComposite;
import fi.ruoka.ostoslista.repository.ReseptiOstoRepository;

@Service
public class ReseptiOstoBusinessImpl implements ReseptiOstoBusiness {

    @Autowired
    private ReseptiOstoRepository reseptiOstoRepository;

    @Override
    public ReseptiOstoComposite findMostBoughtReseptiIdAndCountByYear(Integer year) {
        List<Object[]> results = reseptiOstoRepository.findMostBoughtReseptiIdAndCountByYear(year);
        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            Long reseptiEntityId = (Long) result[0];
            Long count = (Long) result[1];
            ReseptiOstoComposite reseptiOstoComposite = new ReseptiOstoComposite();
            reseptiOstoComposite.setReseptiEntityId(reseptiEntityId);
            reseptiOstoComposite.setCount(count);
            return reseptiOstoComposite;
        }
        return new ReseptiOstoComposite();
    }

    @Override
    public List<Integer> findAllAvailableYears() {
        return reseptiOstoRepository.findAllAvailableYears();
    }

}
