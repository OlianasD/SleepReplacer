package sleeptowait;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Utils {

	public static Map<String, List<Integer>> createPageAccessMap(Map<String, List<Integer>> sleeps) {
		Map<String, List<Integer>> pageAccesses = new HashMap<>();
		for(String fnam : sleeps.keySet()) {
			pageAccesses.put(fnam, new ArrayList<Integer>());
		}
		return pageAccesses;
	}


	public static Map<String, List<Integer[]>> createTryBlockMap(Map<String, List<Integer>> sleeps) {
		Map<String, List<Integer[]>> blockBoundaries = new HashMap<>();
		for(String fnam : sleeps.keySet()) {
			blockBoundaries.put(fnam, new ArrayList<Integer[]>());
			for(int i=0; i<sleeps.get(fnam).size(); i++) {
				blockBoundaries.get(fnam).add(new Integer[2]);
			}
		}
		return blockBoundaries;
	}

	public static String[] txtFileToLines(String path) throws IOException, FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       lines.add(line);
		    }
		}
		String[] arr = lines.toArray(new String[lines.size()]);
		return arr;
	}

	public static void strArrayToTxtFile(String[] payload, String path) throws IOException {
		 	File fout = new File(path);
	        FileOutputStream fos = new FileOutputStream(fout);
	
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	
	        for (int i = 0; i < payload.length; i++) {
	            bw.write(payload[i]);
	            bw.newLine();
	        }
	        bw.close();
	}
	
	public static void killOrphanProcesses() throws IOException, InterruptedException {
		List<String> cmdArgs = new ArrayList<>();
		cmdArgs.add("killall");
		cmdArgs.add("chrome");
		ProcessBuilder procBuilder = new ProcessBuilder(cmdArgs);
		Process p = procBuilder.start();
		p.waitFor();
		cmdArgs = new ArrayList<>();
		cmdArgs.add("killall");
		cmdArgs.add("chromedriver");
		procBuilder = new ProcessBuilder(cmdArgs);
		p = procBuilder.start();
		p.waitFor();
	}
	
	public static JSONObject loadJSONObject(String path) {
		JSONTokener tokener = null;
		try {
			URI uri = new URI("file://"+path);
			tokener = new JSONTokener(uri.toURL().openStream());
		} catch(Exception e) {
			System.err.println("ERROR: cannot load JSON object from "+path);
			System.err.println("Message: "+e.getMessage());
			e.printStackTrace();
		}
		JSONObject props = new JSONObject(tokener);
		return props;
	}

}
