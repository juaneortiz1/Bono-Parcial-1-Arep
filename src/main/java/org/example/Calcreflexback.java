package org.example;

import java.io.IOException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;


public class Calcreflexback
{
    public static void main( String[] args ) throws IOException, URISyntaxException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean isFirstLine =true;
            String firstLine = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (isFirstLine) {
                    firstLine = inputLine;
                    isFirstLine = false;
                    break;
                }
                if (!in.ready()){
                    break;
                }
            }

            URI requrl = getReqURL(firstLine);

            if (requrl.getPath().startsWith("/compreflex")){
                String result = computeMathCommand(requrl.toString());
                outputLine=  "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        +result;
            }
            else {
                outputLine = htmlClient();
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();

    }
    public static String htmlClient(){
        String htmlCode ="HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"+
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                        "Method not found"+
                "         </body>\n" +
                "</html>";
        return htmlCode;
    }
    public static URI getReqURL(String firstline) throws MalformedURLException, URISyntaxException {
        String rurl = firstline.split(" ")[1];
        return new URI(rurl) ;
    }
    public  static  String computeMathCommand(String command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String mathcommand = command.split("=")[1];
        System.out.println(mathcommand);
        Class c = Math.class;
        Class[] parameterTypes = {double.class};
        Method m = c.getDeclaredMethod("abs", parameterTypes);
        Object[] params = {-2.0};
        String resp = m.invoke(mathcommand, (Object) params).toString();

        return resp;
    }

    public static double[] bblsort(double[] bblarray) {
        boolean change;
        for (int i = 0; i < bblarray.length - 1; i++) {
            change = false;
            for (int j = 0; j < bblarray.length - 1 - i; j++) {
                if (bblarray[j] > bblarray[j + 1]) {
                    double temp = bblarray[j];
                    bblarray[j] = bblarray[j + 1];
                    bblarray[j + 1] = temp;
                    change = true;
                }
            }
            if (!change) {
                break;
            }
        }
        return bblarray;
    }
}
