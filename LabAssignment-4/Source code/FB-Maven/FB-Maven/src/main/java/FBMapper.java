
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


public class FBMapper extends Mapper<Object, Text, Text, Text> {
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        {
            Text txtKey = new Text();
            Text txtValue = new Text();
            String Person;
            String Friend;
            String Key;
            String Value;
            String[] StrValue = value.toString().split("->");
            Person = StrValue[0].trim();
            Friend = StrValue[1].trim();
            String[] Friends = Friend.split(" ");
            Value = Friend;
            txtValue.set(Value);

            for (String fri: Friends)
            {

                char chFriend = fri.charAt(0);
                char chPerson = Person.charAt(0);
                if((int)chPerson < (int)chFriend)
                {
                    Key = Person + fri;
                    txtKey.set(Key);

                }
                else
                {
                    Key = fri + Person;
                    txtKey.set(Key);

                }

                context.write(txtKey,txtValue);
            }

        }

    }
}

