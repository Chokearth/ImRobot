package fr.chokearth.irobot;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileSystem {

    String path = "/";
    final File directory = new File("./ImRobot/lua");

    public FileSystem() {
        File directoryLua = new File("./ImRobot/lua/");
        if(true){
            directory.mkdirs();

            BufferedReader aliasFile = null;
            InputStream in = null;
            OutputStream out = null;
            try {
                aliasFile = new BufferedReader(new InputStreamReader(ImRobot.class.getClassLoader().getResourceAsStream("./assets/irobot/lua/FileAlias.txt")));

                String name = aliasFile.readLine();

                while (name != null){
                    if(!name.equals("") && !name.equals("FileAlias.txt")) {
                        name = name.replace('\\', '/');
                        File file = new File(directoryLua.getPath()+"/"+name);

                        file.getParentFile().mkdirs();

                        in = ImRobot.class.getClassLoader().getResourceAsStream("./assets/irobot/lua/"+name);
                        out = new FileOutputStream(new File(directoryLua.getPath()+"/"+name));


                        byte[] buffer = new byte[1024];
                        int length;
                        while((length = in.read(buffer)) > 0)
                            out.write(buffer, 0, length);

                        in.close();
                        out.close();

//                        FileUtils.copyFile(new File(ImRobot.class.getClassLoader().getResource("./assets/irobot/lua/"+name).getFile()), new File(directoryLua.getPath()+"/"+name));
                    }
                    name = aliasFile.readLine();
                }
                aliasFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String[] ls(){
        return new File(directory.getPath()+"/"+path).list();
    }

    public boolean cd(String pathCd){
        File dir = new File(directory.getPath()+path+pathCd+"/");
        if(dir.exists() && dir.isDirectory()) {
            path=Paths.get(path+pathCd+"/").normalize().toString().replace('\\', '/')+"/";
            if(path.equals("//")) path = "/";
            return true;
        }
        return false;
    }

    public String getPath() {
        return path;
    }

    public String getDirectory() {
        return directory.getPath().replace('\\', '/');
    }
}
