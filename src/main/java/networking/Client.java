package networking;

import accounting.Request;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private InputStream inputStream = null;
    private BufferedReader bufferedReader = null;
    private DataOutputStream dataOutputStream = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            System.out.println("Cannot find host");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    private void sendRequest(Request request, String... args) {
        try {
            dataOutputStream.writeBytes(request.toString() + ((args.length > 0) ? "\n" + args[0] + "\n" : "\n"));
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Cannot send that request.");
        }
    }

    private String receiveRequest() {
        String line = "";
        try {
            line = bufferedReader.readLine();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return line;
    }

    public String sendAndReceiveRequest(Request request, String... args) {
        sendRequest(request, args);
        return receiveRequest();
    }

    public void sendShutdownRequest() {
        try {
            dataOutputStream.writeBytes("SHUTDOWN");
            dataOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Cannot send that request.");
        }
    }

    public void closeConnection() {
        try {
            inputStream.close();
            bufferedReader.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Unable to close connection! " + ex.getMessage());
        }
    }
}
