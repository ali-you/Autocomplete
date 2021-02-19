import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FilePath {

    private Trie trie = new Trie();
    private Map<String, Integer> hashWords = new HashMap<>();
    private String path;

    public Trie getTrie() {
        return trie;
    }

    public Map<String, Integer> getHashWords() {
        return hashWords;
    }


    public FilePath(String path){
        this.path = path;
        this.readFile();
    }

    public void readFile(){
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String word = line.toLowerCase();
                if (!line.contains(" ")) {
                    hashWords.put(word, hashWords.getOrDefault(word, 0) + 1);
                    trie.add(word);
                } else {
                    String[] fileWord = line.split("\\s");
                    for (String str : fileWord) {
                        hashWords.put(str, hashWords.getOrDefault(str, 0) + 1);
                        trie.add(str);
                    }
                }
            }
            fileReader.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void writeFile(String word){
        try{
            FileWriter fileWriter = new FileWriter(path, true);
            fileWriter.append("\n" + word);
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.readFile();
    }



}
