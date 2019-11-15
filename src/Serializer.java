import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.IdentityHashMap;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private Integer identifierNumber = 0;
	IdentityHashMap<Object, Integer> ihm = new IdentityHashMap<Object, Integer>();
    
    public Document serialize(Object obj) {
    	Class c = obj.getClass();
    	
    	// Init document
		Element root = new Element("serialized");
		Document doc = new Document(root);
		
		// Create an object element
		Element objectElement = new Element("object");
		
		// Set the object elements attributes
		objectElement.setAttribute(new Attribute("class", c.getSimpleName()));
		objectElement.setAttribute(new Attribute("id", getObjectId(obj).toString()));
		if (c.isArray()) {
			objectElement.setAttribute(new Attribute("length", Integer.toString(Array.getLength(obj))));
			
			// Set the value of array contents
			for (int i = 0; i < Array.getLength(obj); i++) {
				// Set the value
				Element valueElement;
	    		Object fieldValue = null;
	        	try {
	        		fieldValue = Array.get(obj, i);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
				if (Array.get(obj, i).getClass().getComponentType().isPrimitive()) {
					valueElement = new Element("value");
		        	valueElement.setText(fieldValue.toString());
				} else {
					valueElement = new Element("reference");
					valueElement.setText(getObjectId(fieldValue).toString());
				}
				
				// Add value element to object element
				objectElement.addContent(valueElement);
			}
		}
		
		
		// Get fields
		Field[] fields = c.getDeclaredFields();
		// Filter out static fields
		fields = Arrays.stream(fields).filter(x -> !Modifier.isStatic(x.getModifiers())).toArray(Field[]::new);
		
		for (int i = 0; i < fields.length; i++) {
			// Create field element
			Element fieldElement = new Element("field");
			
			// Set the field element's attributes
			fieldElement.setAttribute(new Attribute("name", fields[i].getName()));	
			fieldElement.setAttribute(new Attribute("declaringclass", fields[i].getDeclaringClass().getSimpleName()));
			
			// Set the value
			Element valueElement;
    		fields[i].setAccessible(true);
    		Object fieldValue = null;
        	try {
        		fieldValue = fields[i].get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (fields[i].getType().isPrimitive()) {
				valueElement = new Element("value");
	        	valueElement.setText(fieldValue.toString());
	        	
				// Add value element to field element
				fieldElement.addContent(valueElement);
				
				// Add field element to object element
				objectElement.addContent(fieldElement);
			} else {
				valueElement = new Element("reference");
				valueElement.setText(getObjectId(fieldValue).toString());
				
				// Add value element to field element
				fieldElement.addContent(valueElement);
				
				// Add field element to object element
				objectElement.addContent(fieldElement);
				
				
			}
			

		}
		
		// Add the object element to the doc
		doc.getRootElement().addContent(objectElement);
		
		return doc;
    }
    
    public Integer getObjectId(Object obj) {
    	Integer id = ihm.get(obj);
    	if (id == null) {
    		ihm.put(obj, identifierNumber);
    		return identifierNumber++;
    	}
    	return id;
    }
    
    public void outputXml(Document doc) {
        try {
			new XMLOutputter().output(doc, System.out);

	        XMLOutputter xmlOutput = new XMLOutputter();

	        // Display in a readable format
	        xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter("file.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
