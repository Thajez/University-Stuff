import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Queue;

public class SelectedTask extends JFrame implements ActionListener {
    static long currentSid;
    static long currentId;
    static String currentName;
    static String currentSubject;
    static LocalDate currentTerm;
    static String currentDescription;
    JButton nameButton;
    JButton subjectButton;
    JButton termButton;
    JButton descriptionButton;
    JButton removeButton;
    JLabel sidLabel;
    JLabel idLabel;
    JLabel nameLabel;
    JLabel subjectLabel;
    JLabel termLabel;
    JLabel descriptionLabel;

    SelectedTask(){

        selection();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // paspaudus kryziuka
        this.setTitle("Selected Task");
        this.setResizable(false);
        this.setName("Selected Task");
        ImageIcon image = new ImageIcon("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\icon.png");
        this.setIconImage(image.getImage());
        this.setLayout(new BorderLayout());
        this.setSize(800,600);
        this.setLocationRelativeTo(null);

        JPanel tasksPanel = new JPanel();
        tasksPanel.setBackground(new Color(173, 95, 10));
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.X_AXIS));

        JPanel taskNode = new JPanel();
        taskNode.setLayout(new BoxLayout(taskNode, BoxLayout.Y_AXIS));
        taskNode.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        taskNode.setBackground(new Color(130, 75, 7));
        taskNode.setPreferredSize(new Dimension(100,100));

        sidLabel = new JLabel("<html><font size= '6'>Search ID: " + currentSid + "</font></html>");
        sidLabel.setForeground(Color.WHITE);
        idLabel = new JLabel("<html><font size= '6'>ID: " + currentId + "</font></html>");
        idLabel.setForeground(Color.WHITE);
        nameLabel = new JLabel("<html><font size= '6'>Name: " + currentName + "</font></html>");
        nameLabel.setForeground(Color.WHITE);
        subjectLabel = new JLabel("<html><font size= '6'>Subject: " + currentSubject + "</font></html>");
        subjectLabel.setForeground(Color.WHITE);
        termLabel = new JLabel("<html><font size= '6'>Term: " + currentTerm + "</font></html>");
        termLabel.setForeground(Color.WHITE);
        descriptionLabel = new JLabel("<html><font size= '6'>Description: " + currentDescription + "</font></html>");
        descriptionLabel.setForeground(Color.WHITE);

        taskNode.add(sidLabel);
        taskNode.add(idLabel);
        taskNode.add(nameLabel);
        taskNode.add(subjectLabel);
        taskNode.add(termLabel);
        taskNode.add(descriptionLabel);

        tasksPanel.add(Box.createHorizontalStrut(10));
        tasksPanel.add(taskNode);
        this.add(tasksPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(173, 95, 10));
        nameButton = new JButton("Change Name");
        nameButton.setPreferredSize(new Dimension(125,40));
        nameButton.addActionListener(this);
        subjectButton = new JButton("Change Subject");
        subjectButton.setPreferredSize(new Dimension(125,40));
        subjectButton.addActionListener(this);
        termButton = new JButton("Change Term");
        termButton.setPreferredSize(new Dimension(125,40));
        termButton.addActionListener(this);
        descriptionButton = new JButton("Change Description");
        descriptionButton.setPreferredSize(new Dimension(140,40));
        descriptionButton.addActionListener(this);
        removeButton = new JButton("Remove Task");
        removeButton.setPreferredSize(new Dimension(125,40));
        removeButton.addActionListener(this);

        buttonPanel.add(nameButton);
        buttonPanel.add(subjectButton);
        buttonPanel.add(termButton);
        buttonPanel.add(descriptionButton);
        buttonPanel.add(removeButton);
        this.add(buttonPanel,BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                done();
            }
        });

        this.setVisible(true);
    }

    public void selection (){
        Queue<Long> tempSId = new LinkedList<>(Main.sid);
        Queue<Long> tempId = new LinkedList<>(Main.id);
        Queue<String> tempName = new LinkedList<>(Main.name);
        Queue<String> tempSubject = new LinkedList<>(Main.subject);
        Queue<LocalDate> tempTerm = new LinkedList<>(Main.term);
        Queue<String> tempDescription = new LinkedList<>(Main.description);

        for(int i = 0; i < Main.sAmount; i++){
            currentSid = tempSId.poll();
            currentId = tempId.poll();
            currentName = tempName.poll();
            currentSubject = tempSubject.poll();
            currentTerm = tempTerm.poll();
            currentDescription = tempDescription.poll();

            if (ShowFound.selectID == currentSid){
                return;
            }
        }
    }

    public void done(){
        Queue<Long> SId = new LinkedList<>(Main.sid);
        Queue<Long> tempSId = new LinkedList<>();
        Queue<Long> tempId = new LinkedList<>();
        Queue<String> tempName = new LinkedList<>();
        Queue<String> tempSubject = new LinkedList<>();
        Queue<LocalDate> tempTerm = new LinkedList<>();
        Queue<String> tempDescription = new LinkedList<>();
        for(int i = 0; i < Main.sAmount; i++){
            long sid = SId.poll();
            if(sid == currentSid){
                tempSId.offer(sid);
                tempId.offer(currentId);
                tempName.offer(currentName);
                tempSubject.offer(currentSubject);
                tempTerm.offer(currentTerm);
                tempDescription.offer(currentDescription);
            }
        }
        Main.sid = tempSId;
        Main.id = tempId;
        Main.name = tempName;
        Main.subject = tempSubject;
        Main.term = tempTerm;
        Main.description = tempDescription;
        save();
    }

    public void remove(){
        Queue<Long> tempSId = new LinkedList<>();
        Queue<Long> tempId = new LinkedList<>();
        Queue<String> tempName = new LinkedList<>();
        Queue<String> tempSubject = new LinkedList<>();
        Queue<LocalDate> tempTerm = new LinkedList<>();
        Queue<String> tempDescription = new LinkedList<>();
        for(int i = 0; i < Main.sAmount; i++){
            long sid = Main.sid.poll();
            if(sid != currentSid){
                tempSId.offer(sid);
                tempId.offer(Main.id.poll());
                tempName.offer(Main.name.poll());
                tempSubject.offer(Main.subject.poll());
                tempTerm.offer(Main.term.poll());
                tempDescription.offer(Main.description.poll());
            } else {
                Main.id.poll();
                Main.name.poll();
                Main.subject.poll();
                Main.term.poll();
                Main.description.poll();
            }
        }
        Main.sid = tempSId;
        Main.id = tempId;
        Main.name = tempName;
        Main.subject = tempSubject;
        Main.term = tempTerm;
        Main.description = tempDescription;
        Main.sAmount--;
        save();
    }

    public void save(){
        LinkedList<Long> tempId = new LinkedList<>();
        LinkedList<String> tempName = new LinkedList<>();
        LinkedList<String> tempSubject = new LinkedList<>();
        LinkedList<LocalDate> tempTerm = new LinkedList<>();
        LinkedList<String> tempDescription = new LinkedList<>();
        long id = Main.id.poll();
        for(int i = 0; i < Main.amount; i++){
            if(id == Main.Id.get(i)){
                tempId.offer(id);
                tempName.offer(Main.name.poll());
                tempSubject.offer(Main.subject.poll());
                tempTerm.offer(Main.term.poll());
                tempDescription.offer(Main.description.poll());
            } else{
                tempId.offer(Main.Id.get(i));
                tempName.offer(Main.Name.get(i));
                tempSubject.offer(Main.Subject.get(i));
                tempTerm.offer(Main.Term.get(i));
                tempDescription.offer(Main.Description.get(i));
            }
        }
        Main.Id = tempId;
        Main.Name = tempName;
        Main.Subject = tempSubject;
        Main.Term = tempTerm;
        Main.Description = tempDescription;
        Main.writeJSON();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nameButton){
            String name = "";
            while(name.length() < 4){
                name = JOptionPane.showInputDialog("New Name:");
                if (name == null) {
                    JOptionPane.showMessageDialog(null, "Name change cancelled!","Cancelled", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (name.length() < 4){
                    JOptionPane.showMessageDialog(null,"Name has to contain atleast 4 letters!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            currentName = name;
            nameLabel.setText("<html><font size= '6'>Name: " + currentName + "</font></html>");
        } else if (e.getSource()==subjectButton){
            String subject = "";
            while(subject.length() < 4){
                subject = JOptionPane.showInputDialog("New Subject:");
                if (subject == null) {
                    JOptionPane.showMessageDialog(null, "Subject change cancelled!","Cancelled", JOptionPane.WARNING_MESSAGE);
                    return;
                } else if (subject.length() < 4){
                    JOptionPane.showMessageDialog(null,"Subject has to contain atleast 4 letters!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            currentSubject = subject;
            subjectLabel.setText("<html><font size= '6'>Subject: " + currentSubject + "</font></html>");
        } else if (e.getSource()==termButton){
            LocalDate term;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            while (true) {
                String date = JOptionPane.showInputDialog("New Term (ex. 2024/11/28):");
                if (date == null) {
                    JOptionPane.showMessageDialog(null, "Term change cancelled!", "Cancelled", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try {
                    term = LocalDate.parse(date, formatter);

                    if (term.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(null,"The term date cannot be in the past. Please enter a future date.","Error",JOptionPane.ERROR_MESSAGE);
                    } else {
                        break;
                    }
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null,"Invalid date format. Please use yyyy/MM/dd.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            currentTerm = term;
            termLabel.setText("<html><font size= '6'>Term: " + currentTerm + "</font></html>");
        } else if (e.getSource()==descriptionButton){
            String description = "";
            while(description.length() < 10){
                description = JOptionPane.showInputDialog("New Description:");
                if(description == null){
                    JOptionPane.showMessageDialog(null, "Description change cancelled!","Cancelled", JOptionPane.WARNING_MESSAGE);
                    return;
                }else if (description.length() < 10){
                    JOptionPane.showMessageDialog(null,"Description has to contain atleast 10 letters!","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            currentDescription = description;
            descriptionLabel.setText("<html><font size= '6'>Description: " + currentDescription + "</font></html>");
        } else if (e.getSource()==removeButton){
            remove();
            JOptionPane.showMessageDialog(this, "Task removed successfully!");
            this.dispose();
        }
    }
}
