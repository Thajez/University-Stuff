import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    // Visur naudojami kintamieji
    static LinkedList<Long> Id = new LinkedList<>();
    static LinkedList<String> Name = new LinkedList<>();
    static LinkedList<String> Subject = new LinkedList<>();
    static LinkedList<LocalDate> Term = new LinkedList<>();
    static LinkedList<String> Description = new LinkedList<>();
    static Queue<Long> sid = new LinkedList<>();
    static Queue<Long> id = new LinkedList<>();
    static Queue<String> name = new LinkedList<>();
    static Queue<String> subject = new LinkedList<>();
    static Queue<LocalDate> term = new LinkedList<>();
    static Queue<String> description = new LinkedList<>();
    static long amount = 0;
    static long sAmount = 0;
    static long nextId = 1;
    static Scanner scanner = new Scanner(System.in);
    static MainMenu menu = new MainMenu();
    static boolean found = false;

    public static void main(String[] args) {
        readJSON();
        while(true) menu.waitForPress();
    }

    public static LocalDate inputValidDate() {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        while (true) {
            String input = JOptionPane.showInputDialog("Term (ex. 2024/11/28):");
            if (input == null) { // Cancel button handling
                JOptionPane.showMessageDialog(null, "Task creation canceled.");
                date = null;
                break;
            }

            // Try to parse the date
            try {
                date = LocalDate.parse(input, formatter);

                // Check if date is in the future
                if (date.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(null,"The term date cannot be in the past. Please enter a future date.","Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    break; // Valid date entered
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null,"Invalid date format. Please use yyyy/MM/dd.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        return date;
    }

    //Pasiema issaugotas uzduotis
    public static void readJSON(){
        StringBuilder jsonString = new StringBuilder();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\Tasks.json"));
            String line;

            while((line = bufferedReader.readLine()) != null){
                jsonString.append(line).append("\n"); // kopijuoja eilutes
            }
            bufferedReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("Save file doesn't exist!");
        }catch (IOException e){
            System.out.println("Error occured with input/output!");
        }

        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonString.toString());
            JSONObject data = (JSONObject) obj;

            amount = (long) data.get("amount");
            nextId = amount + 1;

            JSONArray tasks = (JSONArray) data.get("tasks");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

            for (Object i : tasks) {
                JSONObject task = (JSONObject) i;
                long jid = (long) task.get("id");
                if(jid >= nextId){
                    nextId = jid + 1;
                }
                Id.offer(jid);
                Name.offer((String) task.get("name"));
                Subject.offer((String) task.get("subject"));
                String termDate = (String) task.get("term");
                LocalDate term = LocalDate.parse(termDate, formatter);
                Term.offer(term);
                Description.offer((String) task.get("description"));
            }

        }catch(ParseException e){
            System.out.println("Error occured with parsing!");
        }
    }

    //Issaugoja pakeitimus
    public static void writeJSON(){
        JSONObject data = new JSONObject();
        data.put("amount", amount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        JSONArray tasks = new JSONArray();
        for(int i = 0; i < amount; i++){
            JSONObject task = new JSONObject();
            task.put("id", Id.get(i));
            task.put("name", Name.get(i));
            task.put("subject", Subject.get(i));
            String termDate = Term.get(i).format(formatter);
            task.put("term", termDate);
            task.put("description", Description.get(i));
            tasks.add(task);
        }

        data.put("tasks", tasks);

        try(FileWriter file = new FileWriter("C:\\Users\\eliuk\\OneDrive\\Desktop\\projects\\Java\\TODOList\\src\\Tasks.json")) {
            file.write(data.toJSONString());
            file.flush();
        }catch (IOException e){
            System.out.println("Error writing JSON file!");
        }
    }

    //Uzduoties suradimas
    public static void search(int y) {
        found = false;
        String temp = "";
        sAmount = 0;
        if(y == 1){
            while(temp.length() < 3){
                temp = JOptionPane.showInputDialog("Type in atleast first 3 letters\nName: ");
                if(temp == null){
                    JOptionPane.showMessageDialog(null, "Search canceled.");
                    return;
                }
            }
            for (int i = 0; i < amount; i++) {
                String tempName = Name.get(i);
                if(tempName.toLowerCase().startsWith(temp.toLowerCase())){
                    found = true;
                    sAmount++;
                    sid.offer(sAmount);
                    id.offer(Id.get(i));
                    name.offer(Name.get(i));
                    subject.offer(Subject.get(i));
                    term.offer(Term.get(i));
                    description.offer(Description.get(i));
                }
            }
        }
        else{
            while(temp.length() < 3){
                temp = JOptionPane.showInputDialog("Type in atleast first 3 letters\nSubject: ");
                if(temp == null){
                    JOptionPane.showMessageDialog(null, "Search canceled.");
                    return;
                }
            }
            for (int i = 0; i < amount; i++) {
                String tempSubject = Subject.get(i);
                if(tempSubject.toLowerCase().startsWith(temp.toLowerCase())){
                    found = true;
                    sAmount++;
                    sid.offer(sAmount);
                    id.offer(Id.get(i));
                    name.offer(Name.get(i));
                    subject.offer(Subject.get(i));
                    term.offer(Term.get(i));
                    description.offer(Description.get(i));
                }
            }
        }
        if(found){
            return;
        }
        else{
            JOptionPane.showMessageDialog(null, "There are no tasks that have a name/subject starting with ("+temp+")","Not found",JOptionPane.WARNING_MESSAGE);
        }
        sAmount = 0;
        sid.clear();
        id.clear();
        name.clear();
        subject.clear();
        term.clear();
        description.clear();
    }

    // uzduociu ivedimas
    public static void addTask() {
        String name = "";
        String subject = "";
        String description = "";
        while(name.length() < 4){
            name = JOptionPane.showInputDialog("Name:");
            if (name == null) {
                JOptionPane.showMessageDialog(null, "Task creation canceled.");
                return;
            } else if (name.length() < 4){
                JOptionPane.showMessageDialog(null,"Name has to contain atleast 4 letters!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        while(subject.length() < 4) {
            subject = JOptionPane.showInputDialog("Subject:");
            if (subject == null) {
                JOptionPane.showMessageDialog(null, "Task creation canceled.");
                return;
            }else if (subject.length() < 4){
                JOptionPane.showMessageDialog(null,"Subject has to contain atleast 4 letters!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        LocalDate term = inputValidDate();
        while(description.length() < 10){
            description = JOptionPane.showInputDialog("Description:");
            if (description == null) {
                JOptionPane.showMessageDialog(null, "Task creation canceled.");
                return;
            }else if (description.length() < 10){
                JOptionPane.showMessageDialog(null,"Description has to contain atleast 10 letters!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        int position = 0;
        while(position == 0){
            String Position = JOptionPane.showInputDialog("Position (1 - " + amount + "):");
            if (Position == null) {
                JOptionPane.showMessageDialog(null, "Task creation canceled.");
                return;
            }
            try {
                position = Integer.parseInt(Position);
            } catch (NumberFormatException e) {
                position = 0;
                JOptionPane.showMessageDialog(null,"You must enter a number!","Error",JOptionPane.ERROR_MESSAGE);
            }
        }


        amount++;
        if(position < 1){
            position = 1;
        } else if (position > amount) {
            position = (int) amount;
        }
        Id.add(position - 1, nextId);
        nextId++;
        Name.add(position - 1, name);
        Subject.add(position - 1, subject);
        Term.add(position - 1, term);
        Description.add(position - 1, description);
        writeJSON();
        JOptionPane.showMessageDialog(null, "Task created successfully.");
    }

 }