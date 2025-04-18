package ms.pedido.service;

import lombok.extern.slf4j.Slf4j;
import ms.pedido.model.ItemPedido;
import ms.pedido.model.Pedido;
import ms.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido salvarPedido(Pedido pedido){
        if (pedido.getItens() != null){
            for (ItemPedido item: pedido.getItens()){
                item.setPedido(pedido);
            }
        }
        log.info("Pedido Salvo com sucesso id:");
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> retornarTodosPedidos(){
        log.info("Listagem de todos os pedidos Bem Sucedida");
        return pedidoRepository.findAll();

    }
}
