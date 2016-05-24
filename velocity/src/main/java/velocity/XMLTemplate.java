package velocity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class XMLTemplate {

	private VelocityEngine velocityEngine;

	public XMLTemplate() {
		try {
			// Load the velocity properties from the class path
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("velocity.properties"));

			// Create and initialize the template engine
			velocityEngine = new VelocityEngine(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute() throws Exception {

		/*
		 * organize our data
		 */

		ArrayList list = new ArrayList();
		Map map = new HashMap();

		map.put("accountno", "009090009901990");
		map.put("swiftcode", "1221212");

		for (int i = 0; i < 18; i++) {
			map.put("narration" + i, "your account was debited for so and so ammount" + i);
		}

		list.add(map);
		
		map = new HashMap();

		map.put("accountno", "009090009901991");
		map.put("swiftcode", "1221212");

		for (int i = 0; i < 18; i++) {
			map.put("narration" + i, "your account was debited for so and so ammount" + i);
		}

		list.add(map);

		/*
		 * add that list to a VelocityContext
		 */
		System.err.println(System.currentTimeMillis());
		VelocityContext context = new VelocityContext();
		context.put("accountlist", list);

		/*
		 * get the Template
		 */

		Template t = velocityEngine.getTemplate("general.vm");

		/*
		 * now render the template into a Writer, here a StringWriter
		 */

		StringWriter writer = new StringWriter();
		System.err.println(System.currentTimeMillis());
		t.merge(context, writer);

		/*
		 * use the output in the body of your emails
		 */
		System.err.println(System.currentTimeMillis());
		System.out.println(writer.toString());

	}

	public static void main(String[] args) throws Exception {
		XMLTemplate t = new XMLTemplate();
		t.execute();
	}

}
