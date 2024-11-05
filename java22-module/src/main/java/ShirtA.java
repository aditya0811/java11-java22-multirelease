public class ShirtA {
  public static void main(String[] args) {
    new ShirtC();
    System.out.println("this is ShirtA from java22-module :" +
        Runtime.version().version().get(0));
  }
}
