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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    private JLabel heading=new JLabel("Client");
    private JTextArea messageArea= new JTextArea();
    private JTextField messageInput =new JTextField();
    private Font font=new Font("Robot",Font.PLAIN,20);

    /**
     * 
     */
    public Client(){
        try{ 
       System.out.println("Sending request to server");
       socket=new Socket("127.0.0.1",9992);
       System.out.println("connection is established");

       br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
       out=new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
           startReading();
         startWriting();
          
        }
         catch (Exception e) {
           e.printStackTrace(); 
        }
 
    }

    /**
     */
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
            messageArea.append("Me:"+contentToSend+"\n");
            out.println(contentToSend);
            out.flush();
            messageInput.setText("");
            messageInput.requestFocus();

          }
          
        }

      });
    }

    private void createGUI(){

      this.setTitle("Client Messager[END]");
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
            while(true){
             
                String msg=br.readLine();

                if(msg.equals("exit")){
                  System.out.println("Server terminated the chat...");
                  JOptionPane.showMessageDialog(this,"Server terminated Chat..");
                  messageInput.setEnabled(false);
                  socket.close();
                  break;
                }

                // System.out.println("Server: "+msg);

                messageArea.append("Server: " + msg+"\n");
            
            
        }
    }

    catch (Exception e) {
            System.out.println("connection is closed");
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
           System.out.println("conmection is closed");
        }
        };
       new Thread(r2).start();
      }
    public static void main(String[] args) {
        System.out.println("this is client");
        new Client();
    }
}
