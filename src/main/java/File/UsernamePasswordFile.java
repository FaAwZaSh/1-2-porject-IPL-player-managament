package File;

import java.io.*;
import java.util.HashMap;

public class UsernamePasswordFile {
    private static final String INPUT_FILE_NAME = "src/UsernamesAndPasses.txt";
    private static final String OUTPUT_FILE_NAME = "src/UsernamesAndPasses.txt";

    public HashMap<String, String> loadFile() throws IOException {

        HashMap<String, String> loginInfo = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            String[] info = line.split(",");
            loginInfo.put(info[0], info[1]);
        }
        br.close();
        return loginInfo;
    }

    public void saveFile(HashMap<String, String> loginInfo) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (String user : loginInfo.keySet()) {
            bw.write(String.join(",", user, loginInfo.get(user)));
            bw.newLine();
        }
        bw.close();
    }
}
