package javaAdventure.adventurerestapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javaAdventure.adventurerestapi.model.Prato;
import javaAdventure.adventurerestapi.repository.PratosRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PratosService {

    @Autowired
    private PratosRepository pratosRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Prato> consultarTodos(){
        return new ArrayList<>(this.pratosRepository.findAll());
    }

    public Optional<Prato> consultar(Integer codigo){
        return this.pratosRepository.findById(codigo);
    }

    public Prato salvar(Prato prato){
        final String mensagem = StringConverter(prato);
        kafkaTemplate.send("prato", mensagem);
        return pratosRepository.save(prato);
    }

    public void deletar(Integer codigo){
        pratosRepository.deleteById(codigo);
    }

    private String StringConverter(Prato prato){
        try{
            return objectMapper.writeValueAsString(prato);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
