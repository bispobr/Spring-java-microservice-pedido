package ms.pedido.service;


import ms.pedido.model.ItemPedido;
import ms.pedido.model.Pedido;
import ms.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;


    @Autowired
    @InjectMocks
    private PedidoService pedidoService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void salvarPedido_pedidoComItens_DeveSalvarComSucesso() {

        Pedido pedido = new Pedido();
        ItemPedido item1 = new ItemPedido();
        ItemPedido item2 = new ItemPedido();
        pedido.setItens(List.of(item1, item2));

        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Pedido resultado = pedidoService.salvarPedido(pedido);


        assertNotNull(resultado);
        assertEquals(pedido, item1.getPedido());
        assertEquals(pedido, item2.getPedido());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void salvarPedido_pedidoSemItens_DeveSalvarMesmoSemItens() {

        Pedido pedido = new Pedido();
        pedido.setItens(null);

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido resultado = pedidoService.salvarPedido(pedido);


        assertNotNull(resultado);
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void salvarPedido_pedidoNulo_DeveLancarExcecao() {

        assertThrows(NullPointerException.class, () -> pedidoService.salvarPedido(null));
        verify(pedidoRepository, never()).save(any());
    }

    @Test
    void retornarTodosPedidos_pedidosExistem_DeveRetornarListaDePedidos() {

        List<Pedido> listaPedidos = List.of(new Pedido(), new Pedido());
        when(pedidoRepository.findAll()).thenReturn(listaPedidos);


        List<Pedido> resultado = pedidoService.retornarTodosPedidos();


        assertEquals(2, resultado.size());
        verify(pedidoRepository).findAll();
    }

    @Test
    void retornarTodosPedidos_semPedidos_DeveRetornarListaVazia() {

        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());


        List<Pedido> resultado = pedidoService.retornarTodosPedidos();

        assertTrue(resultado.isEmpty());
        verify(pedidoRepository).findAll();
    }

    @Test
    void retornarTodosPedidos_erroNoRepositorio_DeveLancarExcecao() {

        when(pedidoRepository.findAll()).thenThrow(new RuntimeException("Erro no banco"));


        assertThrows(RuntimeException.class, () -> pedidoService.retornarTodosPedidos());
        verify(pedidoRepository).findAll();
    }


}