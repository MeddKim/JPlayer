package com.jplayer.player.utils;

import com.google.common.base.Strings;
import com.jplayer.MainLauncher;
import com.jplayer.player.domain.*;
import com.jplayer.player.enums.ChapterBtnEnum;
import com.jplayer.player.enums.FileType;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jplayer.MainLauncher.globalMd5List;

/**
 * @author Willard
 * @date 2019/9/26
 */
@Slf4j
public class CommonUtils {

    /**
     * 编号名称分隔符
     */
    public static final String ID_NAME_SPLIT = "\\.";

    public static final String BG_PREX = "file:";
    public static final String VIDEO_PREX = "file:/";
    public static final String BG_NAME = "lesson.jpg";

    public static final String SALT = "34mnFedp";

    public static ArrayList<ModuleInfo> getModuleInfo(String path){
        File root = new File(path);
        ArrayList<ModuleInfo> moduleInfos = new ArrayList<>();
        for(File dir : root.listFiles()){
            if(dir.isDirectory()){
                String[] names = dir.getName().split(ID_NAME_SPLIT);
                if(names.length == 2){
                    ModuleInfo moduleInfo = new ModuleInfo();
                    moduleInfo.setModuleId(names[0]);
                    moduleInfo.setModuleName(names[1]);
                    moduleInfo.setModulePath(dir.getAbsolutePath());
                    moduleInfo.setBgUrl(BG_PREX + dir.getAbsolutePath() + File.separator + "bg.png");
                    moduleInfos.add(moduleInfo);
                }
            }
        }
        return moduleInfos;
    }

    public static ArrayList<ThemeInfo> getThemeInfo(String path){
        File root = new File(path);
        ArrayList<ThemeInfo> themeInfos = new ArrayList<>();
        for (File themeDir : root.listFiles()){
            if(themeDir.isDirectory()){
                String[] names = themeDir.getName().split(ID_NAME_SPLIT);
                if(names.length == 2){
                    ArrayList<CourseBaseInfo> courseBaseInfos = new ArrayList<>();
                    ThemeInfo themeInfo = new ThemeInfo();
                    themeInfo.setThemeId(names[0]);
                    themeInfo.setThemeName(names[1]);
                    themeInfo.setThemePath(themeDir.getAbsolutePath());
                    //获取课程信息
                    for(File courseDir : themeDir.listFiles()){
                        if(courseDir.isDirectory()){
                            String[] courseNames = courseDir.getName().split(ID_NAME_SPLIT);
                            if(courseNames.length == 2){
                                CourseBaseInfo courseBaseInfo = new CourseBaseInfo();
                                courseBaseInfo.setBgUrl( BG_PREX + courseDir.getAbsolutePath() + File.separator +BG_NAME);
                                courseBaseInfo.setCourseId(courseNames[0]);
                                courseBaseInfo.setCourseName(courseNames[1]);
                                courseBaseInfo.setCoursePath(courseDir.getAbsolutePath());
                                courseBaseInfos.add(courseBaseInfo);
                            }
                        }
                    }
                    themeInfo.setCourse(courseBaseInfos);
                    themeInfos.add(themeInfo);
                }
            }
        }
        return themeInfos;
    }


