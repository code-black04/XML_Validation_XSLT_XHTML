package org.assignment;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        // XML and XSD file paths
        String xmlFile = "/home/naina/Desktop/UoL/Mobile And Web Apps/Assignment/Assignment 1/Solution/assignment-1/src/main/resources/WebServiceProvider.xml";
        String xsdFile = "/home/naina/Desktop/UoL/Mobile And Web Apps/Assignment/Assignment 1/Solution/assignment-1/src/main/resources/WebServiceProvider.xsd";

        // Validate XML against XSD
        boolean isValid = validateXMLSchema(xsdFile, xmlFile);
        if (isValid) {
            System.out.println("XML is valid against the provided XSD.");
        } else {
            System.out.println("XML is NOT valid against the provided XSD.");
        }
    }

    // Method to validate XML against XSD
    public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            // Create a SchemaFactory capable of understanding WXS schemas
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Load the XSD schema
            Schema schema = factory.newSchema(new File(xsdPath));

            // Create a Validator object, which can be used to validate an instance document
            Validator validator = schema.newValidator();

            // Validate the XML document
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            // Catch validation exception and return false
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}