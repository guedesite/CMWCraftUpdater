import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;

public class main {

	private static List<String> all = new ArrayList<String>();
	
	public static void main(String[] args) {
		File baseDir = new File("base/");
		try {
			loopDir(baseDir);
			System.out.println("end");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void loopDir(File f2) throws IOException {

		int i = 0, nb = 0;
		for(File f:f2.listFiles()) {
			if(!f.isDirectory()) {
				nb++;
			} 
		}
		for(File f:f2.listFiles()) {
			if(f.isDirectory()) {
				loopDir(f);
			}
			else {
				File f3 = null;
				if((f3 = new File(f.getAbsolutePath().replace("\\CMWCraftUpdater\\base\\", "\\CMWCraftUpdater\\change\\"))).exists()) {
					if(Arrays.equals(Files.readAllBytes(f.toPath()), Files.readAllBytes(f3.toPath()))) {
						i++;
						if(!f3.delete()) {
							System.err.println("can't delete file "+f3.getAbsolutePath());
							FileUtils.forceDelete(f3);
						} else {
							System.out.println("delete file "+f3.getAbsolutePath());
						}
					} else {
						Files.copy(f.toPath(), f3.toPath(),StandardCopyOption.REPLACE_EXISTING);
						System.out.println("Replace "+f3.getAbsolutePath());
					}
				}
				else {
					if(f3.createNewFile()) {
						System.out.println("Create file "+f3.getAbsolutePath());
					} else {
						System.err.println("can't create file "+f3.getAbsolutePath());
					}
				}

			}
		}
		if(nb == i && nb != 0) {
			FileUtils.cleanDirectory(f2);
			FileUtils.deleteDirectory(f2);
			System.out.println("delete dir "+f2.getAbsolutePath());
		}
	}
}