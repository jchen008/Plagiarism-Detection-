import java.util.*;

public interface Detector{
    public boolean isSyn(String[] tuple1, String[] tuple2, HashMap<String, HashSet<String>> synsMap);
}