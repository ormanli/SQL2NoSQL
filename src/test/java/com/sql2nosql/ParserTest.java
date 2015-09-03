package com.sql2nosql;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;

public class ParserTest {
	private Block<Document> printBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			System.out.println(document.toJson());
		}
	};

	@Test
	public void selectTest() throws Exception {
		SQLExecuter executer = new SQLExecuter("localhost", 27017, "mydb");

		FindIterable<Document> result = executer.execute("SELECT * FROM deneme");

		result.forEach(printBlock);
	}

	@Test
	public void selectWhereOrTest() throws Exception {
		SQLExecuter executer = new SQLExecuter("localhost", 27017, "mydb");

		FindIterable<Document> result = executer.execute("SELECT * FROM deneme WHERE col1=5 or col2=2");

		result.forEach(printBlock);
	}

	@Test
	public void selectWhereTest() throws Exception {
		SQLExecuter executer = new SQLExecuter("localhost", 27017, "mydb");

		FindIterable<Document> result = executer.execute("SELECT * FROM deneme WHERE col1=5");

		result.forEach(printBlock);
	}

	@Test
	public void selectColumnTest() throws Exception {
		SQLExecuter executer = new SQLExecuter("localhost", 27017, "mydb");

		FindIterable<Document> result = executer.execute("SELECT A.col3,A.col2 FROM deneme A WHERE A.col1=5");

		result.forEach(printBlock);
	}

	@Test
	public void selectGreaterThanTest() throws Exception {
		SQLExecuter executer = new SQLExecuter("localhost", 27017, "mydb");

		FindIterable<Document> result = executer.execute("SELECT A.* FROM deneme A WHERE A.col2>3");

		result.forEach(printBlock);
	}
}
