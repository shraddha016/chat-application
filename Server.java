import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import java.io.*;
public class Server extends JFrame{
   
    ServerSocket servers;
      Socket socket;
      BufferedReader br;
      PrintWriter out;

      private JLabel heading=new JLabel("Server");
    private JTextArea messageArea= new JTextArea();
    private JTextField messageInput =new JTextField();
    private Font font=new Font("Robot",Font.PLAIN,20);

      

      public Server(){
        try {
           servers=new ServerSocket(9992);
            System.out.println("server is ready for the connection...");
            System.out.println("server is waiting........");
            socket=servers.accept(); 

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream()) ;

            createGUI();
            handleEvents();

            startReading();
            startWriting();
            
        }
       
        catch (Exception e) {
          /* e.printStackTrace(); */
        }
        
      }

      private void handleEvents(){
        messageInput.addKeyListener(new KeyListener(){
  
          @Override
          public void keyTyped(KeyEvent e) {
            
          }
  
          @Override
          public void keyPressed(KeyEvent e) {
          
          }
  
          @Override
          public void keyReleased(KeyEvent e) {
            
  
            if(e.getKeyCode()==10){
  
              String contentToSend = messageInput.getText();
              messageArea.append("server:"+contentToSend+"\n");
              out.println(contentToSend);
              out.flush();
              messageInput.setText("");
              messageInput.requestFocus();
  
            }
            
          }

        });
      }
  
      private void createGUI(){
  
        this.setTitle("Server Messager[END]");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
  
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);
  
        //image icon resizing---------------------------------------------------------------------
        ImageIcon myimage = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("chatss.png")));
        Image img1= myimage.getImage();
        Image img2=img1.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon i=new ImageIcon(img2);
  
        heading.setIcon(i);
  
  
        //---------------------------------------------------------------------------
  
       heading.setHorizontalTextPosition(SwingConstants.CENTER);
       heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setLayout(new BorderLayout());
  
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(messageArea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
  
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
      }

      public void startReading(){
       
        Runnable r1=()->{

            System.out.println("reader started...");
         try{
            while(!socket.isClosed()){
              
                String msg=br.readLine();

                if(msg.equals("exit")){
                  System.out.println("Server terminated the chat...");
                  JOptionPane.showMessageDialog(this,"Server terminated Chat..");
                  messageInput.setEnabled(false);
                  socket.close();
                  break;
                }

                System.out.println("Client: "+msg);
            }
        }
            
        catch (Exception e) {
           System.out.println("connection is close");
        }
        

        };

         new Thread(r1).start();

      }

      

      public void startWriting(){
      
        Runnable r2=()->{
        System.out.println("writer started....");
         try {
       while(!socket.isClosed()){

       
            BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
            String content=br1.readLine();
            out.println(content);
            out.flush();

             if(content.equals("exit")){
                 
                  socket.close();
                  break;
                }

        
       }
    }
       catch (Exception e) {
           System.out.println("connection is close");
        }
        
        };
       new Thread(r2).start();
      }
    public static void main(String[] args) {
        System.out.println("this is server going to start.....");
       SwingUtilities.invokeLater(() -> new Server());
    }
}