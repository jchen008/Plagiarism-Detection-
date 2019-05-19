# Plagiarism-Detection
# Zhuo Chen
## Assumption
### 1. Tuples can match no matter where they are in the text
### 2. If either file is empty, output 0%
## Algorithm
### 1. Synonym file is parsed into a Hashmap which stores key-value pair <word, synonymSet>.
### 2. First string file is parsed and all tuples are constructed as String arrays.
### 3. Second string file is parsed and all tuples are constructed as String arrays
### 4. Compare each tuple from the first file to each tuple from the second file and get the count of synonyms
### 5. Divide the count of synonyms between two string files by the number of tuples in the second file to get the final result (the percent of tuples in file1 which appear in file2)
## Testing Procedure
### command -> right output
### 1. java PlagiarismAnalysis syns.txt file1.txt file3.txt  -> 50%
### 2. java PlagiarismAnalysis syns.txt file1.txt file2.txt  -> 100%
### 3. java PlagiarismAnalysis syns.txt file3.txt file4.txt -> 100%
### 4. java PlagiarismAnalysis syns.txt file1.txt file3.txt 4 -> 0%
### 5. java PlagiarismAnalysis syns.txt file1.txt file3.txt 2 -> 67%
