import java.sql.*;
import java.util.Scanner;

public class Second_Program {



    public static void sellProduct(Connection con,String product_name,int product_quantity)
    {
        try
        {
            Statement stat = con.createStatement();
            Statement stat2 = con.createStatement();
            Statement stat3 = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT Quantity FROM Product WHERE Name="+"'"+product_name+"';");

            ResultSet rs2 = stat2.executeQuery("SELECT Price FROM Product WHERE Name="+"'"+product_name+"';");
            int initial_product_quantity;
            int initialPrice;
            rs.next();
            initial_product_quantity = rs.getInt("Quantity");
            rs2.next();
            initialPrice = rs2.getInt("Price");
            int updated_product_quantity = initial_product_quantity - product_quantity;
            int totalPrice = initialPrice*product_quantity;
            stat3.execute("UPDATE Product SET Quantity="+updated_product_quantity+" WHERE Name="+"'"+product_name+"';");

            System.out.println("Total Price: "+totalPrice);

        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }

    public static void viewProduct(Connection con,String product_name)
    {
        try
        {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT Name, Quantity, Price FROM Product WHERE Name="+"'"+product_name+"';");

            while(rs.next()){System.out.println("Name: "+rs.getString("Name")+"  Quantity: "+rs.getString("Quantity")+"  Price: "+rs.getString("Price"));}
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shop","root","mysql");
            Scanner sc = new Scanner(System.in);


            int Choice;
            char choice_count;
            do
            {   System.out.print("1. View Product\n 2.Sell Product\n");
                System.out.println("Enter your choice: ");
                Choice = sc.nextInt();

                switch (Choice)
                {
                    case 1 ->
                            {
                                System.out.println("Enter product name: ");
                                String name = sc.next();
                                viewProduct(connection,name);

                            }
                    case 2 ->
                            {
                                System.out.println("Enter product name and quantity to sell: ");
                                String name = sc.next();
                                int quantity = sc.nextInt();
                                sellProduct(connection,name,quantity);
                            }
                    default -> System.out.println("Something went wrong.");
                }
                System.out.println("Do something else?: ");
                choice_count = sc.next().charAt(0);
            }while(choice_count == 'y' || choice_count == 'Y');

            connection.close();
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }
}
