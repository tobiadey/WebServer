import java.io.*;
import java.net.*;

public class Webserver {
    public static void main(String[] args) throws IOException{
        //Innit Server Socket on port 8080
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Web server is running on port " + port);
    
        //Continuously accept client connections
        while(true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress());

            // Create a new thread to handle the client request
            Thread thread = new Thread(()->{
                try {
                    handleClientRequest(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            }    
    }
    private static void handleClientRequest(Socket clientSocket)throws IOException{
        // read the request
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        String line;
        StringBuilder request = new StringBuilder();
        // Parse the HTTP Request 
        // Extract the method (GET, POST, etc.), path, and headers from the HTTP request.
        while ((line=reader.readLine()) != null && !line.isEmpty()){
            request.append(line).append("\r\n");
        }
        System.out.println("Received request:\n" + request);

        // return a static HTML page for a GET request
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        response += "<html><body><h1>Hello, World From Webserver!</h1></body></html>";
        writer.write(response);
        writer.flush();

        reader.close();
        writer.close();
        clientSocket.close();
    }

}



    // Tested using Postman - GET request sent to http://localhost:8080

    // Improve and Expand:
    // Implement error handling for cases like invalid requests, file not found, or internal server errors.
    // Experiment with handling different HTTP methods (e.g., POST, PUT, DELETE).
    // Implement support for serving static files (HTML, CSS, images, etc.).
    // Add support for dynamic content generation using servlets or other Java technologies.
