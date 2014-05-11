package external_tools;

import java.io.*;

public class FSdeletefile {
    private int nullValue=0;
    private RandomAccessFile file;
    private long directoryBlockPos = 4096;
    private long currPositon = 0;
    public void start(String f, String filename) {
	try {
	    file = new RandomAccessFile(filename,"rw");
	    deleteFile(f);
	}
	catch ( IOException e ) {
	    System.out.print("IO Exception - BlockDevice file not found.");
	}
    
    }

    private void deleteFile(String f) throws IOException {
		long currPos = directoryBlockPos;
		while ( currPos <= 8191 ) {
		    file.seek(currPos);
		    byte[] bytes = new byte[12];
		    char[] chars = new char[12];
		    int c = 0;
		    while ( c < 12 ) {
			bytes[c] = (byte)file.read();
			chars[c] = (char)bytes[c];
			if ( (int)chars[c] == 0 ) {
			    break;
			}
			c++;
		    }
		    String s = "";
		    c = 0;
		    while ( c < 12 ) {
			if ( (int)chars[c] == 0 ) {
			    break;
			}
			s += chars[c];
			c++;
		    }
		    if ( s.equals(f) ) {
			currPos+=12;
			//nullvalue
			file.writeInt(nullValue);
			return;
		    }	
		
		    currPos += 16;
		}
		throw new RuntimeException("File not found.");
    }
}

 