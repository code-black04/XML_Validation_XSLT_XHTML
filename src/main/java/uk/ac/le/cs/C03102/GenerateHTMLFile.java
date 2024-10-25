package uk.ac.le.cs.C03102;

import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class GenerateHTMLFile {

    private String xmlFilePath;
    private String xsdFilePath;

    public GenerateHTMLFile(String xmlFilePath, String xsdFilePath) {
        xmlFilePath = this.xmlFilePath;
        xsdFilePath = this.xsdFilePath;
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
}
