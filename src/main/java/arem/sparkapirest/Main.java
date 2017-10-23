/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arem.sparkapirest;

/**
 *
 * @author JuanArevaloMerchan
 */
import java.util.HashMap;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;
import static spark.Spark.*;

public class Main {

    private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();

    static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }

    public final static void apply() {
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                corsHeaders.forEach((key, value) -> {
                    response.header(key, value);
                });
            }
        };
        Spark.after(filter);
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Main.apply();
        get("/cuadrado/:id", (req, res) -> {
            String id = req.params(":id");

            try {
                long numero = Long.parseLong(id);
                numero = numero * numero;
                return String.valueOf(numero);

            } catch (NumberFormatException e) {
                res.status(400);
                return "El siguiente número es inválido: " + id;
            }
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}
