package tbansal.framework.jaxb;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarshallingTest {

	@Before
	public void setUp() {
		try {
			if (Files.exists(Paths.get("D:\\Backup\\MyStuff.xml"))) {
				Files.delete(Paths.get("D:\\Backup\\MyStuff.xml"));
				Files.delete(Paths.get("D:\\Backup"));
			}
			Files.createDirectory(Paths.get("D:\\Backup"));
			Files.createFile(Paths.get("D:\\Backup\\MyStuff.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() {

	}

	@Test
	public void test() {
		Tutorials ts = new Tutorials();

		for (int i = 1; i < 6; i++) {
			Tutorial tutorial = new Tutorial();
			tutorial.setName("Tutorial" + i);
			for (int j = 0; j < 3; j++) {
				tutorial.addChapter(newChapter(j));
			}
			ts.addTutorial(tutorial);

		}
		Tutorials tsm = null;
		try {
			Unmarshaller um = JAXBContext.newInstance(Tutorials.class)
					.createUnmarshaller();
			tsm = (Tutorials) um.unmarshal(MarshallingTest.class
					.getResourceAsStream("/test1.xml"));
			Marshaller m = JAXBContext.newInstance(Tutorials.class)
					.createMarshaller();
			File f = File.createTempFile("java", ".xml");
			f.deleteOnExit();
			m.marshal(ts, f);
			assertEquals(
					Files.readAllLines(Paths.get(f.getPath()),
							Charset.defaultCharset()),
					Files.readAllLines(Paths.get(f.getPath()),
							Charset.defaultCharset()));
		} catch (JAXBException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void assertEquals(List<String> string1,List<String> string2){
		System.out.println(string1+System.getProperty("line.separator")+string2);
		Assert.assertEquals(string1, string2);
	}

	private Chapter newChapter(int chapterno) {
		Chapter chapter = new Chapter();

		chapter.setName("chapter" + chapterno);
		for (int j = 0; j < 3; j++) {
			chapter.addTopic(newTopic(j));
		}
		return chapter;

	}

	private Topic newTopic(int topicno) {
		Topic topic = new Topic();
		topic.setName("hello" + topicno);
		topic.setDescription("hello" + topicno);
		List<String> javaFiles = Collections.EMPTY_LIST;
		topic.setJavafiles(javaFiles);
		return topic;

	}

}
