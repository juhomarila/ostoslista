package fi.ruoka.ostoslista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import fi.ruoka.ostoslista.service.OstosListaService;
import fi.ruoka.ostoslista.service.OstosService;
import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.logging.OstosListaLogger;

@RestController
@RequestMapping("/api/ostoslista")
public class OstosListaController {

    @Autowired
    private OstosListaService ostosListaService;

    @Autowired
    private OstosService ostosService;

    private final OstosListaLogger logger;

    @Autowired
    public OstosListaController(OstosListaLogger logger) {
        this.logger = logger;
    }

    @PostMapping
    public ResponseEntity<?> createOstosLista(@RequestBody OstosListaDto dto) {
        logger.postLogStart("createOstosLista");
        var vsr = ostosListaService.createOstosLista(dto);
        logger.postLogEnd("createOstosLista");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<?> getAllOstosLista() {
        logger.getLogStart("getAllOstosLista");
        var vsr = ostosListaService.getAllOstosLista();
        logger.getLogEnd("getAllOstosLista");
        return vsr.getT().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(vsr.getT());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOstosListaById(@PathVariable Long id) {
        logger.getLogStart("getOstosListaById");
        var vsr = ostosListaService.getOstosListaById(id);
        logger.getLogEnd("getOstosListaById");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOstosLista(@RequestBody OstosListaDto dto) {
        logger.putLogStart("updateOstosLista");
        var vsr = ostosListaService.updateOstosLista(dto);
        logger.putLogEnd("updateOstosLista");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOstosLista(@PathVariable Long id) {
        logger.deleteLogStart("deleteOstosLista");
        var vsr = ostosListaService.deleteOstosLista(id);
        logger.deleteLogEnd("deleteOstosLista");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/ostos/{id}")
    public ResponseEntity<?> deleteOstos(@PathVariable Long id) {
        logger.deleteLogStart("deleteOstos");
        var vsr = ostosService.deleteOstos(id);
        logger.deleteLogEnd("deleteOstos");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/ostos/{id}")
    public ResponseEntity<?> addOstos(@PathVariable Long id, @RequestBody OstosDto dto) {
        logger.postLogStart("addOstos");
        var vsr = ostosService.addOstos(id, dto);
        logger.postLogEnd("addOstos");
        return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                        vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
