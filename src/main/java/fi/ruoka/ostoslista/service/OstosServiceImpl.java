package fi.ruoka.ostoslista.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.OstosBusiness;
import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@Service
public class OstosServiceImpl implements OstosService {

    @Autowired
    OstosBusiness business;

    @Autowired
    private OstosValidator validator;

    private final OstosListaLogger logger;

    @Autowired
    public OstosServiceImpl(OstosListaLogger logger) {
        this.logger = logger;
    }

    @Override
    public ValidateServiceResult<Boolean> deleteOstos(Long id) {
        Optional<OstosEntity> optOstos = business.getOstosById(id);
        var vr = new ValidationResult();
        var errorMsg = new ArrayList<String>();

        if (optOstos.isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".ostosEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.OE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(false, vr);
        }
        vr = validator.validate(ostosToDto(optOstos.get()));
        if (vr.validated) {
            business.deleteOstos(optOstos.get().getId());
        }
        return new ValidateServiceResult<>(vr.validated, vr);
    }

    @Override
    public ValidateServiceResult<OstosDto> addOstos(Long id, OstosDto dto) {
        var vr = validator.validate(dto);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.OE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        Optional<OstosEntity> optOstos = business.addOstos(id, dto);
        if (optOstos.isPresent()) {
            return new ValidateServiceResult<>(ostosToDto(optOstos.get()), vr);
        }
        logger.logError(ValidationError.OE104);
        return new ValidateServiceResult<>(null, vr);
    }

    private OstosDto ostosToDto(OstosEntity entity) {
        OstosDto dto = new OstosDto();
        dto.setMaara(entity.getMaara());
        dto.setTuote(entity.getTuote());
        dto.setYksikko(entity.getYksikko());
        dto.setOstosListaId(entity.getOstosLista().getId());
        return dto;
    }

}
