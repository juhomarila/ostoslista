package fi.ruoka.ostoslista.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.ReseptiBusiness;
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
    public ValidateServiceResult<ReseptiDto> createResepti(ReseptiDto dto) {
        var vr = validator.validate(dto, true);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.RE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var optResepti = business.createResepti(dto);
        if (optResepti.isPresent()) {
            var reseptiDto = reseptiToDto(optResepti.get());
            return new ValidateServiceResult<>(reseptiDto, vr);
        }
        logger.logError(ValidationError.RE104);
        return new ValidateServiceResult<>(null, vr);
    }

    @Override
    public ValidateServiceResult<List<ReseptiDto>> getAllResepti() {
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
        return new ValidateServiceResult<>(validatedReseptiDtos, new ValidationResult());
    }

    @Override
    public ValidateServiceResult<ReseptiDto> getReseptiById(Long id) {
        Optional<ReseptiEntity> optResepti = business.getReseptiById(id);
        var vr = new ValidationResult();
        if (optResepti.isEmpty()) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(ValidationError.VE001 + ".reseptiEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var reseptiDto = reseptiToDto(optResepti.get());
        vr = validator.validate(reseptiDto, false);

        if (!vr.validated) {
            logger.logValidationAndIdFailure(ValidationError.RE102 + vr.getErrorMsg(),
                    ValidationError.RE103 + reseptiDto.getId().toString());
            return new ValidateServiceResult<>(null, vr);
        }
        return new ValidateServiceResult<>(reseptiDto, vr);
    }

    @Override
    public ValidateServiceResult<Boolean> deleteResepti(Long id) {
        Optional<ReseptiEntity> optResepti = business.getReseptiById(id);
        var vr = new ValidationResult();
        var errorMsg = new ArrayList<String>();

        if (optResepti.isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".reseptiEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.RE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(false, vr);
        }
        vr = validator.validate(reseptiToDto(optResepti.get()), false);
        if (vr.validated) {
            business.deleteResepti(optResepti.get().getId());
        }
        return new ValidateServiceResult<>(vr.validated, vr);
    }

    @Override
    public ValidateServiceResult<ReseptiDto> updateResepti(ReseptiDto dto) {
        var vr = validator.validate(dto, false);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.RE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var optResepti = business.updateResepti(dto);
        if (optResepti.isPresent()) {
            var reseptiDto = reseptiToDto(optResepti.get());
            return new ValidateServiceResult<>(reseptiDto, vr);
        }
        logger.logError(ValidationError.RE105);
        return new ValidateServiceResult<>(null, vr);
    }

    @Override
    public ReseptiDto reseptiToDto(ReseptiEntity resepti) {
        ReseptiDto reseptiDto = new ReseptiDto();

        reseptiDto.setId(resepti.getId());
        reseptiDto.setOhje(resepti.getOhje());
        reseptiDto.setNimi(resepti.getNimi());
        reseptiDto.setAdded(resepti.getAdded());
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
