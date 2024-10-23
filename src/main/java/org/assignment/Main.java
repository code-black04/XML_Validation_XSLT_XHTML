package org.assignment;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import net.sf.saxon.s9api.*;
import org.xml.sax.SAXException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws SaxonApiException {
        // XML and XSD file paths
        String xmlFile = "./src/main/resources/WebServiceProvider.xml";
        String xsdFile = "./src/main/resources/WebServiceProvider.xsd";
        String xsltFile = "./src/main/resources/WebServiceProvider.xslt";

        // Validate XML against XSD
        boolean isValid = validateXMLSchema(xsdFile, xmlFile);
        if (isValid) {
            System.out.println("XML is valid against the provided XSD.");
            createHTMLFile(xmlFile, xsltFile);

        } else {
            System.out.println("XML is NOT valid against the provided XSD.");
        }
    }

    public static void createHTMLFile(String xmlFilePath, String xsltFilePath) throws SaxonApiException {
        Processor processor = new Processor(false);

        // Load the XSLT file
        XsltCompiler compiler = processor.newXsltCompiler();
        XsltExecutable executable = compiler.compile(new StreamSource(new File(xsltFilePath)));

        // Create a transformer for the XSLT
        XsltTransformer transformer = executable.load();

        // Set the source XML file
        transformer.setSource(new StreamSource(new File(xmlFilePath)));

        // Set the output to a new HTML file
        Serializer out = processor.newSerializer(new File("./src/main/resources/WebServiceProvider.html"));
        out.setOutputProperty(Serializer.Property.METHOD, "html");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");

        transformer.setDestination(out);

        // Run the transformation
        transformer.transform();

        System.out.println("Transformation complete! HTML file created.");
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