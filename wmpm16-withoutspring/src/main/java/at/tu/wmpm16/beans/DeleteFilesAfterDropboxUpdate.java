package at.tu.wmpm16.beans;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class DeleteFilesAfterDropboxUpdate {

	public void delete(){
		File f = new File("c:/wmpm/file");
		for (File file : f.listFiles()) {
			file.delete();
		}
	}
}
