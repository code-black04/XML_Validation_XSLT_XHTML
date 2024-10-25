package uk.ac.le.cs.C03102;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

public class GenerateJSONFile {

    private String xmlFilePath;

    public GenerateJSONFile(String xmlFilePath) {
        xmlFilePath = this.xmlFilePath;
    }

    public static void createJsonFIle(String xmlFilePath) throws IOException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFilePath);
            document.getDocumentElement().normalize();

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = IntStream.range(0, document.getElementsByTagName("abstract_method").getLength())
                    .mapToObj(i -> (Element) document.getElementsByTagName("abstract_method").item(i))
                    .map(GenerateJSONFile::processMethodElement)
                    .collect(JSONArray::new, JSONArray::put, JSONArray::put);

            jsonObject.put("abstract_method", jsonArray);
            GenerateJSONFile.outputJsonFile(String.valueOf(new File("./src/main/resources/WebServiceProvider.json")), jsonObject);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject processMethodElement(Element methodElement) {
        JSONObject methodJson = new JSONObject();
        methodJson.put("visibility", getTagValue("visibility", methodElement));
        methodJson.put("method_name", methodElement.getAttribute("name"));

        // Process arguments and exceptions
        methodJson.put("arguments", processArguments(methodElement));
        methodJson.put("exceptions", processExceptions(methodElement));

        // Process return type
        methodJson.put("return", getTagValue("return", methodElement));
        return methodJson;
    }

    private static JSONObject processArguments(Element methodElement) {
        JSONArray parametersArray = IntStream.range(0, methodElement.getElementsByTagName("parameter").getLength())
                .mapToObj(i -> (Element) methodElement.getElementsByTagName("parameter").item(i))
                .map(parameterElement -> {
                    JSONObject parameterJson = new JSONObject();
                    parameterJson.put("datatype", parameterElement.getAttribute("type"));
                    parameterJson.put("label", parameterElement.getTextContent());
                    return parameterJson;
                })
                .collect(JSONArray::new, JSONArray::put, JSONArray::put);

        JSONObject argumentsJson = new JSONObject();
        argumentsJson.put("parameter", parametersArray);
        return argumentsJson;
    }

    // Helper to process the <exceptions> and nested <exception> elements
    private static JSONObject processExceptions(Element methodElement) {
        NodeList exceptionNodes = methodElement.getElementsByTagName("exception");
        if (exceptionNodes.getLength() == 0) {
            return new JSONObject(); // Return empty if no exceptions
        }

        JSONArray exceptionsArray = IntStream.range(0, exceptionNodes.getLength())
                .mapToObj(i -> exceptionNodes.item(i).getTextContent())
                .collect(JSONArray::new, JSONArray::put, JSONArray::put);

        JSONObject exceptionsJson = new JSONObject();
        exceptionsJson.put("exception", exceptionsArray);
        return exceptionsJson;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        return (nodeList.getLength() > 0) ? nodeList.item(0).getTextContent() : null;
    }

    private static Document getDocument(String filePath) {
        // Code for Document initialization here
        return null; // Replace with actual document parsing code
    }

    private static void outputJsonFile(String jsonFilePath, JSONObject jsonObject) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFilePath))) {
            String prettyJsonString = jsonObject.toString(1); // Pretty print JSON with 4-space indentation
            writer.write(prettyJsonString);
            writer.flush();
            System.out.println("Pretty-printed JSON written to file:\n" + prettyJsonString);
        } catch (IOException e) {
            System.err.println("Error writing JSON to file: " + e.getMessage());
        }
    }
}
