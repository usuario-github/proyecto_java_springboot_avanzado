package com.pepito.InventarioService.Configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import principalcommons.PedidoEvent;

@Component
public class PedidoEventListener {
    private static final Logger logger = LoggerFactory.getLogger(PedidoEventListener.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handlePedidoCreated(PedidoEvent pedidoEvent) {
        logger.info("Mensaje recibido en {}: {}", RabbitMQConfig.QUEUE, pedidoEvent);
        logger.info("Inventario: descontar {} unidad(es) del producto {} (pedido {})",
                pedidoEvent.getCantidad(), pedidoEvent.getProductoId(), pedidoEvent.getPedidoId());
    }
}
