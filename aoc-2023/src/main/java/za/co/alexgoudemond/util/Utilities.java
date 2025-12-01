package za.co.alexgoudemond.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Utilities {

  public static List<String> getDocumentContents(String docName) {
    List<String> documentContents = new ArrayList<>();
    try (Scanner reader = new Scanner(new File(docName))) {
      while (reader.hasNextLine()) {
        documentContents.add(reader.nextLine());
      }
    } catch (FileNotFoundException e) {
      System.out.println("File " + docName + " does not exist");
    }
    return documentContents;
  }

  //todo - play with
  public static List<String> getDocumentContentsNio1(String docName){
    try {
//      return Files.lines(Path.of(docName)); // : Stream<String>
      return Files.readAllLines(Path.of(docName));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //todo - play with
  public static List<String> getDocumentContentsIo1(String docName){
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(docName)))) {
      return reader.lines().collect(Collectors.toList());
    } catch (FileNotFoundException e) {
      throw new RuntimeException("File " + docName + " does not exist");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
