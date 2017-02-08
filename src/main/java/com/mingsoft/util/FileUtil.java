 package com.mingsoft.util;
 
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.InputStreamReader;
 import java.io.OutputStreamWriter;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public class FileUtil
 {
   public static final String URF8 = "UTF-8";
   
   public static String readFile(String filePath)
   {
     String fileContent = "";
     try
     {
       File f = new File(filePath);
       if ((f.isFile()) && (f.exists()))
       {
         InputStreamReader read = new InputStreamReader(
           new FileInputStream(f), "UTF-8");
         BufferedReader reader = new BufferedReader(read);
         String line;
         while ((line = reader.readLine()) != null)
         {
           fileContent = fileContent + line + "\n";
         }
         reader.close();
         read.close();
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return fileContent;
   }
   
   public static List readFolder(List<Map<String, String>> list, String folderPath)
   {
     if (list == null) {
       return null;
     }
     File file = new File(folderPath);
     File[] tempList = file.listFiles();
     if ((tempList != null) && (tempList.length > 0)) {
       for (File tmpFile : tempList)
       {
         Map<String, String> map = new HashMap();
         map.put("name", file.getName());
         map.put("path", file.getPath());
         list.add(map);
         if (tmpFile.isDirectory()) {
           readFolder(list, tmpFile.getPath());
         }
       }
     }
     return list;
   }
   
   public static void writeFile(String content, String writePath, String charCoder)
   {
     try
     {
       File file = new File(writePath);
       OutputStreamWriter osw = new OutputStreamWriter(
         new FileOutputStream(file), charCoder);
       BufferedWriter reader = new BufferedWriter(osw);
       reader.write(content);
       osw.flush();
       reader.close();
       osw.close();
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
   }
   
   public static void createFolder(String path)
   {
     File file = new File(path);
     if (!file.exists()) {
       file.mkdirs();
     }
   }
   
   public static void delFolders(String folderPath)
   {
     File file = new File(folderPath);
     if (!file.isDirectory())
     {
       file.delete();
       return;
     }
     File[] tempList = file.listFiles();
     if ((tempList != null) && (tempList.length > 0)) {
       for (File tmpFile : tempList) {
         if (tmpFile.isDirectory()) {
           delFolders(tmpFile.getPath());
         } else {
           tmpFile.delete();
         }
       }
     } else {
       file.delete();
     }
     delFolders(file.getPath());
   }
   
   public static void delFile(String path)
   {
     File file = new File(path);
     if (file.exists()) {
       file.delete();
     }
   }
   
   public static void writeTmpFile(String fileName, String content)
   {
     String strDir = System.getProperty("user.dir");
     
     String folderpath = strDir;
     String filepath = folderpath + File.separatorChar + fileName + ".tmp";
     writeFile(content, filepath, "utf-8");
   }
   
   public static String readTmpFile(String fileName)
   {
     String strDir = System.getProperty("user.dir");
     
     String folderpath = strDir;
     String filepath = folderpath + File.separatorChar + fileName + ".tmp";
     return readFile(filepath);
   }
 }


