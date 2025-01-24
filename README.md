## XML Validation, XSLT, XHTML Project

This project demonstrates XML validation, JSON and HTML generation, and transformation using XSLT. The project includes functionality to validate XML files against an XSD schema, transform XML into HTML using XSLT, and generate JSON and HTML files programmatically.

## Features

- **XML Validation**:
    - Validates XML files against an XSD schema using `XmlSchemaValidator`.
- **HTML and JSON Generation**:
    - Programmatically generates HTML and JSON files using `GenerateHTMLFile` and `GenerateJSONFile`.
- **XSLT Transformation**:
    - Transforms XML into XHTML using an XSLT stylesheet.

## How to Run

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/code-black04/XML_Validation_XSLT_XHTML
   cd XML_Validation_XSLT_XHTML
   ```

2. **Build the Project**:
    - Make sure Maven is installed on your system.
    - Run the following command:
      ```bash
      mvn clean install
      ```

3. **Run the Application**:
    - Execute the `Main` class from your IDE or use the following Maven command:
      ```bash
      mvn exec:java -Dexec.mainClass="com.project.Main"
      ```

4. **Transform XML Using XSLT**:
    - Place your XML file in the `resources` folder.
    - Ensure the XSLT file (`WebServiceProvider.xslt`) is present.
    - The transformed HTML will be generated in the `resources` folder.

5. **Validate XML**:
    - Use the `XmlSchemaValidator` to validate an XML file against the provided XSD.

6. **Generate JSON or HTML**:
    - Use `GenerateJSONFile` or `GenerateHTMLFile` to generate files programmatically.

## Dependencies

The project uses the following libraries:

- **Maven**: For project management and dependency handling.
- **Java Standard Libraries**: Includes DOM, SAX, and other XML-related libraries.

## Resources

- **WebServiceProvider.xml**: Sample XML file.
- **WebServiceProvider.xsd**: XSD schema for validation.
- **WebServiceProvider.xslt**: XSLT stylesheet for XML transformation.
- **style.css**: Styling for generated HTML.