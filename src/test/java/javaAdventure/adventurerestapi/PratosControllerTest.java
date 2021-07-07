package javaAdventure.adventurerestapi;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javaAdventure.adventurerestapi.model.Prato;
import javaAdventure.adventurerestapi.repository.PratosRepository;
import javaAdventure.adventurerestapi.service.PratosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PratosControllerTest {

    @InjectMocks
    PratosService pratosService;

    @Mock
    PratosRepository pratosRepository;

    @BeforeEach
    public void setup(){
        standaloneSetup(this.pratosService);
    }

    @Test
    public void retornarSucesso_SalvarPrato(){
        Prato prato = new Prato(2, "Ovo frito", "ovo, óleo", 10.99, false);
        when(pratosRepository.save(any(Prato.class))).thenReturn(prato);
        Prato pratoAtual = pratosService.salvar(prato);

        assertNotNull(pratoAtual.getCodigo());
        assertEquals(prato.getCodigo(), pratoAtual.getCodigo());

        verify(pratosRepository, atLeastOnce()).save(ArgumentMatchers.any());
    }

    @Test
    public void retornarSucesso_BuscarPrato(){
        when(pratosRepository.findById(anyInt())).thenReturn(java.util.Optional.of(retornaPrato()));

        Optional<Prato> prato = pratosService.consultar(2);

        assertEquals(retornaPrato().getNome(), prato.get().getNome());
        verify(pratosRepository, atLeastOnce()).findById(ArgumentMatchers.any());
    }

    @Test
    public void retornaSucesso_BuscarTodosPratos(){
        List<Prato> pratos = new ArrayList<>();
        when(pratosRepository.findAll()).thenReturn(pratos);

        pratosService.consultarTodos();

        verify(pratosRepository, atLeastOnce()).findAll();
    }

    @Test
    public void retornaSucesso_DeletarPrato(){
        doNothing().when(pratosRepository).deleteById(any());

        pratosService.deletar(anyInt());

        verify(pratosRepository, times(1)).deleteById(any());
    }

    private Prato retornaPrato(){
        Prato prato = new Prato(2, "Ovo frito", "ovo, óleo", 10.99, false);
        return prato;
    }
}
