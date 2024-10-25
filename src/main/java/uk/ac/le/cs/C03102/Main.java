package uk.ac.le.cs.C03102;

import net.sf.saxon.s9api.SaxonApiException;
import java.io.IOException;
import static uk.ac.le.cs.C03102.GenerateHTMLFile.createHTMLFile;
import static uk.ac.le.cs.C03102.GenerateJSONFile.createJsonFIle;
import static uk.ac.le.cs.C03102.XmlSchemaValidator.validateXMLSchema;

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
            createJsonFIle(xmlFilePath);
        } else {
            System.out.println("XML is NOT valid against the provided XSD.");
        }
    }
}