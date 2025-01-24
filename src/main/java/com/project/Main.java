package com.project;

import net.sf.saxon.s9api.SaxonApiException;
import java.io.IOException;
import static com.project.GenerateHTMLFile.createHTMLFile;
import static com.project.WebServiceProvider.createJsonFile;
import static com.project.XmlSchemaValidator.validateXMLSchema;

public class Main {
    public static void main(String[] args) throws SaxonApiException, IOException {
        // XML, XSD and XSLT file paths
        final String xmlFilePath = "./src/main/resources/WebServiceProvider.xml";
        final String xsdFilePath = "./src/main/resources/WebServiceProvider.xsd";
        final String xsltFilePath = "./src/main/resources/WebServiceProvider.xslt";

        // Validate XML against XSD
        boolean isValid = validateXMLSchema(xsdFilePath, xmlFilePath);
        if (isValid) {
            System.out.println("XML is valid against the provided XSD.");
            createHTMLFile(xmlFilePath, xsltFilePath);
            createJsonFile(xmlFilePath);
        } else {
            System.out.println("XML is NOT valid against the provided XSD.");
        }
    }
}