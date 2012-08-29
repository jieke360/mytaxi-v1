/***/
package BackupUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * AngeFirst
 * 
 * @author yinzhiping Backup.java
 * @version 1.0, 2011-11-18 上午09:59:45
 */
public class Backup {
    public static List<File> list(File dir, String nametxt, String ext, String type, List<File> fs) {
        listFile(dir, nametxt, type, ext, fs);
        File[] all = dir.listFiles();
        // 递归获得当前目录的所有子目录
        for (int i = 0; i < all.length; i++) {
            File d = all[i];
            if (d.isDirectory()) {
                list(d, nametxt, ext, type, fs);
            }
        }
        return null;
        // 遍历子目录 ，列出每个子目录的文件
    }

    private static List<File> listFile(File dir, String nametxt, String type, String ext, List<File> fs) {
        File[] all = dir.listFiles(new Fileter(ext));
        for (int i = 0; i < all.length; i++) {
            File d = all[i];
            if (d.getName().toLowerCase().indexOf(nametxt.toLowerCase()) >= 0) {
                if (type.equals("1")) {
                    fs.add(d);
                }
                else if (d.isDirectory() && type.equals("2")) {
                    fs.add(d);
                }
                else if (!d.isDirectory() && type.equals("3")) {
                    fs.add(d);
                }
            }

        }
        return fs;
    }

    public static boolean delFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            }
            else {
                bea = false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bea;
    }

    static class Fileter implements FilenameFilter {
        private final String ext;

        public Fileter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(ext);

        }
    }

    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { // 文件存在
                InputStream inStream = new FileInputStream(oldPathFile); // 读入源文件
                File n = new File(newPathFile);
                if (!n.exists()) {
                    n.createNewFile();
                }
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    public static void main(String[] args) {

        // .java 转为.txt
        File f = new File("src/");
        List<File> files = new ArrayList<File>();
        list(f, "", ".java", "3", files);
        for (File file : files) {

            String name = file.getName().substring(0, file.getName().indexOf(".java")) + ".txt";
            copyFile(file.getAbsolutePath(), file.getParent() + "/" + name);
            String path = "back\\" + file.getParent().substring(file.getParent().indexOf("src") + 3);
            File ff = new File(path);
            ff.mkdirs();
            moveFile(file.getParent() + File.separator + name, ff.getPath() + "/" + name);
            if (!file.getName().equals("Backup.java")) {
                delFile(file.getParent() + "/" + name);
            }
        }
        // .txt 转为.java/**
        /*
         * List<File> files2 = new ArrayList<File>(); File f2 = new
         * File("back"); list(f2, "", ".txt", "3", files2); if (files2.size() >
         * 1) { for (File file : files2) { String name =
         * file.getName().substring(0, file.getName().indexOf(".txt")) +
         * ".java"; File newFile = new File(file.getParent() + "/" + name);
         * file.renameTo(newFile); } }
         */

    }
}
