import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main3 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path imagePath=Paths.get ("C:\\Users\\Soumili\\Desktop\\PBL IMAGES\\testImage1.png");
        
        // Prepare HTTP request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:8000/extract_text")).header("Content-Type", "multipart/form-data; boundary=" + "boundary")
                .POST(HttpRequest.BodyPublishers.ofInputStream(() -> {
                    try {
                        // Create the multipart/form-data body
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        PrintWriter writer = new PrintWriter(bos);
                        writer.println("--boundary");
                        writer.println("Content-Disposition: form-data; name=\"image\"; filename=\"" + imagePath.getFileName() + "\"");
                        writer.println("Content-Type: image/jpeg");
                        writer.println();
                        writer.flush();
                        Files.copy(imagePath, bos);
                        writer.println();
                        writer.println("--boundary--");
                        writer.flush();
                        return new ByteArrayInputStream(bos.toByteArray());
                    } 
                    catch (IOException e) 
                    {
                        e.printStackTrace();
                        return null;
                    }
                })).build();
        
        
        // Send the HTTP request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Print the extracted text from the response
        System.out.println("Extracted Text:");
        System.out.println(response.body());

        
    }
}
