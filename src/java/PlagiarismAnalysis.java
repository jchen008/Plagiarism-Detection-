import java.util.*;
import java.io.*;

public class PlagiarismAnalysis implements Detector{

    @Override
    public boolean isSyn(String[] tuple1, String[] tuple2, HashMap<String, HashSet<String>> synsMap){
        for(int i = 0; i < tuple1.length; i++){
            String w1 = tuple1[i];
            String w2 = tuple2[i];
            if (!w1.equals(w2)){
                if (!synsMap.containsKey(w1) || !synsMap.containsKey(w2)){
                    return false;
                }else{
                    if (!synsMap.get(w1).equals(synsMap.get(w2))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Get all tuples from the file and return a list which stores all tuples
    public List<String[]> getTuplesFromFile(String file, int N) throws IOException{
        List<String[]> tuples = new ArrayList<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            // String to store line read
            String l;
            while((l = reader.readLine()) != null){
                // Ignore Case
                String[] wordsInLine = l.toLowerCase().split(" ");
                for (int i = 0; i <= wordsInLine.length - N; i++) {
                    String[] tuple = new String[N];
                    int j = 0;
                    while (j < N) {
                        tuple[j] = wordsInLine[i+j];
                        j++;
                    }
                    tuples.add(tuple);
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("No File found");
            System.exit(1);
        }finally{
            if (reader != null) reader.close();
        }
        return tuples;
    }

    // Generate a synonyms map to have each word map to a set of all of its synonyms and return the map
    public HashMap<String, HashSet<String>> generateSynsMap(String file) throws IOException{
        HashMap<String,HashSet<String>> synsMap = new HashMap<>();
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            // String to store line read
            String l;
            while ((l = reader.readLine()) != null) {
                // Ignore Case
                String[] wordsInLine = l.toLowerCase().split(" ");
                for (String word: wordsInLine){
                    HashSet<String> set;
                    if (synsMap.containsKey(word)){
                        set = synsMap.get(word);
                    }else{
                        set = new HashSet<>();
                    }
                    set.addAll(Arrays.asList(wordsInLine));
                    synsMap.put(word,set);
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("No File found");
            System.exit(1);
        }finally{
            if (reader != null) reader.close();
        }
        return synsMap;
    }

    // Check plagiarism between two files and return the similarity
    public double checkPlagiarism(String syns, String file1, String file2, int N) throws IOException {
        // initialize data structure to store the input files
        List<String[]> tuplesFromFile1 = new ArrayList<>();
        List<String[]> tuplesFromFile2 = new ArrayList<>();
        HashMap<String, HashSet<String>> synsMap = new HashMap<>(); 
        // number of similar tuples between two files
        double synscount = 0; 

        // import data from input files
        tuplesFromFile1 = getTuplesFromFile(file1, N);
        tuplesFromFile2 = getTuplesFromFile(file2, N);
        synsMap = generateSynsMap(syns);

        // Check similarities by incrementing synscount each time we find a similarity 
        for (String[] tuple1 : tuplesFromFile1) {
            for (String[] tuple2 : tuplesFromFile2) {
                if (isSyn(tuple1, tuple2, synsMap))
                    synscount++;
            }
        }

        return synscount / tuplesFromFile2.size();
    }

    public static void main(String[] args) throws IOException{
        if (args.length > 4 || args.length < 3) {
            System.err.println("Please enter either 3 or 4 arguments");
            System.exit(-1);
        }

        String synsFile = "../data/" + args[0];
        String f1 = "../data/" + args[1];
        String f2 = "../data/" + args[2];

        int N = 0;
        if (args.length == 4){
            N = Integer.parseInt(args[3]);
        }else{
            N = 3;
        }

        PlagiarismAnalysis pd = new PlagiarismAnalysis();
        Double result = pd.checkPlagiarism(synsFile, f1, f2, N);
        // if file2 is empty, output 0.0%
        if (result.isNaN()){
            System.out.println("0.0%");
            return;
        }
        System.out.println(String.format("%.0f%%", result * 100));

    }
}