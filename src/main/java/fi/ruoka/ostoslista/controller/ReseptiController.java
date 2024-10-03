package fi.ruoka.ostoslista.controller;

import fi.ruoka.ostoslista.dto.ReseptiDto;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/reseptit")
public class ReseptiController {

    @Autowired
    private ReseptiService reseptiService;

    @PostMapping
    public ResponseEntity<?> createResepti(@RequestBody ReseptiDto dto) {
        boolean savedResepti = reseptiService.createResepti(dto);
        System.out.println(dto);

        if (savedResepti) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<ReseptiDto>> getAllResepti() {
        List<ReseptiDto> reseptis = reseptiService.getAllResepti();
        return ResponseEntity.ok(reseptis);
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ReseptiDto> getReseptiById(@PathVariable Long id) {
        ReseptiDto resepti = reseptiService.getReseptiById(id);
        return ResponseEntity.ok(resepti);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResepti(@PathVariable Long id) {
        boolean deleted = reseptiService.deleteResepti(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
