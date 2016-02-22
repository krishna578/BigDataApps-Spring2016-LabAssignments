
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

public class FBReducer extends Reducer<Text, Text, Text, Text> {
    private Text m_result = new Text();


    public void reduce(Text Keys, Iterable<Text> values, Context context) throws IOException, InterruptedException {



            // Prepare a 2-String-Array to hold the values, i.e. the friends lists of
            // our current friends pair.
            String[] combined = new String[2];
            int cur = 0;
            for(Text value : values) {
                combined[cur++] = value.toString();
            }

            // Calculate the intersection of these lists and write result in the form (UserAUserB, MutualFriends).
            m_result.set(intersection(combined[0], combined[1]));
            context.write(Keys, m_result);


        }



    private String intersection(String s1, String s2) {
        HashSet<Character> h1 = new HashSet<Character>();
        HashSet<Character> h2 = new HashSet<Character>();

        for(int i = 0; i < s1.length(); i++) {
            h1.add(s1.charAt(i));
        }
        for(int i = 0; i < s2.length(); i++) {
            h2.add(s2.charAt(i));
        }

        h1.retainAll(h2);
        Character[] res = h1.toArray(new Character[0]);
        String intersect = new String();
        for (int i = 0; i < res.length; i++) {
            intersect += res[i];
        }

        char[] letters = intersect.toCharArray();
        Arrays.sort(letters);
        String sortedIntersect = new String(letters);
        return sortedIntersect;
    }



}