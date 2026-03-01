package ms.pedido.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ms.pedido.dto.PedidoDto;
import ms.pedido.model.Pedido;
import ms.pedido.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoController pedidoController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoController).build();
        ReflectionTestUtils.setField(pedidoController, "chaveDeRoteamento", "fila.processamento");
    }

    @Test
    void criarPedido_dadosValidos_pedidoCriadoComSucesso() {

        PedidoDto pedidoDto = new PedidoDto("Pedido Teste",null);


        Pedido pedidoSalvo = new Pedido();
        pedidoSalvo.setDescricao("Pedido Teste");

        when(pedidoService.salvarPedido(any(Pedido.class))).thenReturn(pedidoSalvo);


        String resposta = pedidoController.criarPedido(pedidoDto);


        assertEquals("Pedido Salvo e enviado para processamento :Pedido Teste", resposta);
        verify(pedidoService).salvarPedido(any(Pedido.class));
        verify(rabbitTemplate).convertAndSend("", "fila.processamento", "Pedido Teste");
    }

    @Test
    void criarPedido_erroAoSalvarPedido_deveLancarExcecaoInterna() {

        PedidoDto pedidoDto = new PedidoDto("Pedido Teste",null);

        when(pedidoService.salvarPedido(any(Pedido.class)))
                .thenThrow(new RuntimeException("Falha no serviço"));


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoController.criarPedido(pedidoDto);
        });

        assertEquals("Falha no serviço", exception.getMessage());
        verify(pedidoService).salvarPedido(any(Pedido.class));

    }

    @Test
    void listarPedido_existemPedidos_retornaListaComPedidos() {

        Pedido pedido1 = new Pedido();
        pedido1.setDescricao("Pedido 1");

        Pedido pedido2 = new Pedido();
        pedido2.setDescricao("Pedido 2");

        List<Pedido> pedidos = List.of(pedido1, pedido2);

        when(pedidoService.retornarTodosPedidos()).thenReturn(pedidos);

        List<Pedido> resultado = pedidoController.listarPedido();

        assertEquals(2, resultado.size());
        assertEquals("Pedido 1", resultado.getFirst().getDescricao());
        verify(pedidoService).retornarTodosPedidos();
    }
}