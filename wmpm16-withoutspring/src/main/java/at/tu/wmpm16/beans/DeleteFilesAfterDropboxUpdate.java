package at.tu.wmpm16.beans;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class DeleteFilesAfterDropboxUpdate {
	
	//Mac
	String path = new String("//wmpm/file");
	
	//Windows
//	String path = new String("c:/wmpm/file");

	public void delete(){
		File f = new File(path);
		for (File file : f.listFiles()) {
			file.delete();
		}
	}
}
