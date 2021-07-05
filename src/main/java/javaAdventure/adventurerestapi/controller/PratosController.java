package javaAdventure.adventurerestapi.controller;

import javaAdventure.adventurerestapi.model.Prato;
import javaAdventure.adventurerestapi.service.PratosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/prato")
public class PratosController {

    @Autowired
    private PratosService pratosService;

    @GetMapping(path = "/getall")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Prato>> consultarTodos(){
        return new ResponseEntity<>(this.pratosService.consultarTodos(), HttpStatus.OK);
    }

    @GetMapping(path = "/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Prato> consultar(@PathVariable("codigo") Integer codigo) {
        return pratosService.consultar(codigo);
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Prato salvar(@RequestBody Prato prato){
        return pratosService.salvar(prato);
    }

    @DeleteMapping(path = "/delete/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable("codigo") Integer codigo){
            pratosService.deletar(codigo);
    }

    @PutMapping(path = "/edit/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Prato> editar(@PathVariable("codigo") Integer codigo, @RequestBody Prato prato){
        Prato novoPrato = pratosService.consultar(codigo)
                .orElseThrow(NullPointerException::new);
        novoPrato.setNome(prato.getNome());
        novoPrato.setIngredientes(prato.getIngredientes());
        novoPrato.setValor(prato.getValor());

        final Prato pratoEditado = pratosService.salvar(novoPrato);
        return ResponseEntity.ok().body(pratoEditado);
    }
}
