/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarypersistentinterceptorsclient;


import com.tutorialspoint.interceptor.LibrarySessionInterceptorsRemote;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author tiago.lucas
 */
public class LibraryPersistentInterceptorsClient {

    BufferedReader brConsoleReader = null;
    Properties props;
    InitialContext ctx;

    {

        props = new Properties();
        try {
            props.load(new FileInputStream("jndi.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            ctx = new InitialContext(props);
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        brConsoleReader
                = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LibraryPersistentInterceptorsClient ejbTester = new LibraryPersistentInterceptorsClient();
        ejbTester.testInterceptedEjb();
    }

    private void showGUI(){
        System.out.println("************************");
        System.out.println("Welcome to Book Store");
        System.out.println("************************");
        System.out.print("Options\n1. Add Book\n2. Exit\nEnterChoice: ");
    }
    
    private void testInterceptedEjb() {
        try{
            int choice = 1;
            LibrarySessionInterceptorsRemote libraryBean =
                    (LibrarySessionInterceptorsRemote)ctx.lookup("LibrarySessionInterceptors/remote");
            
            while(choice != 2){
                String bookName;
                showGUI();
                String strChoice = brConsoleReader.readLine();
                choice = Integer.parseInt(strChoice);
                if(choice == 1){
                    System.out.println("Enter book name: ");
                    bookName = brConsoleReader.readLine();
                    libraryBean.addBooks(bookName);
                }else if(choice == 2)
                    break;
            }
            List<String> booksList = libraryBean.getBooks();
            
            System.out.println("Book(s) entered so far: "+booksList.size());
            for(int i=0;i<booksList.size();i++){
                System.out.println((i+1)+". "+booksList.get(i));
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }finally{
            try{
                if(brConsoleReader != null){
                    brConsoleReader.close();
                }
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
}
