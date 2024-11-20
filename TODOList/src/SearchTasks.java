import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SearchTasks extends JFrame implements ActionListener {
    JButton nameButton;
    JButton subjectButton;

    SearchTasks(Runnable onCloseCallback){
        nameButton = new JButton("Search by Name");
        nameButton.setBounds(20,30,150,60);
        nameButton.addActionListener(this);
        subjectButton = new JButton("Search by Subject");
        subjectButton.setBounds(215,30,150,60);
        subjectButton.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // paspaudus kryziuka
        this.setTitle("Edit");
        this.setResizable(false);
        this.setName("Edit");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(173, 95, 10));
        this.setLayout(null);
        this.setSize(400,150);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(onCloseCallback != null){
                    onCloseCallback.run();
                }
            }
        });
        this.add(nameButton);
        this.add(subjectButton);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nameButton){
            this.setVisible(false);
            Main.search(1);
            if(Main.found){
                new ShowFound(()->this.setVisible(true));
            } else this.setVisible(true);
        }
        else if(e.getSource()==subjectButton){
            this.setVisible(false);
            Main.search(0);
            if(Main.found){
                new ShowFound(()->this.setVisible(true));
            } else this.setVisible(true);
        }
    }
}
