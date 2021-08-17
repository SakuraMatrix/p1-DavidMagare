package com.github.dmagare.families;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.dmagare.families.repository.ExpenseRepository;
import com.github.dmagare.families.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ComponentScan
public class ExpenseConfig {
    @Autowired
    ExpenseService expenseService;

    @Bean
    public CqlSession session(){
        return CqlSession.builder().build();
    }

    @Bean
    public DisposableServer server() throws URISyntaxException {
        Path indexHTML = Paths.get(BudgetApp.class.getResource("/index.html").toURI());
        Path errorHTML = Paths.get(BudgetApp.class.getResource("/error.html").toURI());
       // ExpenseService expenseService = service();
        return HttpServer.create()
                .port(8080)
                .route(routes ->
                        routes.get("/expenses", (request, response) ->
                                        response.send(expenseService.getAll().map(BudgetApp::toByteBuf)
                                                .log("http-server")))
                                .post("/expenses", (request, response) ->
                                                response.send(request.receive().asString()
                                                        .map(BudgetApp::purseExpense)
                                                        .map(ExpenseService::create)
                                                        .map(BudgetApp::toByteBuf))
                                      //  .log("http-serve"))
                                )
                                .get("/expenses/{param}", (request, response) ->
                                        response.send(expenseService.get(request.param("param")).map(BudgetApp::toByteBuf)
                                                .log("http-server")))
                                .get("/", (request, response) ->
                                        response.sendFile(indexHTML))
                                .get("/error", (request, response) ->
                                        response.status(404).addHeader("Message", "Goofed")
                                                .sendFile(errorHTML))
                )
                .bindNow();
    }
}
