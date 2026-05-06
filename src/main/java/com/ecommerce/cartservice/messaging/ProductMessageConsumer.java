package com.ecommerce.cartservice.messaging;

import com.ecommerce.cartservice.dto.ProductMessage;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.boot.json.JsonParseException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.exc.JsonNodeException;

@Component
public class ProductMessageConsumer {

    private final ObjectMapper objectMapper;

    public ProductMessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Listen to "product-queue" and process incoming product messages
     */
    @JmsListener(destination = "product-queue")
    public void receiveProductMessage(String message) {
        try {
            ProductMessage product = objectMapper.readValue(message, ProductMessage.class);
            System.out.println("📥 Received product message: " + product);
            System.out.println("   → Product '" + product.getName() + "' is now available (price: " + product.getPrice() + "€)");
            // In a real app, you could cache this product info locally
            // or update cart prices if a product price changed
        } catch (JsonNodeException e) {
            System.err.println("❌ Error deserializing product message: " + e.getMessage());
        }
    }
}