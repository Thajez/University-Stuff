import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame implements ActionListener {
    JButton showAllButton;
    JButton editTaskButton;
    private final Object pause = new Object();

    MainMenu(){

        showAllButton = new JButton("Show All");
        showAllButton.setBounds(325,640,150,60);
        showAllButton.addActionListener(this);
        editTaskButton = new JButton("Edit Tasks");
        editTaskButton.setBounds(525,640,150,60);
        editTaskButton.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // paspaudus kryziuka
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.writeJSON(); //Issaugo isejus
                Main.scanner.close();
                System.exit(0); // iseina
            }
        });
        this.setTitle("To Do List");
        this.setResizable(false);
        this.setName("To Do List");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(173, 95, 10));
        this.setLayout(null);
        this.setSize(1000,750);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(showAllButton);
        this.add(editTaskButton);
    }

    @Override
    public void actionPerformed (ActionEvent e){
        synchronized (pause){
            if(e.getSource()==showAllButton){
                this.setVisible(false);
                new ShowAll(() -> this.setVisible(true));
            }
            else if(e.getSource()==editTaskButton){
                this.setVisible(false);
                new EditTasks(()->this.setVisible(true));
            }
            pause.notify();
        }
    }

    public void waitForPress(){
        synchronized (pause){
            try{
                pause.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
