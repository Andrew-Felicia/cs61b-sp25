public class Dessert {
    public int flavor;  // instance variables
    public int price;
    static int numDesserts = 0; //static variables
    public Dessert(int f, int p){   //constructor
        flavor = f;
        price = p;
        numDesserts += 1;
    }
    public void printDessert(){
        System.out.println(flavor + " " + price + " " + numDesserts);
    }

    public static void main(String[] args){
        System.out.println("I love dessert!");
    }
}
