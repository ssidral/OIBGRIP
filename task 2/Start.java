
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import net.proteanit.sql.DbUtils;
import javax.swing.*;
 
public class Start {
     
    public static class ex{
        public static int days=0;
            }
 
    public static void main(String[] args) {
         
        login_Form();
        create();
    }
    public static void login_Form(){
        
        JFrame f=new JFrame("Login");//creating instance of JFrame  
        
        JLabel l1,l2;  
        l1=new JLabel("Username"); 
        l1.setBounds(80,15, 100,30); 
         
        l2=new JLabel("Password"); 
        l2.setBounds(80,50, 100,30);    
         
        JTextField F_user = new JTextField(); 
        F_user.setBounds(160, 15, 200, 30);
             
        JPasswordField F_pass=new JPasswordField(); 
        F_pass.setBounds(160, 50, 200, 30);
           
        JButton login_but=new JButton("Login");
        login_but.setBounds(160,90,80,25);
        
        login_but.addActionListener(new ActionListener() {  
             
            public void actionPerformed(ActionEvent e){ 
     
            String username = F_user.getText(); 
            String password = F_pass.getText(); 
             
            if(username.equals("")) 
            {
                JOptionPane.showMessageDialog(null,"Please enter username"); 
            } 
            else if(password.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please enter password"); 
            }
            else { 
                Connection connection=connect(); 
                try
                {
                Statement stmt = connection.createStatement();
                  stmt.executeUpdate("USE LIBRARY");
                  String st = ("SELECT * FROM USERS WHERE USERNAME='"+username+"' AND PASSWORD='"+password+"'"); 
                  ResultSet rs = stmt.executeQuery(st); 
                  if(rs.next()==false) { 
                      System.out.print("No user");  
                      JOptionPane.showMessageDialog(null,"Wrong Username/Password!");
     
                  }
                  else {
                      f.dispose();
                    rs.beforeFirst();  
                    while(rs.next())
                    {
                      String admin = rs.getString("ADMIN"); 
                     
                      String UID = rs.getString("UID"); 
                      if(admin.equals("1")) { 
                          admin_menu(); 
                      }
                      else{
                          user_menu(UID); 
                      }
                  }
                  }
                }
                catch (Exception ex) {
                     ex.printStackTrace();
            }
            }
        }               
        });

			JLabel backround=new JLabel(new ImageIcon("C:\\Users\\shri\\eclipse-workspace\\Library\\src\\back.jpg"));
			f.setContentPane(backround);
        f.add(F_pass); 
        f.add(login_but);
        f.add(F_user);  
        f.add(l1);  
        f.add(l2); 
       
        f.setSize(700,400);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
       f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static Connection connect()
    {
    try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/mysql?autoReconnect=true&useSSL=false","root","8408");
            
            return con;
     } 
     catch (Exception ex) {
            ex.printStackTrace();
     }
    return null;
    }
    
