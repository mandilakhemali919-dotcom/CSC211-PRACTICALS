import java.util.*;
import java.io.*

public class TryHeapSort{
  private static long startTime;
  private static long endTime;

public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Usage: java TryHeapSort inputfile");
      System.out.println("You gave: " + ArraystoStrings(args));
      System.exit(1);
    }
      
