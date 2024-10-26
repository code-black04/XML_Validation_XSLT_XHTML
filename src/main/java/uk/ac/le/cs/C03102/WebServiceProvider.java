package uk.ac.le.cs.C03102;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WebServiceProvider {

    private String xmlFilePath;

    public WebServiceProvider(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public static void createJsonFile(String xmlFilePath) throws IOException {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            MethodHandler methodHandler = new MethodHandler();
            saxParser.parse(new File(xmlFilePath), methodHandler);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("abstract_method", methodHandler.getMethodsArray());

            outputJsonFile("./src/main/resources/WebServiceProvider.json", jsonObject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void outputJsonFile(String jsonFilePath, JSONObject jsonObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            String jsonString = jsonObject.toString(0);
            JsonElement jsonElement = JsonParser.parseString(jsonString);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(jsonElement);

            System.out.println(prettyJson);
            writer.write(prettyJson);
            writer.flush();
            System.out.println("Pretty-printed JSON written to file:\n" + prettyJson);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }

    private static class MethodHandler extends DefaultHandler {
        private JSONArray methodsArray = new JSONArray();
        private JSONObject abstractMethodName;
        private JSONObject argumentsList;
        private JSONArray parametersArray;
        private JSONArray exceptionsArray;
        private StringBuilder stringBuilder;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            stringBuilder = new StringBuilder();
            if ("abstract_method".equals(qName)) {
                abstractMethodName = new JSONObject();
                abstractMethodName.put("method_name", attributes.getValue("name"));
                parametersArray = new JSONArray();
                exceptionsArray = new JSONArray();
            } else if ("parameter".equals(qName)) {
                JSONObject parameterJson = new JSONObject();
                parameterJson.put("datatype", attributes.getValue("type"));
                parametersArray.put(parameterJson);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ("visibility".equals(qName)) {
                abstractMethodName.put("visibility", stringBuilder.toString().trim());
            } else if ("parameter".equals(qName)) {
                JSONObject parameterJson = parametersArray.getJSONObject(parametersArray.length() - 1);
                parameterJson.put("label", stringBuilder.toString().trim());
            } else if ("exception".equals(qName)) {
                exceptionsArray.put(stringBuilder.toString().trim());
            } else if ("abstract_method".equals(qName)) {
                argumentsList = new JSONObject();
                argumentsList.put("parameter", parametersArray);
                abstractMethodName.put("arguments", argumentsList);

                if (!exceptionsArray.isEmpty()) {
                    JSONObject exceptionsJson = new JSONObject();
                    exceptionsJson.put("exception", exceptionsArray);
                    abstractMethodName.put("exceptions", exceptionsJson);
                }
                methodsArray.put(abstractMethodName);
            } else if ("return".equals(qName)) {
                abstractMethodName.put("return", stringBuilder.toString().trim());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            stringBuilder.append(ch, start, length);
        }

        public JSONArray getMethodsArray() {
            return methodsArray;
        }
    }

}
