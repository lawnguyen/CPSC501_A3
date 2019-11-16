import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IdentityHashMap;

import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	private Integer identifierNumber = 0;
	IdentityHashMap<Object, Integer> ihm = new IdentityHashMap<Object, Integer>();
	ArrayList<Object> serializedObjects = new ArrayList<Object>();
    
    public Document serialize(Object obj) {
    	
    	// Init document
		Element root = new Element("serialized");
		Document doc = new Document(root);
		
		Document serializedDoc = serializeObject(obj, doc);
		
		return serializedDoc;
    }
    
    private Document serializeObject(Object obj, Document doc) {
    	// Don't serialize the same object more than once
    	if (serializedObjects.contains(obj)) {
    		return doc;
    	}
    	serializedObjects.add(obj);
    	
    	Class c = obj.getClass();

		Element objectElement = buildObjectElement(obj, doc, c);
		
		buildFieldElements(obj, doc, c, objectElement);
		
		// Add the object element to the doc
		doc.getRootElement().addContent(objectElement);
		
		return doc;
    }

	private void buildFieldElements(Object obj, Document doc, Class c, Element objectElement) {
		// Get fields
		Field[] fields = c.getDeclaredFields();
		// Filter out static fields
		fields = Arrays.stream(fields).filter(x -> !Modifier.isStatic(x.getModifiers())).toArray(Field[]::new);
		
		for (int i = 0; i < fields.length; i++) {
    		fields[i].setAccessible(true);
			
			// Create field element
			Element fieldElement = new Element("field");
			
			// Set the field element's attributes
			fieldElement.setAttribute(new Attribute("name", fields[i].getName()));	
			fieldElement.setAttribute(new Attribute("declaringclass", fields[i].getDeclaringClass().getName()));
			
			// Set the value
			Element valueElement;
    		Object fieldValue = null;
        	try {
        		fieldValue = fields[i].get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
        	if (fieldValue == null) {
				valueElement = new Element("value");
				valueElement.setText("null");
	        	
				addFieldElementWithValue(objectElement, fieldElement, valueElement);
        	} else if (fields[i].getType().isPrimitive()) {
				valueElement = new Element("value");
				valueElement.setText(fieldValue.toString());
	        	
				addFieldElementWithValue(objectElement, fieldElement, valueElement);
				
			} else {
				valueElement = new Element("reference");
				valueElement.setText(getObjectId(fieldValue).toString());
				
				addFieldElementWithValue(objectElement, fieldElement, valueElement);
				
				// Recursively serialize the field object
				serializeObject(fieldValue, doc);
			}
		}
	}

	private Element buildObjectElement(Object obj, Document doc, Class c) {
		// Create an object element
		Element objectElement = new Element("object");
		
		// Set the object elements attributes
		objectElement.setAttribute(new Attribute("class", c.getName()));
		objectElement.setAttribute(new Attribute("id", getObjectId(obj).toString()));
		if (c.isArray()) {
			objectElement.setAttribute(new Attribute("length", Integer.toString(Array.getLength(obj))));
			
			Element valueElement;
			if (c.getComponentType().isPrimitive()) {
				for (int i = 0; i < Array.getLength(obj); i++) {
					valueElement = new Element("value");
					valueElement.setText(Array.get(obj, i).toString());
					
					// Add value element to object element
					objectElement.addContent(valueElement);
				}
			} else {
				// Array object
				for (int i = 0; i < Array.getLength(obj); i++) {
					if (Array.get(obj, i) == null) {
						valueElement = new Element("value");
						valueElement.setText("null");
						
						// Add value element to object element
						objectElement.addContent(valueElement);
					} else {
						valueElement = new Element("reference");
						valueElement.setText(getObjectId(Array.get(obj, i)).toString());
						
						// Add value element to object element
						objectElement.addContent(valueElement);
						
						// Recursively serialize the array object
						serializeObject(Array.get(obj, i), doc);
					}
				}	
			}
		}
		return objectElement;
	}

	private void addFieldElementWithValue(Element parentElement, Element fieldElement, Element valueElement) {
		// Add value element to field element
		fieldElement.addContent(valueElement);
		
		// Add field element to object element
		parentElement.addContent(fieldElement);
	}
    
    private Integer getObjectId(Object obj) {
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
