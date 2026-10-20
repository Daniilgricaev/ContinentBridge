package com.orangemask.continentBridge;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class GetRequest {
    public static void main(String[] args) {
        try{
            HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(10)).build();
            URI uri = URI.create("http://localhost:8080");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .timeout(Duration.ofSeconds(12))
                    .header("Accept", "orangemask/json")
                    .header("User-Agent", "ContinentBridge/0.0")
                    .build();
            HttpResponse<String>responce = client.send(request, HttpResponse.BodyHandlers.ofString());

            int status = responce.statusCode();
            if(status >= 200 && status < 300){
                System.out.println("Status: " + status);

                String body = responce.body();
                System.out.println("Raw JSON:\n" + body);
                System.out.println("Заголовок: " + extract(body, "\"title\":"));
                System.out.println("Тело: " + extract(body, "\"body\":"));
            }else{
                System.out.println("Error: " + status + ", body: " + responce.body());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static String extract(String json, String key){
        int i = json.indexOf(key);
        if(i < 0){
            return "<NOT FOUND>";
        }
        int s = json.indexOf('\"', i + key.length()) + 1;
        int e = json.indexOf('\"', s);
        return json.substring(s,e);
    }
}
