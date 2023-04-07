package com.challenge.challenge.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class FileReaderUtil {

  public static List<String> readFile(String zonesFilesPath) {

    try (BufferedReader r = new BufferedReader(new FileReader(zonesFilesPath))) {
      List<String> lines = new ArrayList<>();
      String s = r.readLine();

      while (s != null) {
        lines.add(s);
        s = r.readLine();
      }

      return lines;
    } catch (IOException e){
      return new ArrayList<>();
    }
  }

  public static String getFilePath(String file){
    return String.format("./%s", file);
  }
}
