package Testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Objects.Transaction;
//Junit test file by owen
public class Junit_testTransaction {
	protected Transaction transaction;
	
	@Before
	public void setUp() {
		transaction=new Transaction("05/10/2015","Test Description","Owen",210.87);
	}
	
	@Test
	public void test() {
		assertThat(transaction.getBuyer().equals("Owen"));
		assertThat(transaction.getDescritpion().equals("Test Desctription"));
		assertThat(transaction.getValue().is(equals(210.87)));
		assertThat(transaction.getDate().equals("05/10/2015"));
		
	}
	

}
