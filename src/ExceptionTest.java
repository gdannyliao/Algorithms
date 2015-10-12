import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import javax.xml.ws.http.HTTPException;

public class ExceptionTest {
	public void test() throws IOException {
	}
}

class SubException extends ExceptionTest {
	@Override
	public void test() throws HTTPException, IllegalArgumentException {
	}
}

class A {
	public void foo() throws IOException {

	}
}

//class B extends A {
//
//	@Override
//	public void foo() throws SQLException {
//
//	} // NOT allowed
//}