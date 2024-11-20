import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EditTasks extends JFrame implements ActionListener{
    JButton addButton;
    JButton searchButton;
    JButton sortButton;

    EditTasks(Runnable onCloseCallback){
        addButton = new JButton("Add Task");
        addButton.setBounds(20,30,150,60);
        addButton.addActionListener(this);
        searchButton = new JButton("Search tasks");
        searchButton.setBounds(220,30,150,60);
        searchButton.addActionListener(this);
        sortButton = new JButton("Sort by Term");
        sortButton.setBounds(415,30,150,60);
        sortButton.addActionListener(this);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // paspaudus kryziuka
        this.setTitle("Edit");
        this.setResizable(false);
        this.setName("Edit");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(173, 95, 10));
        this.setLayout(null);
        this.setSize(600,150);
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(onCloseCallback != null){
                    onCloseCallback.run();
                }
            }
        });
        this.add(addButton);
        this.add(searchButton);
        this.add(sortButton);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed (ActionEvent e){
        if(e.getSource()==addButton){
            this.setVisible(false);
            Main.addTask();
            this.setVisible(true);
        }else if (e.getSource()==searchButton){
            this.setVisible(false);
            new SearchTasks(()->this.setVisible(true));
        }
        else if (e.getSource()==sortButton){
            this.setVisible(false);
            QuickSort.sortByTerm();
            this.setVisible(true);
        }
    }
}
