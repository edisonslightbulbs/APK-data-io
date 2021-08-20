package edisonslightbulbs.accelerometer;

        import android.content.Context;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileWriter;

public class Utils {

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void writeFileOnInternalStorage(Context context, String dir, String fileName, String str){
        File path = new File(context.getFilesDir(), dir);
        if(!path.exists()){
            path.mkdir();
        }

        try {
            File file = new File(path, fileName);
            FileWriter writer = new FileWriter(file);
            writer.append(str);
            writer.flush();
            writer.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}