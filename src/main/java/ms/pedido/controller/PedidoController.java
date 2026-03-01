package ms.pedido.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ms.pedido.dto.PedidoDto;
import ms.pedido.model.Pedido;
import ms.pedido.service.PedidoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    PedidoService pedidoService;

    @Value("${broker.queue.processamento.name}")
    private String chaveDeRoteamento;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos pedidos")
    @ApiResponse(responseCode = "200", description = "pedido criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public String criarPedido (@Valid @RequestBody PedidoDto pedidodto){
        log.info("Solicitação de criação de pedido Recebida");
        var pedido = new Pedido();
        BeanUtils.copyProperties(pedidodto,pedido);
        Pedido pedidoSalvo = pedidoService.salvarPedido(pedido);
        rabbitTemplate.convertAndSend("",chaveDeRoteamento,pedidoSalvo.getDescricao());
        return "Pedido Salvo e enviado para processamento :" +pedido.getDescricao();
    }

    @GetMapping
    @Operation(description = "Endpoint responsável listar todo os pedidos cadastrados")
    @ApiResponse(responseCode = "200", description = "Listagem Bem sucedida")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public List<Pedido> listarPedido(){
        log.info("Solicitação de listagem de pedido Recebida");
        return pedidoService.retornarTodosPedidos();
    }
}