    public static ArrayList<ChapterInfo> getChapterInfo(String path){
        return getChapterInfo(path,false);
    }
    public static ArrayList<ChapterInfo> getChapterInfo(String path,Boolean isCalMd5){
        ArrayList<ChapterInfo> chapterInfos = new ArrayList<>();
        File root = new File(path);
        for(File chapterDir: root.listFiles()){
            if(chapterDir.isDirectory()){
                String[] chapterNames = chapterDir.getName().split(ID_NAME_SPLIT);
                if(chapterNames.length == 2){
                    ChapterInfo chapterInfo = new ChapterInfo();
                    chapterInfo.setIsSelected(false);
                    chapterInfo.setChapterId(chapterNames[0]);
                    chapterInfo.setChapterName(chapterNames[1]);
                    chapterInfo.setChapterPath(chapterDir.getAbsolutePath());
                    chapterInfo.setChapterType(ChapterBtnEnum.getTypeFromDir(chapterNames[1]));
                    List<ChapterFile> chapterFiles = new ArrayList<>();
                    if(ChapterBtnEnum.LESSON_PLAN.equals(chapterInfo.getChapterType())){
                        ChapterFile chapterFile = new ChapterFile();
                        chapterFile.setPlayUrl(chapterDir.getAbsolutePath() + File.separator + "jiaoan.pdf");
                        chapterFile.setType(FileType.PDF);
                        chapterFiles.add(chapterFile);
                    }else if(ChapterBtnEnum.FAMILY.equals(chapterInfo.getChapterType())){
                        ChapterFile chapterFile = new ChapterFile();
                        chapterFile.setPlayUrl(chapterDir.getAbsolutePath() + File.separator + "jiating.pdf");
                        chapterFile.setType(FileType.PDF);
                        chapterFiles.add(chapterFile);
                    }else {
                        for(File fileDir : chapterDir.listFiles()){
                            if(fileDir.isDirectory()){
                                String[] chapterFileNames = fileDir.getName().split(ID_NAME_SPLIT);
                                if(chapterFileNames.length == 2){
                                    ChapterFile chapterFile = new ChapterFile();
                                    chapterFile.setFileId(chapterFileNames[0]);
                                    chapterFile.setFileName(chapterFileNames[1]);
                                    if(chapterFileNames[1].contains("视频")){
                                        chapterFile.setType(FileType.VEDIO);
                                        chapterFile.setPlayUrl(VIDEO_PREX + fileDir.getAbsolutePath() + File.separator + "video.mp4");
                                        chapterFile.setThumbUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator +"s.jpg");
                                    }else if(chapterFileNames[1].contains("教案") || chapterFileNames[1].contains("家庭")){
                                        chapterFile.setType(FileType.PDF);
                                        chapterFile.setPlayUrl(fileDir.getAbsolutePath() + File.separator + "jiaoan.pdf");
                                    }else{
                                        chapterFile.setType(FileType.IMG);
                                        chapterFile.setBgUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator + "m.jpg");
                                        chapterFile.setPlayUrl(fileDir.getAbsolutePath() + File.separator + "voice.mp3");
                                        chapterFile.setThumbUrl(BG_PREX + fileDir.getAbsolutePath() + File.separator + "s.jpg");
                                    }
                                    chapterFiles.add(chapterFile);
                                }
                            }
                        }
                    }
                    chapterInfo.setChapterFiles(chapterFiles);
                    chapterInfos.add(chapterInfo);
                }
            }
        }
        return chapterInfos;
    }


    public static String getNextCoursePath(String currentCoursePath){
        File file = new File(currentCoursePath);
        File parent = file.getParentFile();
        File[] children = parent.listFiles();
        Boolean isStopFind = false;
        for(int i=0; i< children.length; i++){
            if(i == children.length && children[i].getAbsolutePath().equals(currentCoursePath)){
                return null;
            }
            if(isStopFind){
               return children[i].getAbsolutePath();
            }
            if(children[i].getAbsolutePath().equals(currentCoursePath)){
                isStopFind = true;
            }
        }
        return null;
    }

    public static String getPreCoursePath(String currentCoursePath){
        File file = new File(currentCoursePath);
        File parent = file.getParentFile();
        File[] children = parent.listFiles();
        Boolean isStopFind = false;
        String prePath = null;
        for(int i=0; i< children.length; i++){
            if(0 == i && children[i].getAbsolutePath().equals(currentCoursePath)){
                return null;
            }
            if(children[i].getAbsolutePath().equals(currentCoursePath)){
                isStopFind = true;
            }
            if(isStopFind){
                return prePath;
            }
            prePath = children[i].getAbsolutePath();
        }
        return null;
    }


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }


    public static String calFileMD5(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi.toString(16);
    }

    public static String getMD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }catch (Exception e){
            log.error("拷贝文件异常");
        }finally {
            if(inputChannel != null){
                try {
                    inputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputChannel != null){
                try {
                    outputChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getMD5(String filePath,String salt){
        String sourceMd5 = getMd5ByFile(filePath);
        String targetMd5 = getMD5( salt + sourceMd5 + salt);
        return targetMd5;
    }

    public static String getMd5ByFile(String filePath) {
        log.info("当前加密文件路径：{}",filePath);
        if(Strings.isNullOrEmpty(filePath)){
            return null;
        }
        if(filePath.startsWith(VIDEO_PREX)){
            filePath = StringStartTrim(filePath,VIDEO_PREX);
        }else if(filePath.startsWith(BG_PREX)){
            filePath = StringStartTrim(filePath,BG_PREX);
        }
        File file = new File(filePath);
        if(!file.exists()){
            return null;
        }
        InputStream fis;
        byte[] buffer = new byte[2048];
        int numRead = 0;
        MessageDigest md5;

        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return md5ToString(md5.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }


    public static String StringStartTrim(String stream, String trim) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trim == null || trim.length() == 0) {
            return stream;
        }
        // 要删除的字符串结束位置
        int end;
        // 正规表达式
        String regPattern = "[" + trim + "]*+";
        Pattern pattern = Pattern.compile(regPattern, Pattern.CASE_INSENSITIVE);
        // 去掉原始字符串开头位置的指定字符
        Matcher matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            end = matcher.end();
            stream = stream.substring(end);
        }
        // 返回处理后的字符串
        return stream;
    }


    public static String md5ToString(byte[] md5Bytes) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String getSHA256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodestr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                // 1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public static Boolean checkMD5(ArrayList<ChapterInfo> chapterInfos){
        for(ChapterInfo chapterInfo : chapterInfos){
            for(ChapterFile chapterFile : chapterInfo.getChapterFiles()){
                if(!Strings.isNullOrEmpty(chapterFile.getBgUrl())){
                    String md5 = getMD5(chapterFile.getBgUrl(),SALT);
                    if(!globalMd5List.contains(md5)){
                        return false;
                    }
                }
                if(!Strings.isNullOrEmpty(chapterFile.getPlayUrl())){
                    String md5 = getMD5(chapterFile.getPlayUrl(),SALT);
                    if(!globalMd5List.contains(md5)){
                        return false;
                    }
                }
                if(!Strings.isNullOrEmpty(chapterFile.getThumbUrl())){
                    String md5 = getMD5(chapterFile.getThumbUrl(),SALT);
                    if(!globalMd5List.contains(md5)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
//        ArrayList<ChapterInfo> chapterInfos = getChapterInfo("E:\\course\\0.FS未来素养课程\\0.欺凌预防\\0.识别欺凌(1)");
//        System.out.println(chapterInfos.size());
//        String next = getNextCoursePath("D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\1.识别欺凌(2)");
//        String next = getNextCoursePath("D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\9.鱼拓印");
//        System.out.println(next);

//                String pre = getPreCoursePath("D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\1.识别欺凌(2)");
//        String pre = getNextCoursePath("D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\9.鱼拓印");
//        System.out.println(pre);


//        String value = calPathMD5("D:\\dev\\app\\javaWorkspace\\JPlayer\\course\\0.FS未来素养课程\\0.欺凌预防\\1.识别欺凌(2)\\1.导入\\0.导入视频\\play.mp4");
//        System.out.println(value);

        System.out.println(getMD5("C:\\dev\\app\\JPlayer\\course\\0.FS未来素养课程\\01.欺凌预防\\01.识别欺凌1\\01.导入\\05.视频\\s.jpg",SALT));
        System.out.println(getMD5("C:\\dev\\app\\JPlayer\\course\\0.FS未来素养课程\\01.欺凌预防\\01.识别欺凌1\\01.导入\\05.视频\\m.jpg",SALT));
        System.out.println(getMD5("C:\\dev\\app\\JPlayer\\course\\0.FS未来素养课程\\01.欺凌预防\\01.识别欺凌1\\01.导入\\05.视频\\video.mp4",SALT));
    }
}
