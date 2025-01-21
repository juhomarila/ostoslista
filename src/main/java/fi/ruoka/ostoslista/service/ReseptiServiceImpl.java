package fi.ruoka.ostoslista.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.ReseptiBusiness;
import fi.ruoka.ostoslista.business.ReseptiError;
import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.dto.RuokaAineDto;
import fi.ruoka.ostoslista.entity.ReseptiEntity;
import fi.ruoka.ostoslista.entity.RuokaAineEntity;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@Service
public class ReseptiServiceImpl implements ReseptiService {

    @Autowired
    private ReseptiBusiness business;

    @Autowired
    private ReseptiValidator validator;

    private final OstosListaLogger logger;

    @Autowired
    public ReseptiServiceImpl(OstosListaLogger logger) {
        this.logger = logger;
    }

    @Override
    public ValidatedServiceResult<ReseptiDto> createResepti(ReseptiDto dto) {
        var vr = validator.validate(dto, true);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.RE102 + vr.getErrorMsg());
            return new ValidatedServiceResult<>(null, vr);
        }
        var optResepti = business.createResepti(dto);
        if (optResepti.isPresent()) {
            var reseptiDto = reseptiToDto(optResepti.get());
            return new ValidatedServiceResult<>(reseptiDto, vr);
        }
        logger.logError(ValidationError.RE104);
        return new ValidatedServiceResult<>(null, vr);
    }

    @Override
    public ValidatedServiceResult<List<ReseptiDto>> getAllResepti() {
        List<ReseptiDto> reseptiDtos = business.getAllResepti().stream().map(res -> reseptiToDto(res))
                .collect(Collectors.toList());
        List<ReseptiDto> validatedReseptiDtos = new ArrayList<>();
        for (ReseptiDto reseptiDto : reseptiDtos) {
            var vr = validator.validate(reseptiDto, false);
            if (vr.validated) {
                validatedReseptiDtos.add(reseptiDto);
            } else {
                logger.logValidationAndIdFailure(ValidationError.RE102 + vr.getErrorMsg(),
                        ValidationError.RE103 + reseptiDto.getId().toString());
            }
        }
        return new ValidatedServiceResult<>(validatedReseptiDtos, new ValidationResult());
    }

    @Override
    public ValidatedServiceResult<ReseptiDto> getReseptiById(Long id) {
        Optional<ReseptiEntity> optResepti = business.getReseptiById(id);
        var vr = new ValidationResult();
        if (optResepti.isEmpty()) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(ValidationError.VE001 + ".reseptiEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidatedServiceResult<>(null, vr);
        }
        var reseptiDto = reseptiToDto(optResepti.get());
        vr = validator.validate(reseptiDto, false);

        if (!vr.validated) {
            logger.logValidationAndIdFailure(ValidationError.RE102 + vr.getErrorMsg(),
                    ValidationError.RE103 + reseptiDto.getId().toString());
            return new ValidatedServiceResult<>(null, vr);
        }
        return new ValidatedServiceResult<>(reseptiDto, vr);
    }

    @Override
    public ValidatedServiceResult<Boolean> deleteResepti(Long id) {
        Optional<ReseptiEntity> optResepti = business.getReseptiById(id);
        var vr = new ValidationResult();
        var errorMsg = new ArrayList<String>();

        if (optResepti.isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".reseptiEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidatedServiceResult<>(false, vr);
        }
        vr = validator.validate(reseptiToDto(optResepti.get()), false);
        if (vr.validated) {
            business.deleteResepti(optResepti.get().getId());
        }
        return new ValidatedServiceResult<>(vr.validated, vr);
    }

    @Override
    public ValidatedServiceResult<ReseptiDto> updateResepti(Long id, ReseptiDto dto) {
        var vr = validator.validateUpdate(dto, id);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidatedServiceResult<>(null, vr);
        }
        try {
            var optResepti = business.updateResepti(id, dto);
            if (optResepti.isPresent()) {
                var reseptiDto = reseptiToDto(optResepti.get());
                return new ValidatedServiceResult<>(reseptiDto, vr);
            }
        } catch (ReseptiError e) {
            logger.logError(ValidationError.RE105);
            vr.getErrorMsg().add(ValidationError.RE105);
            return new ValidatedServiceResult<>(null, vr);
        }
        logger.logError(ValidationError.RE105);
        return new ValidatedServiceResult<>(null, vr);
    }

    @Override
    public ReseptiDto reseptiToDto(ReseptiEntity resepti) {
        ReseptiDto reseptiDto = new ReseptiDto();

        reseptiDto.setId(resepti.getId());
        reseptiDto.setOhje(resepti.getOhje());
        reseptiDto.setNimi(resepti.getNimi());
        reseptiDto.setAdded(resepti.getAdded());
        reseptiDto.setOstoKerrat(resepti.getOstoKerrat());
        reseptiDto.setRuokaAineet(ruokaAineetToDto(resepti.getRuokaAineet()));

        return reseptiDto;
    }

    private List<RuokaAineDto> ruokaAineetToDto(List<RuokaAineEntity> ruokaAineet) {
        List<RuokaAineDto> ruokaAineetDtosList = new ArrayList<>();
        ruokaAineet.forEach(ruokaAine -> {
            RuokaAineDto ruokaAineDto = new RuokaAineDto();
            ruokaAineDto.setId(ruokaAine.getId());
            ruokaAineDto.setMaara(ruokaAine.getMaara());
            ruokaAineDto.setRuokaAine(ruokaAine.getRuokaAine());
            ruokaAineDto.setYksikko(ruokaAine.getYksikko());
            ruokaAineetDtosList.add(ruokaAineDto);
        });
        return ruokaAineetDtosList;
    }
}
