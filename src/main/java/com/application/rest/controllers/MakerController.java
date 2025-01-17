package com.application.rest.controllers;

import com.application.rest.dtos.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.services.IMakerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/maker")
public class MakerController {

    private IMakerService makerService;

    public MakerController(IMakerService makerService) {
        this.makerService = makerService;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findById(id);

        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            MakerDTO makerDTO = new MakerDTO();
            makerDTO.setId(maker.getId());
            makerDTO.setName(maker.getName());
            makerDTO.setProducts(maker.getProducts());
            return ResponseEntity.status(HttpStatus.OK).body(makerDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<MakerDTO> makerDTOList = makerService.findAll()
                .stream()
                .map(maker -> {
                    MakerDTO makerDTO = new MakerDTO();
                    makerDTO.setId(maker.getId());
                    makerDTO.setName(maker.getName());
                    makerDTO.setProducts(maker.getProducts());
                    return makerDTO;
                })
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(makerDTOList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {
        if (makerDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        makerService.save(Maker.builder()
                .name(makerDTO.getName())
                .build());
        return ResponseEntity.created(new URI("/api/v1/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO) {
        Optional<Maker> makerOptional = makerService.findById(id);

        if (makerOptional.isPresent()) {
            Maker maker = makerOptional.get();
            maker.setName(makerDTO.getName());
            makerService.save(maker);
            return ResponseEntity.status(HttpStatus.OK).body("Registro Actualizado");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (id != null) {
            makerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Registro Eliminado");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
