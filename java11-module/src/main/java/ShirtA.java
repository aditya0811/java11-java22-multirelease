
public class ShirtA {
  public static void main(String[] args) {
    new ShirtB();
    System.out.println("this is ShirtA from java11-module :" +
        Runtime.version().version().get(0));
  }
}
