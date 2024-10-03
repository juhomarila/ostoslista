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

@RestController
@RequestMapping("/api/ostoslista")
public class OstosListaController {

    @Autowired
    private OstosListaService ostosListaService;

    @Autowired
    private OstosService ostosService;

    @PostMapping
    public ResponseEntity<?> createOstosLista(@RequestBody OstosListaDto dto) {
        boolean savedOstosLista = ostosListaService.createOstosLista(dto);
        System.out.println(dto);

        if (savedOstosLista) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> getAllOstosLista() {
        return ResponseEntity.ok(ostosListaService.getAllOstosLista());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OstosListaDto> getOstosListaById(@PathVariable Long id) {
        OstosListaDto ostosLista = ostosListaService.getOstosListaById(id);
        return ResponseEntity.ok(ostosLista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOstosLista(@PathVariable Long id, @RequestBody OstosListaDto dto) {
        boolean updatedOstosLista = ostosListaService.updateOstosLista(id, dto);
        if (updatedOstosLista) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOstosLista(@PathVariable Long id) {
        boolean deletedOstosLista = ostosListaService.deleteOstosLista(id);
        if (deletedOstosLista) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/ostos/{id}")
    public ResponseEntity<?> deleteOstos(@PathVariable Long id) {
        boolean deletedOstos = ostosService.deleteOstos(id);
        if (deletedOstos) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/ostos/{id}")
    public ResponseEntity<?> addOstos(@PathVariable Long id, @RequestBody OstosDto dto) {
        boolean addedOstos = ostosService.addOstos(id, dto);
        if (addedOstos) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