    public static void create() {
        try {
        Connection connection=connect();
        ResultSet resultSet = connection.getMetaData().getCatalogs();
      
            while (resultSet.next()) {
              
              String databaseName = resultSet.getString(1);
              if(databaseName.equals("library")) {
                  
                  Statement stmt = connection.createStatement();
                 
                  String sql = "DROP DATABASE library";
                  stmt.executeUpdate(sql);
              }
            }
              Statement stmt = connection.createStatement();
               
              String sql = "CREATE DATABASE LIBRARY"; //Create Database
              stmt.executeUpdate(sql); 
              stmt.executeUpdate("USE LIBRARY"); //Use Database
              //Create Users Table
              String sql1 = "CREATE TABLE USERS(UID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, USERNAME VARCHAR(30), PASSWORD VARCHAR(30), ADMIN BOOLEAN)";
              stmt.executeUpdate(sql1);
              //Insert into users table
              stmt.executeUpdate("INSERT INTO USERS(USERNAME, PASSWORD, ADMIN) VALUES('admin','admin',TRUE)");
              //Create Books table
              stmt.executeUpdate("CREATE TABLE BOOKS(BID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, BNAME VARCHAR(50), GENRE VARCHAR(20), PRICE INT)");
              //Create Issued Table
              stmt.executeUpdate("CREATE TABLE ISSUED(IID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, UID INT, BID INT, ISSUED_DATE VARCHAR(20), RETURN_DATE VARCHAR(20), PERIOD INT, FINE INT)");
              //Insert into books table
              stmt.executeUpdate("INSERT INTO BOOKS(BNAME, GENRE, PRICE) VALUES ('White Tiger', 'Mystery', 200),  ('Nectar in a Sieve', 'Fiction', 300), ('TThe Great Indian Novel','Mystery', 150), ('Train to Pakistan', 'Biography', 250), ('Palace of Illusions','Fiction', 350)");
               
        resultSet.close();
        }
         catch (Exception ex) {
             ex.printStackTrace();
    }
    }
    public static void user_menu(String UID) {
        
        
        JFrame f=new JFrame("User Functions"); //Give dialog box name as User functions
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit user menu on closing the dialog box
        JButton view_but=new JButton("View Books");//creating instance of JButton  
        view_but.setBounds(20,20,120,25);//x axis, y axis, width, height 
        view_but.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                 
                JFrame f = new JFrame("Books Available"); //View books stored in database
                //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 
                 
                Connection connection = connect();
                String sql="select * from BOOKS"; //Retreive data from database
                try {
                    Statement stmt = connection.createStatement(); //connect to database
                     stmt.executeUpdate("USE LIBRARY"); // use librabry
                    stmt=connection.createStatement();
                    ResultSet rs=stmt.executeQuery(sql);
                    JTable book_list= new JTable(); //show data in table format
                    book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                      
                    JScrollPane scrollPane = new JScrollPane(book_list); //enable scroll bar
     
                    f.add(scrollPane); //add scroll bar
                    f.setSize(800, 400); //set dimensions of view books frame
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }               
                 
        }
        }
        );
         
        JButton my_book=new JButton("My Books");//creating instance of JButton  
        my_book.setBounds(150,20,120,25);
        my_book.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                 
                   
                JFrame f = new JFrame("My Books"); //View books issued by user
                //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                int UID_int = Integer.parseInt(UID); //Pass user ID
     
               
                Connection connection = connect(); 
               
                String sql="select distinct issued.*,books.bname,books.genre,books.price from issued,books " + "where ((issued.uid=" + UID_int + ") and (books.bid in (select bid from issued where issued.uid="+UID_int+"))) group by iid";
                String sql1 = "select bid from issued where uid="+UID_int;
                try {
                    Statement stmt = connection.createStatement();
                    
                     stmt.executeUpdate("USE LIBRARY");
                    stmt=connection.createStatement();
                   
                    ArrayList books_list = new ArrayList();
      
                    
                     
                    ResultSet rs=stmt.executeQuery(sql);
                    JTable book_list= new JTable(); 
                    book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                   
                    JScrollPane scrollPane = new JScrollPane(book_list);
     
                    f.add(scrollPane); 
                    f.setSize(800, 400); 
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }               
                     
        }
        }
        );
        
        JButton log_out=new JButton("Log out"); //creating instance of JButton to Log Out
        log_out.setBounds(150,80,120,25); 
        
        log_out.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				login_Form();
				f.setVisible(false);
			}
		});
         
               
        f.add(my_book); 
        f.add(view_but); 
        f.add(log_out);
        f.setSize(300,200);
        f.setLayout(null);
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        }
    
    public static void admin_menu() {
        
        
        JFrame f=new JFrame("Admin Functions"); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
         
         
        JButton create_but=new JButton("Create/Reset");//creating instance of JButton to create or reset database
        create_but.setBounds(180,180,120,25);
        create_but.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                 
                create();
                JOptionPane.showMessageDialog(null,"Database Created/Reset!"); 
                 
            }
        });
         
         
        JButton view_but=new JButton("View Books");//creating instance of JButton to view books
        view_but.setBounds(50,20,120,25);
        view_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                 
                JFrame f = new JFrame("Books Available"); 
               
                 
                 
                Connection connection = connect(); 
                String sql="select * from BOOKS"; 
                try {
                    Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE LIBRARY"); 
                    stmt=connection.createStatement();
                    ResultSet rs=stmt.executeQuery(sql);
                    JTable book_list= new JTable(); 
                    book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                    //mention scroll bar
                    JScrollPane scrollPane = new JScrollPane(book_list); 
     
                    f.add(scrollPane);
                    f.setSize(800, 400); 
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }               
                 
        }
        }
        );
         
        JButton users_but=new JButton("View Users");//creating instance of JButton to view users
        users_but.setBounds(180,20,120,25);
        users_but.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e){
                     
                    JFrame f = new JFrame("Users List");
                   
                     
                    Connection connection = connect();
                    String sql="select * from users"; 
                    try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY"); 
                        stmt=connection.createStatement();
                        ResultSet rs=stmt.executeQuery(sql);
                        JTable book_list= new JTable();
                        book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                        
                        JScrollPane scrollPane = new JScrollPane(book_list);
     
                        f.add(scrollPane); 
                        f.setSize(800, 400); 
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }       
                     
                     
        }
            }
        );  
         
        JButton issued_but=new JButton("View Issued Books");//creating instance of JButton to view the issued books
        issued_but.setBounds(50,60,160,25);
        issued_but.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    JFrame f = new JFrame("Users List");
       
                    Connection connection = connect();
                    String sql="select * from issued";
                    try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY");
                        stmt=connection.createStatement();
                        ResultSet rs=stmt.executeQuery(sql);
                        JTable book_list= new JTable();
                        book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                         
                        JScrollPane scrollPane = new JScrollPane(book_list);
     
                        f.add(scrollPane);
                        f.setSize(800, 400);
                        f.setVisible(true);
                        f.setLocationRelativeTo(null);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }       
                                 
        }
            }
        );
         
         
        JButton add_user=new JButton("Add User"); //creating instance of JButton to add users
        add_user.setBounds(50,180,120,25); 
        add_user.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    JFrame g = new JFrame("Enter User Details"); //Frame to enter user details
                  
                    JLabel l1,l2;  
                    l1=new JLabel("Username");  
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("Password"); 
                    l2.setBounds(30,50, 100,30); 
                     
                    
                    JTextField F_user = new JTextField();
                    F_user.setBounds(110, 15, 200, 30);
                     
                  
                    JPasswordField F_pass=new JPasswordField();
                    F_pass.setBounds(110, 50, 200, 30);
                  
                    JRadioButton a1 = new JRadioButton("Admin");
                    a1.setBounds(55, 80, 200,30);
                 
                    JRadioButton a2 = new JRadioButton("User");
                    a2.setBounds(130, 80, 200,30);
                
                    ButtonGroup bg=new ButtonGroup();    
                    bg.add(a1);bg.add(a2);  
                     
                                     
                    JButton create_but=new JButton("Create");//creating instance of JButton for Create 
                    create_but.setBounds(130,130,80,25); 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                         
                        String username = F_user.getText();
                        String password = F_pass.getText();
                        Boolean admin = false;
                         
                        if(a1.isSelected()) {
                            admin=true;
                        }
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY");
                         stmt.executeUpdate("INSERT INTO USERS(USERNAME,PASSWORD,ADMIN) VALUES ('"+username+"','"+password+"',"+admin+")");
                         JOptionPane.showMessageDialog(null,"User added!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                         
                     
                        g.add(create_but);
                        g.add(a2);
                        g.add(a1);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_user);
                        g.add(F_pass);
                        g.setSize(350,200);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                     
                     
        }
        });
             
         
        JButton add_book=new JButton("Add Book"); //creating instance of JButton for adding books
        add_book.setBounds(180,100,120,25); 
         
        add_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    //set frame wot enter book details
                    JFrame g = new JFrame("Enter Book Details");
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // set labels
                    JLabel l1,l2,l3;  
                    l1=new JLabel("Book Name");  //lebel 1 for book name
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("Genre");  //label 2 for genre
                    l2.setBounds(30,53, 100,30); 
                     
                    l3=new JLabel("Price");  //label 2 for price
                    l3.setBounds(30,90, 100,30); 
                     
                    //set text field for book name
                    JTextField F_bname = new JTextField();
                    F_bname.setBounds(110, 15, 200, 30);
                     
                    //set text field for genre 
                    JTextField F_genre=new JTextField();
                    F_genre.setBounds(110, 53, 200, 30);
                    //set text field for price
                    JTextField F_price=new JTextField();
                    F_price.setBounds(110, 90, 200, 30);
                             
                     
                    JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
                    create_but.setBounds(130,130,80,25);
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                       
                        String bname = F_bname.getText();
                        String genre = F_genre.getText();
                        String price = F_price.getText();
                        
                        int price_int = Integer.parseInt(price);
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY");
                         stmt.executeUpdate("INSERT INTO BOOKS(BNAME,GENRE,PRICE) VALUES ('"+bname+"','"+genre+"',"+price_int+")");
                         JOptionPane.showMessageDialog(null,"Book added!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                                     
                        g.add(l3);
                        g.add(create_but);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_bname);
                        g.add(F_genre);
                        g.add(F_price);
                        g.setSize(350,200);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                                 
        }
        });
         
         
        JButton issue_book=new JButton("Issue Book"); //creating instance of JButton to issue books
        issue_book.setBounds(50,100,120,25); 
         
        issue_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    //enter details
                    JFrame g = new JFrame("Enter Details");
                    //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //create labels
                    JLabel l1,l2,l3,l4;  
                    l1=new JLabel("Book ID(BID)");  // Label 1 for Book ID
                    l1.setBounds(30,15, 100,30); 
                     
                     
                    l2=new JLabel("User ID(UID)");  //Label 2 for user ID
                    l2.setBounds(30,53, 100,30); 
                     
                    l3=new JLabel("Period(days)");  //Label 3 for period
                    l3.setBounds(30,90, 100,30); 
                     
                    l4=new JLabel("Issued Date(DD-MM-YYYY)");  //Label 4 for issue date
                    l4.setBounds(30,127, 150,30); 
                     
                    JTextField F_bid = new JTextField();
                    F_bid.setBounds(110, 15, 200, 30);
                     
                     
                    JTextField F_uid=new JTextField();
                    F_uid.setBounds(110, 53, 200, 30);
                     
                    JTextField F_period=new JTextField();
                    F_period.setBounds(110, 90, 200, 30);
                     
                    JTextField F_issue=new JTextField();
                    F_issue.setBounds(180, 130, 130, 30);   
     
                     
                    JButton create_but=new JButton("Submit");//creating instance of JButton  
                    create_but.setBounds(130,170,80,25);//x axis, y axis, width, height 
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){
                         
                        String uid = F_uid.getText();
                        String bid = F_bid.getText();
                        String period = F_period.getText();
                        String issued_date = F_issue.getText();
     
                        int period_int = Integer.parseInt(period);
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY");
                         stmt.executeUpdate("INSERT INTO ISSUED(UID,BID,ISSUED_DATE,PERIOD) VALUES ('"+uid+"','"+bid+"','"+issued_date+"',"+period_int+")");
                         JOptionPane.showMessageDialog(null,"Book Issued!");
                         g.dispose();
                          
                        }
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    });
                         
                     
                        g.add(l3);
                        g.add(l4);
                        g.add(create_but);
                        g.add(l1);
                        g.add(l2);
                        g.add(F_uid);
                        g.add(F_bid);
                        g.add(F_period);
                        g.add(F_issue);
                        g.setSize(350,250);//400 width and 500 height  
                        g.setLayout(null);//using no layout managers  
                        g.setVisible(true);//making the frame visible 
                        g.setLocationRelativeTo(null);
                     
                     
        }
        });
         
         
        JButton return_book=new JButton("Return Book"); //creating instance of JButton to return books
        return_book.setBounds(50,140,160,25); 
         
        return_book.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                     
                    JFrame g = new JFrame("Enter Details");
                    
                    //set labels 
                    JLabel l1,l2,l3,l4;  
                    l1=new JLabel("Issue ID(IID)");  //Label 1 for Issue ID
                    l1.setBounds(30,15, 100,30); 
                    
                     
                    l4=new JLabel("Return Date(DD-MM-YYYY)");  
                    l4.setBounds(30,50, 150,30); 
                     
                    JTextField F_iid = new JTextField();
                    F_iid.setBounds(110, 15, 200, 30);
                     
                     
                    JTextField F_return=new JTextField();
                    F_return.setBounds(180, 50, 130, 30);
                 
     
                    JButton create_but=new JButton("Return");//creating instance of JButton to mention return date and calculcate fine
                    create_but.setBounds(130,170,80,25);
                    create_but.addActionListener(new ActionListener() {
                         
                        public void actionPerformed(ActionEvent e){                 
                         
                        String iid = F_iid.getText();
                        String return_date = F_return.getText();
                         
                        Connection connection = connect();
                         
                        try {
                        Statement stmt = connection.createStatement();
                         stmt.executeUpdate("USE LIBRARY");
                         
                         String date1=null;
                         String date2=return_date; 
                         
                         //select issue date
                         ResultSet rs = stmt.executeQuery("SELECT ISSUED_DATE FROM ISSUED WHERE IID="+iid);
                         while (rs.next()) {
                             date1 = rs.getString(1);
                              
                           }
                          
                         try {
                                Date date_1=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
                                Date date_2=new SimpleDateFormat("dd-MM-yyyy").parse(date2);
                                //subtract the dates and store in diff
                                long diff = date_2.getTime() - date_1.getTime();
                                //Convert diff from milliseconds to days
                                ex.days=(int)(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                 
                                 
                            } catch (ParseException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                          
                         
                         //update return date
                         stmt.executeUpdate("UPDATE ISSUED SET RETURN_DATE='"+return_date+"' WHERE IID="+iid);
                         g.dispose();
                          
     
                         Connection connection1 = connect();
                         Statement stmt1 = connection1.createStatement();
                         stmt1.executeUpdate("USE LIBRARY");                
                        ResultSet rs1 = stmt1.executeQuery("SELECT PERIOD FROM ISSUED WHERE IID="+iid); //set period
                        String diff=null; 
                        while (rs1.next()) {
                             diff = rs1.getString(1);
                              
                           }
                        int diff_int = Integer.parseInt(diff);
                        if(ex.days < diff_int) { //If number of days are more than the period then calculcate fine
                             
                            //System.out.println(ex.days);
                            int fine = (ex.days-diff_int)*10; //fine for every day after the period is Rs 10.
                            //update fine in the system
                            stmt1.executeUpdate("UPDATE ISSUED SET FINE="+fine+" WHERE IID="+iid);  
                            String fine_str = ("Fine: Rs. "+fine);
                            JOptionPane.showMessageDialog(null,fine_str);
                             
                        }
     
                         JOptionPane.showMessageDialog(null,"Book Returned!");
                          
                        }
                                 
                         
                        catch (SQLException e1) {
                            // TODO Auto-generated catch block
                             JOptionPane.showMessageDialog(null, e1);
                        }   
                         
                        }
                         
                    }); 
                        g.add(l4);
                        g.add(create_but);
                        g.add(l1);
                        g.add(F_iid);
                        g.add(F_return);
                        g.setSize(350,250);
                        g.setLayout(null);
                        g.setVisible(true);
                        g.setLocationRelativeTo(null);              
        }
        });
        JButton log_out=new JButton("Log out"); //creating instance of JButton to Log Out
        log_out.setBounds(500,300,120,25); 
        
        log_out.addActionListener(new  ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				login_Form();
				f.setVisible(false);
			}
		});
        JLabel backround=new JLabel(new ImageIcon("C:\\Users\\shri\\eclipse-workspace\\Library\\src\\back.jpg"));
		f.setContentPane(backround);
        f.add(create_but);
        f.add(return_book);
        f.add(issue_book);
        f.add(add_book);
        f.add(issued_but);
        f.add(users_but);
        f.add(view_but);
        f.add(add_user);
        f.add(log_out);
        f.setSize(700,400);
        f.setResizable(false);
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible 
        f.setLocationRelativeTo(null);
         
        }

}
