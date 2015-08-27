package services.ntr.pms.reader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;

public class ReplayReaderTest
{


	public void setUp() throws Exception
	{}


	public void test() throws IOException{
	
		InputStream inputStream = ReplayReaderTest.class.getResourceAsStream("game_replay.wotreplay");
		
		byte[] byteArray = IOUtils.toByteArray(inputStream);
	    byteArray = Arrays.copyOfRange(byteArray,  12,  44245); 
	
		fail("Not yet implemented");
	}

}
