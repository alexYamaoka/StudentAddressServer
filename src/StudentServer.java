import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StudentServer
{
    // Object input and output stream
    private ObjectOutputStream outputToFile;
    private ObjectInputStream inputFromClient;


    public static void main(String[] args)
    {
        new StudentServer();
    }



    public StudentServer()
    {
        try
        {
            // Create a server socket
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server Started");



            // create an object output stream
            outputToFile = new ObjectOutputStream(new FileOutputStream("student.dat", true));




            while (true)
            {
                // listen for a new connection request
                Socket socket = serverSocket.accept();

                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("Client name: " + inetAddress.getHostName());
                System.out.println("Client IP address: " + inetAddress.getHostAddress());
                System.out.println();


                // create an input stream from the socket
                inputFromClient = new ObjectInputStream(socket.getInputStream());



                // read from input
                Object object = inputFromClient.readObject();


                System.out.println(object);

                // write to the file
                outputToFile.writeObject(object);

                System.out.println("A new student object stored");


            }




        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("class not found");
            System.out.println(ex.getMessage());
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            try
            {
                inputFromClient.close();
                outputToFile.close();
            }
            catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }




}
