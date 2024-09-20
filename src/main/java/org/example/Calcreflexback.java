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
    public static String computeMathCommand(String command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String mathcommand = command.split("=")[1];
        String[] parts = mathcommand.split("\\(");
        String methodName = parts[0];
        String[] params = parts[1].replace(")", "").split(",");

        Double[] paramValues = new Double[params.length];

        for (int i = 0; i < params.length; i++) {
            paramValues[i] = Double.parseDouble(params[i].trim());
        }

        if (methodName.equals("add")) {
            Double result = paramValues[0] + paramValues[1];
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("subtract")) {
            Double result = paramValues[0] - paramValues[1];
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("multiply")) {
            Double result = paramValues[0] * paramValues[1];
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("divide")) {
            if (paramValues[1] == 0) {
                return "{\"error\": \"Division by zero\"}";
            }

            Double result = paramValues[0] / paramValues[1];
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("max")) {
            Double result = Math.max(paramValues[0], paramValues[1]);
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("min")) {
            Double result = Math.min(paramValues[0], paramValues[1]);
            return "{\"result\": " + result.toString() + "}";
        } else if (methodName.equals("bbl")) {
            Double[] sortedArray = bblsort(paramValues);
            return arrayToJson(sortedArray);
        }

        Class<?> c = Math.class;
        Class<?>[] parameterTypes = new Class<?>[paramValues.length];
        for (int i = 0; i < paramValues.length; i++) {
            parameterTypes[i] = double.class;
        }

        Method method = c.getMethod(methodName, parameterTypes);
        Object result = method.invoke(null, (Object[]) paramValues);
        return "{\"result\": " + result.toString() + "}";
    }


    public static String arrayToJson(Double[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"result\": [");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }

    public static Double[] bblsort(Double[] bblarray) {
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
