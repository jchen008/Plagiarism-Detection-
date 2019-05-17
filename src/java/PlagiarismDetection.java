import java.util.*;
import java.io.*;

public class PlagiarismDetection implements Detector{

    public double checkPlagiarism(String syns, String file1, String file2, int N) throws IOException{
        // initialize data structure to store the input files
        List<String[]> tuplesFromFile1 = new ArrayList<>();
        List<String[]> tuplesFromFile2 = new ArrayList<>();
        HashMap<String, HashSet<String>> synsMap = new HashMap<>();
        double synscount = 0;

        // import data from input files
        tuplesFromFile1 = getTuplesFromFile(file1, N);
        tuplesFromFile2 = getTuplesFromFile(file2, N);
        print(tuplesFromFile1);
        print(tuplesFromFile2);
        synsMap = generateSynsMap(syns);

        for (String[] tuple1: tuplesFromFile1){
            for (String[] tuple2: tuplesFromFile2){
                if (isSyn(tuple1, tuple2, synsMap)) synscount++;
            }
        }
        
        return synscount/tuplesFromFile2.size();
    }

    @Override
    public boolean isSyn(String[] tuple1, String[] tuple2, HashMap<String, HashSet<String>> synsMap){
        for(int i = 0; i < tuple1.length; i++){
            String w1 = tuple1[i];
            String w2 = tuple2[i];
            if (!w1.equals(w2)){
                if (synsMap.containsKey(w1) && synsMap.containsKey(w2)){
                    if (!synsMap.get(w1).equals(synsMap.get(w2))){
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    // Get all tuples from the file
    public List<String[]> getTuplesFromFile(String file, int N) throws IOException{
        List<String[]> tuples = new ArrayList<>();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while((line = reader.readLine()) != null){
                String[] wordsInLine = line.toLowerCase().split(" ");
                printS(wordsInLine);
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

    public HashMap<String, HashSet<String>> generateSynsMap(String file) throws IOException{
        HashMap<String,HashSet<String>> synsMap = new HashMap<>();
        BufferedReader reader = null;

        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordsInLine = line.toLowerCase().split(" ");
                for (String word: wordsInLine){
                    HashSet<String> set = null;
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

    private void print(List<String[]> tuples){
        for (String[] s: tuples){
            System.out.print("[");
            for (String ss: s){
                System.out.print(ss+",");
            }
            System.out.print("]");
        }
        System.out.println();
    }

    private void printS(String[] words){
        System.out.print("[");
        for (String ss : words) {
            System.out.print(ss + ",");
        }
        System.out.println("]");
    }

    public static void main(String[] args) throws IOException{
        if (args.length > 4 || args.length < 3) {
            System.err.println("Please enter either 3 or 4 arguments");
            System.exit(-1);
        }


        String syns = "../data/" + args[0];
        String file1 = "../data/" + args[1];
        String file2 = "../data/" + args[2];
        int N = args.length == 4 ? Integer.parseInt(args[3]) : 3;

        System.out.println(syns);

        PlagiarismDetection p = new PlagiarismDetection();
        System.out.println(String.format("%.0f%%", p.checkPlagiarism(syns, file1, file2, N) * 100));

    }
}