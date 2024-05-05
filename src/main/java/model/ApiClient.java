package model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ApiClient {

    private OkHttpClient client;
    private String apiKey;

    public ApiClient() throws IOException {
        this.client = new OkHttpClient();
        this.apiKey = readApiKeyFromFile("application.properties");
    }

    // Метод для чтения API-KEY из файла
    private String readApiKeyFromFile(String filePath) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
        if (is == null) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = reader.readLine();
        reader.close();
        // Извлечение API-KEY из строки
        return line.split(":")[1].trim().replace("\"", "");
    }

    // Метод для получения API-KEY
    public String getApiKey() {
        return this.apiKey;
    }
    
    public String fetchDataFromApi(String apiUrl, String headerName, String headerValue) throws IOException {
        Request request = new Request.Builder()
            .url(apiUrl)
            .addHeader(headerName, headerValue) // Добавление заголовка
            .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

