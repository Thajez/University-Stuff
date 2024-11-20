import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShowAll extends JFrame{
    ShowAll(Runnable onCloseCallback){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // paspaudus kryziuka
        this.setTitle("All Tasks");
        this.setResizable(false);
        this.setName("All Tasks");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setBackground(new Color(173, 95, 10));
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.X_AXIS));

        for(int i = 0; i < Main.amount; i++){
            JPanel taskNode = new JPanel();
            taskNode.setLayout(new BoxLayout(taskNode, BoxLayout.Y_AXIS));
            taskNode.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            taskNode.setBackground(new Color(130, 75, 7));
            taskNode.setPreferredSize(new Dimension(300,300));

            JLabel idLabel = new JLabel("<html><font size= '6'>ID: " +Main.Id.get(i) + "</font></html>");
            idLabel.setForeground(Color.WHITE);
            JLabel nameLabel = new JLabel("<html><font size= '6'>Name: " +Main.Name.get(i) + "</font></html>");
            nameLabel.setForeground(Color.WHITE);
            JLabel subjectLabel = new JLabel("<html><font size= '6'>Subject: " +Main.Subject.get(i) + "</font></html>");
            subjectLabel.setForeground(Color.WHITE);
            JLabel termLabel = new JLabel("<html><font size= '6'>Term: " +Main.Term.get(i) + "</font></html>");
            termLabel.setForeground(Color.WHITE);
            JLabel descriptionLabel = new JLabel("<html><font size= '6'>Description: " +Main.Description.get(i) + "</font></html>");
            descriptionLabel.setForeground(Color.WHITE);

            taskNode.add(idLabel);
            taskNode.add(nameLabel);
            taskNode.add(subjectLabel);
            taskNode.add(termLabel);
            taskNode.add(descriptionLabel);

            tasksPanel.add(Box.createHorizontalStrut(10));
            tasksPanel.add(taskNode);
        }

        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(onCloseCallback != null){
                    onCloseCallback.run();
                }
            }
        });

        this.setVisible(true);
    }
}
