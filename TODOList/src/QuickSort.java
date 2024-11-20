import javax.swing.*;
import java.time.LocalDate;

public class QuickSort {

    public static void sortByTerm(){
        if (Main.amount <= 0){
            JOptionPane.showMessageDialog(null,"There are no tasks to sort!","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        quickSort(0, Main.Term.size() - 1);
        JOptionPane.showMessageDialog(null,"Tasks sorted successfully!");
    }

    private static void quickSort(int low, int high){
        if(low < high){
            int pivotIndex = partition(low,high);
            quickSort(low,pivotIndex - 1);
            quickSort(pivotIndex + 1,high);
        }
    }

    public static int partition(int low, int high){
        LocalDate pivot = Main.Term.get(high);
        int i = low - 1;

        for(int j = low; j < high; j++){
            if(Main.Term.get(j).isBefore(pivot) || Main.Term.get(j).isEqual(pivot)){
                i++;
                swap(i + 1, high);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    public static void swap(int i, int j){
        LocalDate tempTerm = Main.Term.get(i);
        Main.Term.set(i,Main.Term.get(j));
        Main.Term.set(j,tempTerm);

        long tempId = Main.Id.get(i);
        Main.Id.set(i,Main.Id.get(j));
        Main.Id.set(j,tempId);

        String tempName = Main.Name.get(i);
        Main.Name.set(i,Main.Name.get(j));
        Main.Name.set(j,tempName);

        String tempSubject = Main.Subject.get(i);
        Main.Subject.set(i,Main.Subject.get(j));
        Main.Subject.set(j,tempSubject);

        String tempDescription = Main.Description.get(i);
        Main.Description.set(i,Main.Description.get(j));
        Main.Description.set(j,tempDescription);
    }
}
