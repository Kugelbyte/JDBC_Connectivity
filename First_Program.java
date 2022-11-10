import java.sql.*;
import java.util.Scanner;

public class First_Program {


    public static void insertProduct(Connection con,String name, int quantity, int price)
    {   try {

        Statement statement = con.createStatement();
        statement.execute("INSERT INTO Product (Name, Quantity, Price) VALUES ("+"'"+name+"'"+","+quantity+","+price+");");



    }catch (SQLException se)
    {
        se.printStackTrace();
    }

    }

    public static void deleteProduct(Connection con,String product_name)
    {
        try
        {
            Statement stat = con.createStatement();

            Statement stat2 = con.createStatement();
            Statement stat3 = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT PID FROM Product WHERE Name="+"'"+product_name+"';");
            rs.next();
            int set_pid = rs.getInt("PID");
            stat2.execute("DELETE FROM Product WHERE Name="+"'"+product_name+"';");
            stat3.execute("ALTER TABLE Product AUTO_INCREMENT="+set_pid);
        }
        catch (SQLException se){se.printStackTrace();}
    }


    public static void updateProduct(Connection con,String product_name)
    {
        try
        {   Scanner sc = new Scanner(System.in);
            Statement stat = con.createStatement();

            System.out.print("1. Change Quantity\n 2. Change Price\n 3. Change Both\n");

            System.out.println("What you want to do ?: ");

            int choice = sc.nextInt();

            switch (choice)
            {
                case 1: int quantity = sc.nextInt();
                        stat.execute("UPDATE Product SET Quantity="+quantity+" WHERE Name="+"'"+product_name+"';");
                        break;
                case 2: int price = sc.nextInt();
                        stat.execute("UPDATE Product SET Price="+price+" WHERE Name="+"'"+product_name+"';");
                        break;
                case 3: System.out.println("Enter Quantity and Price respectively: ");
                        quantity = sc.nextInt();
                        price = sc.nextInt();
                        stat.execute("UPDATE Product SET Quantity="+quantity+", Price="+price+" WHERE Name="+"'"+product_name+"';" );
                        break;
                default: System.out.println("Error occurred");
            }



        }catch (SQLException se)
        {
            se.printStackTrace();
        }
    }



    public static void showAll(Connection con)
    {
        try
        {
            Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT PID, Name, Quantity, Price FROM Product");

            while (rs.next())
            {   int pid  = rs.getInt("PID");
                String name = rs.getString("Name");
                int Quantity = rs.getInt("Quantity");
                int price = rs.getInt("Price");
                System.out.print("PID: " + pid);
                System.out.print(", Name: " + name);
                System.out.print(", Quantity: " + Quantity);
                System.out.println(", Price: " + price);
            }
        }
        catch (SQLException se){se.printStackTrace();}
    }



    public static void main(String [] args)
    {


        try
        {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shop","root","mysql");
        Scanner sc = new Scanner(System.in);
        int Choice;
        String name; int price; int quantity;

        char choice_count;

        do
        {   System.out.print("1. Insert Product\n 2. Delete Product\n 3. Update Product Information\n 4. Show All");
            Choice = sc.nextInt();
            switch (Choice) {
                case 1 -> {
                    System.out.println("Enter Product Name: ");
                    name = sc.next();
                    System.out.println("Enter Product Quantity: ");
                    quantity = sc.nextInt();
                    System.out.println("Enter Product Price: ");
                    price = sc.nextInt();
                    insertProduct(connection, name, quantity, price);
                }
                case 2 -> {
                    System.out.println("Enter the Product Name to delete: ");
                    name = sc.next();
                    deleteProduct(connection, name);
                }
                case 3 -> {
                    System.out.println("Enter Product Name: ");
                    name = sc.next();
                    updateProduct(connection, name);
                }
                case 4 -> {
                    showAll(connection);
                }
                default -> System.out.println("Something went wrong");
            }
                System.out.println("Do something else?: ");
                choice_count = sc.next().charAt(0);
        }while(choice_count == 'y' || choice_count=='Y');

            connection.close();

        }

        catch(SQLException se)
        {
            se.printStackTrace();
        }


    }

}
