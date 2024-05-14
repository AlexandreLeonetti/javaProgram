import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        get().join(); // Wait for the asynchronous request to complete
    }

    static CompletableFuture<Void> get() {
        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // Build request URI with parameters
        String baseUrl = "https://api.apis.guru/v2/list.json";
        Map<String, String> params = new HashMap<>();
        params.put("q", "tesla");
        params.put("region", "US");
        String url = buildUrl(baseUrl, params);

        // Build request with headers
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
                .header("X-RapidAPI-Host", "yh-finance.p.rapidapi.com")
                .build();

        // Send asynchronous request
        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // Handle response asynchronously
        return futureResponse.thenAccept(response -> {
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode == 200) {
                System.out.println(responseBody);
            } else {
                System.err.println("Error: " + statusCode);
            }
        }).exceptionally(throwable -> {
            System.err.println("Request error: " + throwable.getMessage());
            return null;
        });
    }

    static String buildUrl(String baseUrl, Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        // Remove the last '&' character
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        return urlBuilder.toString();
    }
}
