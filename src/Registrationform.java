import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registrationform extends JDialog{
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JTextField tfAddress;
    private JButton btnregister;
    private JPanel RegisterPanel;
    private JButton btncancel;
    private JButton btnInfo;
    private JTextArea taInfo;

    public Registrationform(JFrame parent)
    {
        super(parent);
        setTitle("WELCOME");
        setContentPane(RegisterPanel);
        setMinimumSize(new Dimension(600,500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnregister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btncancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "subham97");
                    Statement st = conn.createStatement();


                    ResultSet res = st.executeQuery("SELECT * FROM new_table");
                    String s = new String();
                    while (res.next()) {
                        String n = res.getString("name");
                        String em = res.getString("email");
                        String p = res.getString("phone");
                        String a = res.getString("address");


                        s += "  Name: "+n + "   E-mail: " + em + "   Phone No: " + p + "   Address: " + a + "\n";

                        System.out.print(s);
                    }
                    taInfo.setText(s);

                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
            setVisible(true);
    }

    private void registerUser()
    {
      String name=tfName.getText();
      String email=tfEmail.getText();
      String phone=tfPhone.getText();
      String address=tfAddress.getText();

      if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||address.isEmpty())
      {
          JOptionPane.showMessageDialog(this,"Please Enter all the fields","Try again",JOptionPane.ERROR_MESSAGE);
          return;
      }
      user=addUserToDatabase(name,email,phone,address);
      if(user !=null)
      {
          dispose();
      }
      else
      {
          JOptionPane.showMessageDialog(this,"Already Registered", "Try something else",JOptionPane.ERROR_MESSAGE);
      }
    }
    public User user;
    private User addUserToDatabase(String name,String email,String phone,String address)
    {
        User user=null;
        try
        {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","subham97");
            Statement statement=connection.createStatement();
            String sql="INSERT INTO new_table(name,email,phone,address)"+"VALUES(?,?,?,?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,phone);
            preparedStatement.setString(4,address);

            ResultSet resultSet=statement.executeQuery("SELECT * FROM new_table ORDER BY name ASC");
            System.out.println("List of students already in the register ");
            System.out.println();
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            System.out.printf("%20s %30s %20s %30s", "NAME", "E-MAIL", "PHONE", "ADDRESS");
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            while(resultSet.next())
            {
                String n=resultSet.getString("name");
                String e=resultSet.getString("email");
                String p=resultSet.getString("phone");
                String a=resultSet.getString("address");
                String s=String.format("%20s%30s%20s%30s",n,e,p,a);
                System.out.println(s);

            }

            int addrows =preparedStatement.executeUpdate();
            if(addrows>0)
            {
                user=new User();
                user.name=name;
                user.email=email;
                user.phone=phone;
                user.address=address;
            }
            statement.close();
            connection.close();


        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String args[])
    {
        Registrationform myform=new Registrationform(null);
        User user=myform.user;
        if(user!=null) {
            System.out.println("Successful Registration of: " + user.name);
            System.out.println("Name: "+user.name+" Mail ID: "+user.email+" Phone number: "+user.phone+" Address: "+user.address);

        }
        else {
            System.out.println("Registration Cancelled");
        }
    }
}
