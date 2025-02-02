package fi.ruoka.ostoslista.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fi.ruoka.ostoslista.dto.OstosDto;
import fi.ruoka.ostoslista.dto.OstosListaDto;
import fi.ruoka.ostoslista.dto.ReseptiDto;
import fi.ruoka.ostoslista.logging.OstosListaLogger;
import fi.ruoka.ostoslista.service.OstosListaService;
import fi.ruoka.ostoslista.service.OstosService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ostoslista")
@CrossOrigin("*")
public class OstosListaController {

        @Autowired
        private OstosListaService ostosListaService;

        @Autowired
        private OstosService ostosService;

        private final OstosListaLogger logger;

        public OstosListaController(OstosListaLogger logger) {
                this.logger = logger;
        }

        @PostMapping
        public ResponseEntity<?> createOstosLista(@Valid @RequestBody OstosListaDto dto) {
                logger.postLogStart("createOstosLista");
                var vsr = ostosListaService.createOstosLista(dto);
                logger.postLogEnd("createOstosLista");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST
                                                                : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @PostMapping("/resepti")
        public ResponseEntity<?> reseptiToOstosLista(@Valid @RequestBody ReseptiDto dto) {
                logger.postLogStart("reseptiToOstosLista");
                var vsr = ostosListaService.reseptiToOstosLista(dto);
                logger.postLogEnd("reseptiToOstosLista");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST
                                                                : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @PostMapping("/resepti/{id}")
        public ResponseEntity<?> reseptiToExistingOstosLista(@Valid @RequestBody ReseptiDto dto,
                        @PathVariable Long id) {
                logger.postLogStart("reseptiToExistingOstosLista");
                var vsr = ostosListaService.reseptiToExistingOstosLista(dto, id);
                logger.postLogEnd("reseptiToExistingOstosLista");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST
                                                                : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @GetMapping
        public ResponseEntity<?> getAllOstosLista() {
                logger.getLogStart("getAllOstosLista");
                var vsr = ostosListaService.getAllOstosLista();
                logger.getLogEnd("getAllOstosLista");
                return vsr.getT().isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(vsr.getT());
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> getOstosListaById(@PathVariable Long id) {
                logger.getLogStart("getOstosListaById");
                var vsr = ostosListaService.getOstosListaById(id);
                logger.getLogEnd("getOstosListaById");
                return vsr.getVr().validated ? new ResponseEntity<>(HttpStatus.OK)
                                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping("/{id}")
        public ResponseEntity<?> updateOstosLista(@PathVariable Long id, @Valid @RequestBody OstosListaDto dto) {
                logger.putLogStart("updateOstosLista");
                var vsr = ostosListaService.updateOstosLista(id, dto);
                logger.putLogEnd("updateOstosLista");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
        }

        @PutMapping("/{id}/valmis")
        public ResponseEntity<?> setOstosListaValmis(@PathVariable Long id) {
                logger.putLogStart("setOstosListaValmis");
                var vsr = ostosListaService.setOstosListaValmis(id);
                logger.putLogEnd("setOstosListaValmis");
                return vsr.getT() ? new ResponseEntity<>(HttpStatus.OK)
                                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<?> deleteOstosLista(@PathVariable Long id) {
                logger.deleteLogStart("deleteOstosLista");
                var vsr = ostosListaService.deleteOstosLista(id);
                logger.deleteLogEnd("deleteOstosLista");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
        }

        @DeleteMapping("/ostos/{id}")
        public ResponseEntity<?> deleteOstos(@PathVariable Long id) {
                logger.deleteLogStart("deleteOstos");
                var vsr = ostosService.deleteOstos(id);
                logger.deleteLogEnd("deleteOstos");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST
                                                                : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @PostMapping("/ostos/{id}")
        public ResponseEntity<?> addOstos(@PathVariable Long id, @Valid @RequestBody OstosDto dto) {
                logger.postLogStart("addOstos");
                var vsr = ostosService.addOstos(id, dto);
                logger.postLogEnd("addOstos");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST
                                                                : HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @GetMapping("/ostos/{id}")
        public ResponseEntity<?> getOstosById(@PathVariable Long id) {
                logger.getLogStart("getOstosById");
                var vsr = ostosService.getOstosById(id);
                logger.getLogEnd("getOstosById");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
        }

        @PutMapping("/ostos/{id}")
        public ResponseEntity<?> updateOstosById(@PathVariable Long id, @Valid @RequestBody OstosDto dto) {
                logger.getLogStart("updateOstosById");
                var vsr = ostosService.updateOstosById(id, dto);
                logger.getLogEnd("updateOstosById");
                return vsr.getVr().validated ? new ResponseEntity<>(vsr.getT(), HttpStatus.OK)
                                : new ResponseEntity<>(vsr.getVr().getErrorMsg(),
                                                vsr.getVr().validated ? HttpStatus.BAD_REQUEST : HttpStatus.NOT_FOUND);
        }

}
