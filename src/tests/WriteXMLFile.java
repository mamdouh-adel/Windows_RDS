package tests;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WriteXMLFile {

    public static void main(String argv[]) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("application");
            doc.appendChild(rootElement);

            // staff elements
            Element app = doc.createElement("App");
            rootElement.appendChild(app);

            // set attribute to staff element
            Attr attr = doc.createAttribute("id");
            attr.setValue("1");
            app.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");

            // firstname elements
            Element firstname = doc.createElement("appName");
            firstname.appendChild(doc.createTextNode("yong"));
            app.appendChild(firstname);

            // lastname elements
            Element lastname = doc.createElement("appExec");
            lastname.appendChild(doc.createTextNode("mook kim"));
            app.appendChild(lastname);

            // nickname elements
            Element nickname = doc.createElement("appIcon");
            nickname.appendChild(doc.createTextNode("mkyong"));
            app.appendChild(nickname);


            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("apps.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
