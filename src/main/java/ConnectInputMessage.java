import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ConnectInputMessage implements Runnable{
    private Socket serverConnect;
    private InputStream inputStreamServer;

    public Socket getServerConnect() {
        return serverConnect;
    }

    public void setServerConnect(Socket serverConnect) {
        this.serverConnect = serverConnect;
    }

    public InputStream getInputStreamServer() {
        return inputStreamServer;
    }

    public void setInputStreamServer(InputStream inputStreamServer) {
        this.inputStreamServer = inputStreamServer;
    }

    public ConnectInputMessage() throws IOException {
        serverConnect = new Socket("localhost",8887);
        inputStreamServer = serverConnect.getInputStream();

    }
    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStreamServer));

        String serverMassage;

        while (true) {
            try {
                serverMassage = in.readLine();
                if (serverMassage != null) {
                    System.out.println(serverMassage + "\n");
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

            PrintWriter out = null;
            BufferedReader inputUser = new BufferedReader(new InputStreamReader(System.in));

            String userMessage = null;

            do {
                System.out.println("Введіть повідомлення: ");
                try {
                    userMessage = inputUser.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    out = new PrintWriter(serverConnect.getOutputStream(), true);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                out.println(userMessage);
            } while (!"exit".equals(userMessage));
        }
    }

