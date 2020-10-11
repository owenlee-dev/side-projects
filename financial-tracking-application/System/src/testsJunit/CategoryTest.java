package testsJunit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import Objects.Category;

public class CategoryTest {


	
	@Test
	public void test() {
		
		Category groceries = new Category("Groceries",1.5);;
		String tag1  = "SOBEYS";;
		String tag2  = "SUPERSTORE";
		assertEquals("Groceries",groceries.getName());
	
		groceries.setName("new name");
		assertEquals("new name", groceries.getName());

		ArrayList<String> tagsToCheck = new ArrayList<>();
		tagsToCheck.add(tag1);
		tagsToCheck.add(tag2);
		groceries.addTag(tag1);
		groceries.addTag(tag2);
		assertEquals(tagsToCheck,groceries.getTags());
		assertEquals(1.5,groceries.getPoints(),0);
	}

	

}
