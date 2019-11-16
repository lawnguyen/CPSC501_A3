import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SerializerTests {
	private Serializer serializer;
	Object1 obj1;
	Object2 obj2;
	Object3 obj3;
	Object4 obj4;
	Object5 obj5;
	char[] obj6;
	
	@Before
	public void setUp() {
		serializer = new Serializer();
		obj1 = new Object1(4);
		obj2 = new Object2(4.20, new Object2b(new Object2()));
		obj3 = new Object3(new int[] { 1,2,3,4,5,6,7,8,9,0 });
		obj4 = new Object4(new Object1[] { new Object1(42), new Object1(777), new Object1(88) });
		ArrayList<Object1> al = new ArrayList<Object1>();
		al.add(new Object1(42)); 
		al.add(new Object1(777)); 
		al.add(new Object1(88));
		obj5 = new Object5(al);
		obj6 = new char[]{ 's','m','i','t','h' };
	}

	@Test
	public void testSerializer_obj1() {
		Document serialized = serializer.serialize(obj1);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<serialized>\n" + 
				"  <object class=\"Object1\" id=\"0\">\n" + 
				"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
				"      <value>4</value>\n" + 
				"    </field>\n" + 
				"  </object>\n" + 
				"</serialized>\n" + 
				""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
	@Test
	public void testSerializer_obj2() {
		Document serialized = serializer.serialize(obj2);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<serialized>\n" + 
				"  <object class=\"Object2\" id=\"2\">\n" + 
				"    <field name=\"d\" declaringclass=\"Object2\">\n" + 
				"      <value>1.0</value>\n" + 
				"    </field>\n" + 
				"    <field name=\"obj2b\" declaringclass=\"Object2\">\n" + 
				"      <value>null</value>\n" + 
				"    </field>\n" + 
				"  </object>\n" + 
				"  <object class=\"Object2b\" id=\"1\">\n" + 
				"    <field name=\"obj2\" declaringclass=\"Object2b\">\n" + 
				"      <reference>2</reference>\n" + 
				"    </field>\n" + 
				"  </object>\n" + 
				"  <object class=\"Object2\" id=\"0\">\n" + 
				"    <field name=\"d\" declaringclass=\"Object2\">\n" + 
				"      <value>4.2</value>\n" + 
				"    </field>\n" + 
				"    <field name=\"obj2b\" declaringclass=\"Object2\">\n" + 
				"      <reference>1</reference>\n" + 
				"    </field>\n" + 
				"  </object>\n" + 
				"</serialized>\n" + 
				""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
	@Test
	public void testSerializer_obj3() {
		Document serialized = serializer.serialize(obj3);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<serialized>\n" + 
				"  <object class=\"[I\" id=\"1\" length=\"10\">\n" + 
				"    <value>1</value>\n" + 
				"    <value>2</value>\n" + 
				"    <value>3</value>\n" + 
				"    <value>4</value>\n" + 
				"    <value>5</value>\n" + 
				"    <value>6</value>\n" + 
				"    <value>7</value>\n" + 
				"    <value>8</value>\n" + 
				"    <value>9</value>\n" + 
				"    <value>0</value>\n" + 
				"  </object>\n" + 
				"  <object class=\"Object3\" id=\"0\">\n" + 
				"    <field name=\"nums\" declaringclass=\"Object3\">\n" + 
				"      <reference>1</reference>\n" + 
				"    </field>\n" + 
				"  </object>\n" + 
				"</serialized>\n" + 
				""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
	@Test
	public void testSerializer_obj4() {
		Document serialized = serializer.serialize(obj4);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<serialized>\n" + 
					"  <object class=\"Object1\" id=\"2\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>42</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object1\" id=\"3\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>777</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object1\" id=\"4\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>88</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"[LObject1;\" id=\"1\" length=\"3\">\n" + 
					"    <reference>2</reference>\n" + 
					"    <reference>3</reference>\n" + 
					"    <reference>4</reference>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object4\" id=\"0\">\n" + 
					"    <field name=\"obj1s\" declaringclass=\"Object4\">\n" + 
					"      <reference>1</reference>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"</serialized>\n" + 
					""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
	@Test
	public void testSerializer_obj5() {
		Document serialized = serializer.serialize(obj5);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<serialized>\n" + 
					"  <object class=\"Object1\" id=\"3\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>42</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object1\" id=\"4\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>777</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object1\" id=\"5\">\n" + 
					"    <field name=\"num\" declaringclass=\"Object1\">\n" + 
					"      <value>88</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"[Ljava.lang.Object;\" id=\"2\" length=\"10\">\n" + 
					"    <reference>3</reference>\n" + 
					"    <reference>4</reference>\n" + 
					"    <reference>5</reference>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"    <value>null</value>\n" + 
					"  </object>\n" + 
					"  <object class=\"java.util.ArrayList\" id=\"1\">\n" + 
					"    <field name=\"elementData\" declaringclass=\"java.util.ArrayList\">\n" + 
					"      <reference>2</reference>\n" + 
					"    </field>\n" + 
					"    <field name=\"size\" declaringclass=\"java.util.ArrayList\">\n" + 
					"      <value>3</value>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"  <object class=\"Object5\" id=\"0\">\n" + 
					"    <field name=\"obj1sList\" declaringclass=\"Object5\">\n" + 
					"      <reference>1</reference>\n" + 
					"    </field>\n" + 
					"  </object>\n" + 
					"</serialized>\n" + 
					""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
	@Test
	public void testSerializer_obj6() {
		Document serialized = serializer.serialize(obj6);
		
		Document expected = null;
		try {
			expected = new SAXBuilder().build(new StringReader(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<serialized>\n" + 
					"  <object class=\"[C\" id=\"0\" length=\"5\">\n" + 
					"    <value>s</value>\n" + 
					"    <value>m</value>\n" + 
					"    <value>i</value>\n" + 
					"    <value>t</value>\n" + 
					"    <value>h</value>\n" + 
					"  </object>\n" + 
					"</serialized>\n" + 
					""));
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
		XMLOutputter xo = new XMLOutputter();
		xo.setFormat(Format.getCompactFormat());

		assertEquals(xo.outputString(expected), xo.outputString(serialized));
	}
	
}
