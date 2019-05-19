# Plagiarism-Detection
# Zhuo Chen
----------------------
Assumption
----------------------
1. Tuples can match no matter where they are in the text <br>
2. If either file is empty, output 0% <br>
----------------------
Algorithm
----------------------
1. Synonym file is parsed into a Hashmap which stores key-value pair <word, synonymSet>. <br>
2. First string file is parsed and all tuples are constructed as String arrays. <br>
3. Second string file is parsed and all tuples are constructed as String arrays <br>
4. Compare each tuple from the first file to each tuple from the second file and get the count of synonyms <br>
5. Divide the count of synonyms between two string files by the number of tuples in the second file to get the final result (the percent of tuples in file1 which appear in file2) <br>
----------------------
Testing Procedure
----------------------
command -> right output
1. java PlagiarismAnalysis syns.txt file1.txt file3.txt  -> 50% <br>
2. java PlagiarismAnalysis syns.txt file1.txt file2.txt  -> 100% <br>
3. java PlagiarismAnalysis syns.txt file3.txt file4.txt -> 100% <br>
4. java PlagiarismAnalysis syns.txt file1.txt file3.txt 4 -> 0% <br>
5. java PlagiarismAnalysis syns.txt file1.txt file3.txt 2 -> 67% <br>
