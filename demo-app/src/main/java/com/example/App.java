package com.example;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import io.micrometer.prometheusmetrics.PrometheusConfig;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;


public class App {
    public static void main(String[] args) throws Exception {
        PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        HTTPServer metricServer = HTTPServer.builder()
        	    .inetAddress(new java.net.InetSocketAddress("0.0.0.0", 8081).getAddress()) 
        	    .port(8081)
        	    .registry(prometheusRegistry.getPrometheusRegistry())
        	    .buildAndStart();

        System.out.println(" Métriques sur http://localhost:8081/metrics");

        HttpServer appServer = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);
        appServer.createContext("/", exchange -> {
            prometheusRegistry.counter("visites_total").increment();
            String resp = "Hello World - MIAGE DevOps Demo";
            exchange.sendResponseHeaders(200, resp.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(resp.getBytes());
            }
        });
        appServer.start();
    }
}