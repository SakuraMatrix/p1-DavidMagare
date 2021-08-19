package com.github.dmagare.families;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dmagare.families.budget.Expense;
import com.github.dmagare.families.repository.ExpenseRepository;
import com.github.dmagare.families.service.ExpenseService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.datastax.oss.driver.api.core.CqlSession;
import java.net.URISyntaxException;
import java.nio.file.Path;

import java.nio.file.Paths;
public class BudgetApp {
    private static final Logger Log =(ch.qos.logback.classic.Logger) LoggerFactory.getLogger("BudgetApp");
    static final ObjectMapper OBJECT_MAPPER= new ObjectMapper();
    public static void main(String[] args) throws URISyntaxException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExpenseConfig.class);
        applicationContext.getBean(DisposableServer.class)
                .onDispose()
                .block();
    }
      static ByteBuf toByteBuf(Object o) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
                    try{
                        OBJECT_MAPPER.writeValue(out, o);
                    } catch (IOException ex){
                        ex.printStackTrace();
                    }
                    return ByteBufAllocator.DEFAULT.buffer().writeBytes(out.toByteArray());
        }
        static Expense purseExpense(String str){
            Expense expense = null;
            try {
                expense = OBJECT_MAPPER.readValue(str, Expense.class);
            } catch (JsonProcessingException ex){
                String[] params = str.split("&");
                int expense_id =Integer.parseInt(params[0].split("=")[1]);
                String expense_name = params[1].split("=")[1];
                double expense_amount = Double.parseDouble(params[2].split("=")[1]);
                expense = new Expense(expense_id,expense_name,expense_amount);
            }
            return expense;

        }
    }

