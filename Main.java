import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

    public static void main(String[] args) {
        String location;
        String apiKey;
        if (args.length < 2) {
            System.err.print("Invalid number of arguments\n");
            return;
        }

        location = args[1];
        apiKey = args[0];
        String serverResponse;
        try {
            serverResponse = loadContent(location, apiKey);
            if (!serverResponse.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")){
                System.err.println(serverResponse);
                return;
            }

            Document xmlResult = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(serverResponse)));
            printResult(xmlResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
        @brief Making HTTP request and reading the response
        @arg location name of location from where we are looking for weather dat
        @arg apiKey API key to the OpenWeatherMap.org
        @return String containg the response
    */
    private static String loadContent(String location, String apiKey) throws Exception {
        String result = "";
        String mode = "xml";
        String units = "metrics";
        String language = "en";
        String path = "/data/2.5/weather?q=" + location + "&mode="+mode+"&units="+ units + "&lang=" + language + "&APPID=" + apiKey;

        try (Socket s = new Socket("api.openweathermap.org", 80)) {
            //Instantiates a new PrintWriter passing in the sockets output stream
            PrintWriter wtr = new PrintWriter(s.getOutputStream());

            //Prints the request string to the output stream
            wtr.println("GET " + path + " HTTP/1.1");
            wtr.println("Host: api.openweathermap.org");
            wtr.println("");
            wtr.flush();

            //Creates a BufferedReader that contains the server response
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //loads header and jumps over 10 lines.
            ///na 4 řádku se nachází delka odpovědi, kterou načtu
            String contentLength = "";
            for (int i = 0; i<11; i++){
                String a = bufRead.readLine();
                //System.out.println(a);
                if (a.contains("Content-Length:")){
                    contentLength += a;
                } else if(a.contains("HTTP/1.1")){
                    if (Integer.parseInt(a.split(" ")[1]) != 200){
                        result = a.replaceAll("HTTP/1\\.1 ","");
                        return result;
                    }
                }
            }
            //osekávám informaci o delce odpovědi od všeho textu, abych měl jen číslo
            contentLength = contentLength.replaceAll("[^0-9]","");

            int count = Integer.parseInt(contentLength)-1; //off by one korekce
            while (count >= 0) {
                result += (char) bufRead.read();
                count--;
            }

            //(první dva znaky result jsou ukoncení)
            //Closes out buffer and writer
            bufRead.close();
            wtr.close();
        }
        //System.out.println(result);
        return result;
    }

    /**
    @brief function grabs data from XML Document and prints it.
    @arg xml Weather date from OpenWeatherMap.org in XML format
    */
    private static void printResult(Document xml) {
        Element cityNode = (Element) xml.getElementsByTagName("city").item(0);
        System.out.println (cityNode.getAttribute("name"));

        Element cloudsNode = (Element) xml.getElementsByTagName("clouds").item(0);
        System.out.println ("overcast "+cloudsNode.getAttribute("name"));

        Element temperatureNode = (Element) xml.getElementsByTagName("temperature").item(0);
        System.out.println ("temp:"+temperatureNode.getAttribute("value") + "°C");

        Element humidityNode = (Element) xml.getElementsByTagName("humidity").item(0);
        System.out.println ("humidity:"+ humidityNode.getAttribute("value") + humidityNode.getAttribute("unit"));

        Element pressureNode = (Element) xml.getElementsByTagName("pressure").item(0);
        System.out.println ("preassure:" + pressureNode.getAttribute("value") + pressureNode.getAttribute("unit"));

        Element windSpeedNode = (Element) xml.getElementsByTagName("wind").item(0).getChildNodes().item(0);
        System.out.println ("wind-speed: " + windSpeedNode.getAttribute("value") + "km/h");

        Element windDirectionNode = (Element) xml.getElementsByTagName("wind").item(0).getChildNodes().item(2);
        System.out.println ("wind-deg: " + windDirectionNode.getAttribute("value"));
    }

}