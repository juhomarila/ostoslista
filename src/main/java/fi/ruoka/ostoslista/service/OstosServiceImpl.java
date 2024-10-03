package fi.ruoka.ostoslista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.OstosBusiness;
import fi.ruoka.ostoslista.dto.OstosDto;

@Service
public class OstosServiceImpl implements OstosService {

    @Autowired
    OstosBusiness business;

    @Override
    public boolean deleteOstos(Long id) {
        return business.deleteOstos(id);
    }

    @Override
    public boolean addOstos(Long id, OstosDto dto) {
        return business.addOstos(id, dto);
    }

}
