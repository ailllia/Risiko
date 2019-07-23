package risikopackage;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Lasst uns spielen!");
        
        Player playerone = new Player("blue", "none", 0, 2);
        Player playertwo = new Player("green", "none", 0, 2);
        
        //PlayersGUI g = new PlayersGUI();	
        
        Test test = new Test();
        test.printSometing();
    }
}