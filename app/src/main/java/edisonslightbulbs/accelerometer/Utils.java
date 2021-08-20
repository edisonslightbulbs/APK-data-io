package edisonslightbulbs.accelerometer;

        import android.content.Context;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.FileWriter;
        import java.io.IOException;

public class Utils {

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String filePath(Context context, String dir, String file){
        File contextPath = new File(context.getFilesDir(), dir);
        if(!contextPath.exists()){
            contextPath.mkdir();
        }
        return contextPath + "/" + file;
    }

    // non appending
    public static void writeFile(Context context, String dir, String fileName, String str){
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

    // appending
    public static void writeFile(String file, String str){
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(str);
            writer.close();
            outputStream.getFD().sync();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}