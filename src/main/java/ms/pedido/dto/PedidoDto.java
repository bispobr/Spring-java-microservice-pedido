package ms.pedido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ms.pedido.model.ItemPedido;

import java.util.List;

public record PedidoDto(@NotBlank String descricao, @NotNull List<ItemPedido> itens) {
}
