package com.github.dmagare.families;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dmagare.families.repository.ExpenseRepository;
import com.github.dmagare.families.service.ExpenseService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import reactor.netty.http.server.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.datastax.oss.driver.api.core.CqlSession;
import java.net.URISyntaxException;
import java.nio.file.Path;

import java.nio.file.Paths;



public class BudgetApp {
    public static void main(String[] args) throws URISyntaxException {
        Path indexHTML = Paths.get(BudgetApp.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(BudgetApp.class.getResource("/error.html").toURI());

        CqlSession session = CqlSession.builder().build();
        ExpenseRepository expenseRepository = new ExpenseRepository(session);
        ExpenseService expenseService = new ExpenseService(expenseRepository);

        HttpServer.create()
                .port(8080)
                .route(routes ->
                        routes.get("/expenses", (request, response) ->
                                        response.send(expenseService.getAll().map(BudgetApp::toByteBuf)
                                                .log("http-server")))
                                .get("/expenses/{param}", (request, response) ->
                                        response.send(expenseService.get(request.param("param")).map(BudgetApp::toByteBuf)
                                                .log("http-server")))
                                .get("/", (request, response) ->
                                        response.sendFile(indexHTML))
                                .get("/error", (request, response) ->
                                        response.status(404).addHeader("Message", "Goofed")
                                                .sendFile(errorHTML))
                )
                .bindNow()
                .onDispose()
                .block();
    }

      static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

      static ByteBuf toByteBuf(Object o) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
                    try{
                        OBJECT_MAPPER.writeValue(out, o);
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                    return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
        }

    }

