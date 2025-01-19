package fi.ruoka.ostoslista.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.OstosListaBusiness;
import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.entity.OstosEntity;
import fi.ruoka.ostoslista.entity.OstosListaEntity;
import fi.ruoka.ostoslista.enums.Tuotteet;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@Service
public class OstosListaServiceImpl implements OstosListaService {

    @Autowired
    private OstosListaBusiness business;

    @Autowired
    private OstosListaValidator validator;

    private final OstosListaLogger logger;

    @Autowired
    public OstosListaServiceImpl(OstosListaLogger logger) {
        this.logger = logger;
    }

    @Override
    public ValidateServiceResult<OstosListaDto> createOstosLista(OstosListaDto dto) {
        var vr = validator.validate(dto);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.OLE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        Optional<OstosListaEntity> optOstosLista = business.createOstosLista(dto);
        if (optOstosLista.isPresent()) {
            var ostosListaDto = ostosListaToDto(optOstosLista.get());
            return new ValidateServiceResult<>(ostosListaDto, vr);
        }
        logger.logError(ValidationError.OLE104);
        return new ValidateServiceResult<>(null, vr);
    }

    @Override
    public ValidateServiceResult<OstosListaDto> reseptiToOstosLista(ReseptiDto dto) {
        OstosListaDto ostosListaDto = new OstosListaDto();
        ostosListaDto.setNimi("Ostoslista");
        List<OstosDto> ostokset = dto.getRuokaAineet().stream()
                .map(ra -> {
                    OstosDto ostos = new OstosDto();
                    ostos.setMaara(ra.getMaara());
                    ostos.setTuote(ra.getRuokaAine());
                    ostos.setYksikko(ra.getYksikko());
                    ostos.setOstettu(false);
                    return ostos;
                })
                .collect(Collectors.toList());
        ostosListaDto.setOstokset(ostokset);
        return createOstosLista(ostosListaDto);
    }

    @Override
    public ValidateServiceResult<List<OstosListaDto>> getAllOstosLista() {
        List<OstosListaDto> ostosListaDtos = business.getAllOstosLista().stream()
                .map(ostosLista -> ostosListaToDto(ostosLista))
                .collect(Collectors.toList());
        List<OstosListaDto> validatedOstosListaDtos = new ArrayList<>();
        for (OstosListaDto ostosListaDto : ostosListaDtos) {
            var vr = validator.validate(ostosListaDto);
            if (vr.validated) {
                validatedOstosListaDtos.add(ostosListaDto);
            } else {
                logger.logValidationAndIdFailure(ValidationError.OLE102 + vr.getErrorMsg(),
                        ValidationError.OLE103 + ostosListaDto.getId().toString());
            }
        }
        return new ValidateServiceResult<>(validatedOstosListaDtos, new ValidationResult());
    }

    @Override
    public ValidateServiceResult<OstosListaDto> getOstosListaById(Long id) {
        Optional<OstosListaEntity> optOstosLista = business.getOstosListaById(id);
        var vr = new ValidationResult();
        if (optOstosLista.isEmpty()) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(ValidationError.VE001 + ".ostosListaEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.OLE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var ostosListaDto = ostosListaToDto(optOstosLista.get());
        vr = validator.validate(ostosListaDto);

        if (!vr.validated) {
            logger.logValidationAndIdFailure(ValidationError.OLE102 + vr.getErrorMsg(),
                    ValidationError.OLE103 + ostosListaDto.getId().toString());
            return new ValidateServiceResult<>(null, vr);
        }
        return new ValidateServiceResult<>(ostosListaDto, vr);
    }

    @Override
    public ValidateServiceResult<OstosListaDto> updateOstosLista(Long id, OstosListaDto dto) {
        var vr = validator.validateUpdate(dto, id);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.OLE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        Optional<OstosListaEntity> optOstosLista = business.updateOstosLista(id, dto);
        if (optOstosLista.isPresent()) {
            var ostosListaDto = ostosListaToDto(optOstosLista.get());
            return new ValidateServiceResult<>(ostosListaDto, vr);
        }
        logger.logError(ValidationError.OLE105);
        return new ValidateServiceResult<>(null, vr);
    }

    @Override
    public ValidateServiceResult<Boolean> deleteOstosLista(Long id) {
        Optional<OstosListaEntity> optOstosLista = business.getOstosListaById(id);
        var vr = new ValidationResult();
        var errorMsg = new ArrayList<String>();

        if (optOstosLista.isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".ostosListaEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(false, vr);
        }
        vr = validator.validate(ostosListaToDto(optOstosLista.get()));
        if (vr.validated) {
            business.deleteOstosLista(optOstosLista.get().getId());
        }
        return new ValidateServiceResult<>(vr.validated, vr);
    }

    private OstosListaDto ostosListaToDto(OstosListaEntity entity) {
        OstosListaDto dto = new OstosListaDto();

        dto.setId(entity.getId());
        dto.setNimi(entity.getNimi());
        dto.setPaiva(entity.getPaiva());
        dto.setOstokset(ostosToDto(entity.getOstokset()));

        return dto;
    }

    private List<OstosDto> ostosToDto(List<OstosEntity> ostokset) {
        List<OstosDto> ostosDtosList = new ArrayList<>();
        ostokset.forEach(ostos -> {
            OstosDto ostosDto = new OstosDto();
            ostosDto.setId(ostos.getId());
            ostosDto.setMaara(ostos.getMaara());
            ostosDto.setTuote(ostos.getTuote());
            ostosDto.setYksikko(ostos.getYksikko());
            ostosDto.setOstettu(ostos.getOstettu());
            ostosDto.setOsastoId(ostos.getOsastoId());
            ostosDto.setOstosListaId(ostos.getOstosLista().getId());
            ostosDtosList.add(ostosDto);
        });
        return ostosDtosList;
    }
}
