package cpcs324_project;

import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Batol,Sarah,Joud 
 */
public class Cpcs324_Project {

    /**
     * prints the main menu , takes the user choice of test , call the 2 algorithm that the user choose 
     * @param args
     */
    public static void main(String[] args) {
        // intiliaze n=vertex m=edges of the graph and user choice of the menu variables 
        int n = 0, m = 0, MenuChoice = -1;
        Scanner in = new Scanner(System.in);
        // menu do while 
        do {
            System.out.println("\t\t*** Runtime of Different Minimum Spanning Tree Algorithms ***");
            System.out.println("1- Kruskal's Algorithm & Prim's Algorithm (based on Priority Queue)");
            System.out.println("2- Prim's Algorithm (based on Min Heap) & Prim's Algorithm (based on Priority Queue)");
            System.out.print("> Enter your choice : ");
            MenuChoice = in.nextInt();
            if (MenuChoice != 1 && MenuChoice != 2) {
                System.out.println("****Invalid input!****");
            }
            // menu break condition 
        } while (MenuChoice != 1 && MenuChoice != 2);
        if (MenuChoice == 1 || MenuChoice == 2) {
            System.out.println("> Available cases (where n represents # of vertices and m represents # of edges: )");
            System.out.println(" 1-  n=1,000 - m=10,000");
            System.out.println(" 2-  n=1,000 - m=15,000");
            System.out.println(" 3-  n=1,000 - m=25,000");
            System.out.println(" 4-  n=5,000 - m=15,000");
            System.out.println(" 5-  n=5,000 - m=25,000");
            System.out.println(" 6- n=10,000 - m=15,000");
            System.out.println(" 7-  n=10,000 - m=25,000");
            System.out.println(" 8- n=20,000 - m=200,000");
            System.out.println(" 9- n=20,000 - m=300,000");
            System.out.println("10- n=50,000 - m=1,000,000");
            System.out.print("> Enter a case to test: ");
            int choice = in.nextInt();
            // switch for all avaliable cases of the test 
            while (choice < 1 || choice > 10) {
                System.out.println("Invalid input!");
                System.out.print("> Enter a case to test: ");
                choice = in.nextInt();
            }
            switch (choice) {
                case 1: {
                    n = 1000;
                    m = 10000;
                }
                break;
                case 2: {
                    n = 1000;
                    m = 15000;
                }
                break;
                case 3: {
                    n = 1000;
                    m = 25000;
                }
                break;
                case 4: {
                    n = 5000;
                    m = 15000;
                }
                break;
                case 5: {
                    n = 5000;
                    m = 25000;
                }
                break;
                case 6: {
                    n = 10000;
                    m = 15000;
                }
                break;
                case 7: {
                    n = 10000;
                    m = 25000;
                }
                break;
                case 8: {
                    n = 20000;
                    m = 200000;
                }
                break;
                case 9: {
                    n = 20000;
                    m = 300000;
                }
                break;
                case 10: {
                    n = 50000;
                    m = 1000000;
                }
                break;
            }
          Graph graph = new Graph(n, m);
          graph.make_graph(graph);
          // to perform kruskal and prim priority queue
          if (MenuChoice == 1) {
            graph.kruskal();
            graph.PrimPriorityQueue();
        }
          // to perform prim min heap and prim priority queue
        else if (MenuChoice == 2) {
            graph.PrimMinHeap();
            graph.PrimPriorityQueue();
        }
        }
        
    }
}
