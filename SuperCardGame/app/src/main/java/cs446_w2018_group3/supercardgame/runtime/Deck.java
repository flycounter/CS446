package cs446_w2018_group3.supercardgame.runtime;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by yandong on 2018-03-26.
 */

public class Deck {
    private String filePath;
    private FileOutputStream out;
    private FileInputStream in;

    public static void main (String[] args) {
        //System.out.printf("hello world\n");
        String path = "/Users/yandong/Documents/cs446/CS446/SuperCardGame/app/src/main/java/cs446_w2018_group3/supercardgame/runtime/data.json";
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            @SuppressWarnings("resource")
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
            jsonWriter.beginObject();
            jsonWriter.name("id").value("1");
            jsonWriter.endObject();
            jsonWriter.close();
            //@SuppressWarnings("resurce")
            //JsonReader jsonReader = new JsonReader(new InputStreamReader(fileInputStream, "UTF-8"));
            //jsonReader.beginObject();
            //System.out.printf("test:"+jsonReader.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
