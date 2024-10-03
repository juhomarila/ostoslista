package fi.ruoka.ostoslista.controller;

import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.service.ReseptiService;
import jakarta.annotation.security.PermitAll;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/reseptit")
public class ReseptiController {

    @Autowired
    private ReseptiService reseptiService;

    private final OstosListaLogger logger;

    @Autowired
    public ReseptiController(OstosListaLogger logger) {
        this.logger = logger;
    }

    @PostMapping
    public ResponseEntity<?> createResepti(@RequestBody ReseptiDto dto) {
        logger.postLogStart("createResepti");
        var vsr = reseptiService.createResepti(dto);
        logger.postLogEnd("createResepti");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<ReseptiDto>> getAllResepti() {
        logger.getLogStart("getAllResepti");
        var vsr = reseptiService.getAllResepti();
        logger.getLogEnd("getAllResepti");
        return vsr.getT().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(vsr.getT());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReseptiById(@PathVariable Long id) {
        logger.getLogStart("getReseptiById");
        var vsr = reseptiService.getReseptiById(id);
        logger.getLogEnd("getReseptiById");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResepti(@PathVariable Long id) {
        logger.deleteLogStart("deleteResepti");
        var vsr = reseptiService.deleteResepti(id);
        logger.deleteLogEnd("deleteResepti");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateResepti(@PathVariable Long id, @RequestBody ReseptiDto dto) {
        logger.putLogStart("updateResepti");
        var vsr = reseptiService.updateResepti(dto);
        logger.putLogEnd("updateResepti");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
