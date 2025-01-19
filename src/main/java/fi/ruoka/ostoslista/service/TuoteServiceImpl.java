package fi.ruoka.ostoslista.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ruoka.ostoslista.business.TuoteBusiness;
import fi.ruoka.ostoslista.dto.TuoteDto;
import fi.ruoka.ostoslista.entity.TuoteEntity;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@Service
public class TuoteServiceImpl implements TuoteService {

    @Autowired
    private TuoteBusiness business;

    @Autowired
    private TuoteValidator validator;

    private final OstosListaLogger logger;

    @Autowired
    public TuoteServiceImpl(OstosListaLogger logger) {
        this.logger = logger;
    }

    @Override
    public ValidateServiceResult<TuoteDto> addTuote(TuoteDto dto) {
        var vr = validator.validate(dto, true);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.TE102 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var optTuote = business.addTuote(dto);
        if (optTuote.isPresent()) {
            var tuoteDto = tuoteToDto(optTuote.get());
            return new ValidateServiceResult<>(tuoteDto, vr);
        }
        logger.logError(ValidationError.TE104);
        return new ValidateServiceResult<>(null, vr);
    }

    @Override
    public ValidateServiceResult<List<TuoteDto>> getAllTuotteet() {
        List<TuoteDto> tuoteDtos = business.getAllTuotteet().stream().map(tuote -> tuoteToDto(tuote))
                .collect(Collectors.toList());
        List<TuoteDto> validatedTuoteDtos = new ArrayList<>();
        for (TuoteDto tuoteDto : tuoteDtos) {
            var vr = validator.validate(tuoteDto, false);
            if (vr.validated) {
                validatedTuoteDtos.add(tuoteDto);
            } else {
                logger.logValidationAndIdFailure(ValidationError.TE102 + vr.getErrorMsg(), ValidationError.TE103 + tuoteDto.getId().toString());
            }
        }
        return new ValidateServiceResult<>(validatedTuoteDtos, new ValidationResult(true));
    }

    @Override
    public ValidateServiceResult<TuoteDto> getTuoteById(Long id) {
        Optional<TuoteEntity> optTuote = business.getTuoteById(id);
        var vr = new ValidationResult();
        if (optTuote.isEmpty()) {
            List<String> errorMsg = new ArrayList<>();
            errorMsg.add(ValidationError.VE001 + ".tuoteEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.TE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }
        var tuoteDto = tuoteToDto(optTuote.get());
        vr = validator.validate(tuoteDto, false);

        if (!vr.validated) {
            logger.logValidationAndIdFailure(ValidationError.TE102 + vr.getErrorMsg(), ValidationError.TE103 + tuoteDto.getId().toString());
            return new ValidateServiceResult<>(null, vr);
        }
        return new ValidateServiceResult<>(tuoteDto, vr);
    }

    @Override
    public ValidateServiceResult<Boolean> deleteTuote(Long id) {
        Optional<TuoteEntity> optTuote = business.getTuoteById(id);
        var vr = new ValidationResult();
        var errorMsg = new ArrayList<String>();

        if (optTuote.isEmpty()) {
            errorMsg.add(ValidationError.VE001 + ".tuoteEntity");
            vr.setErrorMsg(errorMsg);

            logger.logValidationFailure(ValidationError.TE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(false, vr);
        }
        vr = validator.validate(tuoteToDto(optTuote.get()), false);
        if (vr.validated) {
            business.deleteTuote(optTuote.get().getId());
        }
        return new ValidateServiceResult<>(vr.validated, vr);
    }

    @Override
    public ValidateServiceResult<TuoteDto> updateTuote(Long id, TuoteDto dto) {
        var vr = validator.validateUpdate(dto, id);
        if (!vr.validated) {
            logger.logValidationFailure(ValidationError.TE101 + vr.getErrorMsg());
            return new ValidateServiceResult<>(null, vr);
        }

        var optTuote = business.updateTuote(id, dto);
        if (optTuote.isPresent()) {
            var tuoteDto = tuoteToDto(optTuote.get());
            return new ValidateServiceResult<>(tuoteDto, vr);
        }

        logger.logError(ValidationError.TE105);
        return new ValidateServiceResult<>(null, vr);
    }

    private TuoteDto tuoteToDto(TuoteEntity tuote) {
        var dto = new TuoteDto();
        dto.setId(tuote.getId());
        dto.setTuote(tuote.getTuote());
        dto.setOsasto(tuote.getOsasto());
        dto.setHinta(tuote.getHinta());
        return dto;
    }

}
