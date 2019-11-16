import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class Deserializer {
	HashMap<String, Object> hm = new HashMap<String, Object>();

	public Object deserialize(Document doc) {
		Object obj = null;
        try {
            Element rootElement = doc.getRootElement();

            List<Element> children = rootElement.getChildren();
            InitializeObjects(children);
            for(Object child_ : children){
                Element child = (Element) child_;
                List<Element> objChildren = child.getChildren();

                // Load class dynamically
                Class objClass = Class.forName(child.getAttribute("class").getValue());
                
                // Create an instance of the object
                if (objClass.isArray()) {
                	int length = child.getAttribute("length").getIntValue();
                	obj = hm.get(child.getAttribute("id").getValue());
	                
	                // Initialize array values
                	for (int i = 0; i < length; i++) {
                		Element arrayElement = objChildren.get(i);
                		
                		if (objClass.getComponentType().isPrimitive()) {
                			Array.set(obj, i, getPrimitiveValue(objClass.getComponentType(), arrayElement));
                		} else {
                			Object fieldValue = hm.get(arrayElement.getText());
                			Array.set(obj, i, fieldValue);
                		}
                	}
                	
                } else {
                	obj =hm.get(child.getAttribute("id").getValue());
	                
	                // Process the fields
	                for(Object field_ : objChildren) {
	                    Element field = (Element) field_;
	                    
	                    // Load class dynamically
	                    Class declaringClass = Class.forName(field.getAttribute("declaringclass").getValue());
	                    
	                    // Get field metaobject
	                    Field pField = objClass.getDeclaredField(field.getAttribute("name").getValue());
	                    pField.setAccessible(true);
	                    
	                    // Initialize the value of the field
	                    if (pField.getType().isPrimitive()) {
	                    	Object fieldValue = getPrimitiveValue(pField.getType(), field.getChildren().get(0));
	                    	pField.set(obj, fieldValue);
	                    } else {
	                    	Object fieldValue = hm.get(field.getChildren().get(0).getText());
	                    	pField.set(obj, fieldValue);
	                    }
	                }
                }
            }
        } catch (NoSuchFieldException | 
        		ClassNotFoundException | 
        		IllegalAccessException | 
        		NegativeArraySizeException | 
        		DataConversionException e) {
	        e.printStackTrace();
	    }
        
        return obj;
	}
	
	private void InitializeObjects(List<Element> objectElements) {
		try {
			for(Object element_ : objectElements){
	            Element object = (Element) element_;

	            // Load class dynamically
	            Class objClass = Class.forName(object.getAttribute("class").getValue());
	            
	            // Create an instance of the object
	            if (objClass.isArray()) {
	            	int length = object.getAttribute("length").getIntValue();
	            	Object obj = Array.newInstance(objClass.getComponentType(), length);
	            	
	                // Associate the instance with the object's id
	                hm.putIfAbsent(object.getAttribute("id").getValue(), obj);
	            	
	            } else {
	            	Constructor constructor = objClass.getConstructor();
	            	constructor.setAccessible(true);
	            	Object obj = constructor.newInstance();
	            
	                // Associate the instance with the object's id
	                hm.putIfAbsent(object.getAttribute("id").getValue(), obj);
	                
	            }
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object getPrimitiveValue (Class<?> type, Element e) {
		if (!type.isPrimitive())
			return type;
		if (type == Integer.TYPE)
			return Integer.parseInt(e.getText());
		if (type == Long.TYPE)
			return Long.parseLong(e.getText());
		if (type == Boolean.TYPE)
			return Boolean.parseBoolean(e.getText());
		if (type == Byte.TYPE)
			return Byte.parseByte(e.getText());
		if (type == Character.TYPE)
			return e.getText().charAt(0);
		if (type == Float.TYPE)
			return Float.parseFloat(e.getText());
		if (type == Double.TYPE)
			return Double.parseDouble(e.getText());
		if (type == Short.TYPE)
			return Short.parseShort(e.getText());

		return type;
	}
}
