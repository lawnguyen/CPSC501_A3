import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

public class Deserializer {
    
	public Object deserialize(Document doc) {
		Object obj = null;
        try {
            Element rootElement = doc.getRootElement();

            List children = rootElement.getChildren();
            for(Object child_ : children){
                Element child = (Element) child_;

                Class objClass = Class.forName(child.getAttribute("class").getValue());
                obj = objClass.getConstructor().newInstance();
                List objChildren = child.getChildren();
                for(Object field_ : objChildren) {
                    Element field = (Element) field_;
                    // objClass.getField(field.getAttribute("name").getValue()).setAccessible(true);
                    Field pField = objClass.getDeclaredField(field.getAttribute("name").getValue());
                    pField.setAccessible(true);
                    Class fieldType = pField.getType();
                    if(fieldType == java.lang.String.class)
                        pField.set(obj,field.getValue());
                    else
                        pField.set(obj,Integer.valueOf(field.getValue()));
                }
                System.out.println(obj.toString());
            }
        } catch (NoSuchFieldException | 
        		ClassNotFoundException | 
        		NoSuchMethodException | 
        		InstantiationException | 
        		IllegalAccessException | 
        		InvocationTargetException e) {
	        e.printStackTrace();
	    }
        
        return obj;
	}
}
