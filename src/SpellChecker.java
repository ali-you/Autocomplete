import java.util.*;

public class SpellChecker {

    FilePath filePath = new FilePath("words_alpha.txt");
    final static List<String> validChar = Arrays.asList("abcdefghijklmnopqrstuvwxyz");

    public SpellChecker(){ };

    public String[] suggestWord(String inputWord) {
        if (inputWord.length() == 0 || inputWord == null || validChar.contains(inputWord.toLowerCase()))
            return null;
        String input = inputWord.toLowerCase();
        String[] res = new String[10];
        TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>();
        TrieNode node = filePath.getTrie().search(input);
        if (node == null) {
            for (String w : filePath.getHashWords().keySet()) {
                int distance = editDistance(w, input);
                TreeMap<Integer, TreeSet<String>> nearWords = map.getOrDefault(distance, new TreeMap<>());
                int freq = filePath.getHashWords().get(w);
                TreeSet<String> set = nearWords.getOrDefault(freq, new TreeSet<>());
                set.add(w);
                nearWords.put(freq, set);
                map.put(distance, nearWords);
            }
            for (int i = 0; i < 10; i++) {
                res[i] = map.firstEntry().getValue().firstEntry().getValue().pollFirst();
            }
        } else if (node != null) {
            res = null;
        }
        return res;
    }


    int editDistance(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int dpTable[][] = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0)
                    dpTable[i][j] = j;
                else if (j == 0)
                    dpTable[i][j] = i;
                else if (word1.charAt(i - 1) == word2.charAt(j - 1))
                    dpTable[i][j] = dpTable[i - 1][j - 1];
                else if (i > 1 && j > 1 && word1.charAt(i - 1) == word2.charAt(j - 2)
                        && word1.charAt(i - 2) == word2.charAt(j - 1))
                    dpTable[i][j] = 1 + Math.min(Math.min(dpTable[i - 2][j - 2], dpTable[i - 1][j]), Math.min(dpTable[i][j - 1], dpTable[i - 1][j - 1]));
                else
                    dpTable[i][j] = 1 + Math.min(dpTable[i][j - 1], Math.min(dpTable[i - 1][j], dpTable[i - 1][j - 1]));
            }
        }
        return dpTable[n][m];
    }
}