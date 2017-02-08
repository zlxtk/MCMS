 package com.mingsoft.util;
 
 import java.util.Comparator;
import java.util.Map;
 
 class MapValueComparator
   implements Comparator<Map.Entry<String, String>>
 {
   public int compare(Map.Entry<String, String> str1, Map.Entry<String, String> str2)
   {
     return ((String)str1.getValue()).compareTo((String)str2.getValue());
   }
 }


