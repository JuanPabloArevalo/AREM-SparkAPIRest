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
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        get("/cuadrado/:id", (req, res) -> {
            String id = req.params(":id");
            try{
               long numero = Long.parseLong(id);
               numero = numero*numero;
               return String.valueOf(numero);
            }catch(NumberFormatException e){
                res.status(400);
                return "El siguiente número es inválido: "+id;
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
