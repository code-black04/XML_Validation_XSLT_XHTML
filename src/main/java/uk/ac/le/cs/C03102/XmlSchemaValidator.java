package uk.ac.le.cs.C03102;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlSchemaValidator {

    private String xsdFilePath;
    private String xmlFilePath;

    public XmlSchemaValidator(String xsdFilePath, String xmlFilePath) {
        this.xsdFilePath = xsdFilePath;
        this.xmlFilePath = xmlFilePath;
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
