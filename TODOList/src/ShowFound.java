import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

public class ShowFound extends JFrame implements ActionListener{
    JButton selectButton;
    static long selectID = 0;
    static JLabel sidLabel;
    static JLabel idLabel;
    static JLabel nameLabel;
    static JLabel subjectLabel;
    static JLabel termLabel;
    static JLabel descriptionLabel;
    ShowFound(Runnable onCloseCallback){
        Queue<Long> tempSId = new LinkedList<>(Main.sid);
        Queue<Long> tempId = new LinkedList<>(Main.id);
        Queue<String> tempName = new LinkedList<>(Main.name);
        Queue<String> tempSubject = new LinkedList<>(Main.subject);
        Queue<LocalDate> tempTerm = new LinkedList<>(Main.term);
        Queue<String> tempDescription = new LinkedList<>(Main.description);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Found tasks");
        this.setResizable(false);
        this.setName("Found Tasks");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setBackground(new Color(173, 95, 10));
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.X_AXIS));

        for(int i = 0; i < Main.sAmount; i++){
            JPanel taskNode = new JPanel();
            taskNode.setLayout(new BoxLayout(taskNode, BoxLayout.Y_AXIS));
            taskNode.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
            taskNode.setBackground(new Color(130, 75, 7));
            taskNode.setPreferredSize(new Dimension(100,100));

            sidLabel = new JLabel("<html><font size= '6'>Search ID: " + tempSId.poll() + "</font></html>");
            sidLabel.setForeground(Color.WHITE);
            idLabel = new JLabel("<html><font size= '6'>ID: " + tempId.poll() + "</font></html>");
            idLabel.setForeground(Color.WHITE);
            nameLabel = new JLabel("<html><font size= '6'>Name: " + tempName.poll() + "</font></html>");
            nameLabel.setForeground(Color.WHITE);
            subjectLabel = new JLabel("<html><font size= '6'>Subject: " + tempSubject.poll() + "</font></html>");
            subjectLabel.setForeground(Color.WHITE);
            termLabel = new JLabel("<html><font size= '6'>Term: " + tempTerm.poll() + "</font></html>");
            termLabel.setForeground(Color.WHITE);
            descriptionLabel = new JLabel("<html><font size= '6'>Description: " + tempDescription.poll() + "</font></html>");
            descriptionLabel.setForeground(Color.WHITE);

            taskNode.add(sidLabel);
            taskNode.add(idLabel);
            taskNode.add(nameLabel);
            taskNode.add(subjectLabel);
            taskNode.add(termLabel);
            taskNode.add(descriptionLabel);

            tasksPanel.add(Box.createHorizontalStrut(10));
            tasksPanel.add(taskNode);
        }
        JScrollPane scrollPane = new JScrollPane(tasksPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 95, 10));
        selectButton = new JButton("Select Task");
        selectButton.setPreferredSize(new Dimension(150,40));
        selectButton.addActionListener(this);

        buttonPanel.add(selectButton);
        this.add(buttonPanel,BorderLayout.SOUTH);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==selectButton){
            while(selectID == 0){
                String input = JOptionPane.showInputDialog("Enter search ID of the task you wish to select! (1 - "+ Main.sAmount+"):");
                if(input == null){
                    JOptionPane.showMessageDialog(null,"Task selection cancelled!","Cancelled!",JOptionPane.WARNING_MESSAGE);
                    break;
                }
                try{
                    selectID = Long.parseLong(input);
                } catch (NumberFormatException ex) {
                    selectID = 0;
                    JOptionPane.showMessageDialog(null,"You must enter a number!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            if(selectID != 0){
                new SelectedTask();
            } else {
                Main.sAmount = 0;
                Main.sid.clear();
                Main.id.clear();
                Main.name.clear();
                Main.subject.clear();
                Main.term.clear();
                Main.description.clear();
            }
        }
    }
}
