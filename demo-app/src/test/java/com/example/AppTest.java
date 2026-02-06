package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class AppTest {

	@BeforeAll
    public static void setup() {
        Thread t = new Thread(() -> {
            try {
                App.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true); 
        t.start();

        try { Thread.sleep(3000); } catch (InterruptedException e) {}
    }

    @Test
    public void testFullFlow() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        double initialVisits = getVisits(client);

        HttpRequest requestApp = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .build();
        client.send(requestApp, HttpResponse.BodyHandlers.ofString());

        double newVisits = getVisits(client);
        
        System.out.println("Visites: " + initialVisits + " -> " + newVisits);
        assertEquals(initialVisits + 1, newVisits, "Le compteur doit s'incrémenter");
    }

    private double getVisits(HttpClient client) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/metrics"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        String body = response.body();
        for (String line : body.split("\n")) {
            if (line.startsWith("visites_total") && !line.startsWith("visites_total_created")) {
                return Double.parseDouble(line.split(" ")[1]);
            }
        }
        return 0;
    }
}