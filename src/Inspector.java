import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {
        Class c = obj.getClass();
        inspectClass(c, obj, recursive, 0);
    }

    private void inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	String indentDepth = "";
    	for (int i = 0; i < depth; i++) {
    		indentDepth += "\t";
    	}
    	
    	// Class
    	System.out.println(indentDepth + "|======== Class: " + c.getCanonicalName() + " ========|");
    	
    	// Handle arrays
    	if (c.isArray()) {
			inspectArray(indentDepth, c, obj, depth);
    	}
    	
    	// Fields
    	inspectFields(c, obj, recursive, depth, indentDepth);
    	
    	// Recursively explore superclasses
    	inspectSuperclasses(c, recursive, depth, indentDepth);
    	
    	// Interfaces do not implement interfaces
    	inspectInterfaces(c, recursive, depth, indentDepth);
    }

	private void inspectInterfaces(Class c, boolean recursive, int depth, String indentDepth) {
		if (!c.isInterface()) {
	    	// Recursively explore interfaces
	    	Class[] interfaces = c.getInterfaces();
	    	System.out.println(indentDepth + "Interface(s): ");
    		for (Class i : interfaces) {
    			inspectClass(i, i.getClass(), recursive, depth+1);
    		}
    	}
	}

	private void inspectSuperclasses(Class c, boolean recursive, int depth, String indentDepth) {
		Class superclass = c.getSuperclass();
    	if (superclass != null) {
    		System.out.println(indentDepth + "Super-class: " + superclass.getCanonicalName());
        	if (superclass != Object.class) {
        		inspectClass(superclass, superclass, recursive, depth+1);
        	}
    	}
	}

	private void inspectArray(String indentDepth, Class c, Object obj, int depth) {
		System.out.println(indentDepth + "This class is an array! ");
		System.out.println(indentDepth + " Array name: " + c.getCanonicalName());
		System.out.println(indentDepth + " Component type: " + c.getComponentType());
		System.out.println(indentDepth + " Length: " + Array.getLength(obj));
		System.out.println(indentDepth + " Array contents: " + c.getCanonicalName());	
		for (int i = 0; i < Array.getLength(obj); i++) {
			Object element = Array.get(obj, i);
			System.out.println(indentDepth + "  -" + element);
        	if (element == null) {
        		System.out.println(indentDepth + "  -null");
        	}else if (element.getClass().isPrimitive()) {
				System.out.println(indentDepth + "  -" + element);
    		} else {
    			// Recurse over Object-type fields
				inspectClass(element.getClass(), element, true, depth+1);
    		}
		}
	}

	private void inspectFields(Class c, Object obj, boolean recursive, int depth, String indentDepth) {
		Field[] fields = c.getDeclaredFields();
    	for (int i = 0; i < fields.length; i++) {
    		System.out.println(indentDepth + "Field name: " + fields[i].getName());
    		System.out.println(indentDepth + " Modifier: " + Modifier.toString((fields[i].getModifiers())));
    		System.out.println(indentDepth + " Type: " + fields[i].getType().getCanonicalName());
    		
    		fields[i].setAccessible(true);
    		Object field = null;
        	try {
				field = fields[i].get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// e.printStackTrace();
			}
        	
        	if (field == null) {
        		System.out.println(indentDepth + " Current value: null");
        	}else if (fields[i].getType().isPrimitive()) {
				System.out.println(indentDepth + " Current value: " + field);
    		} else if(!recursive) {
				System.out.println(indentDepth + " Current value: " + field.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(field)));
    		} else {
    			// Recurse over Object-type fields
				System.out.println(indentDepth + " Current value: ");
				inspectClass(field.getClass(), field, recursive, depth+1);
    		}
    	}
	}
}
