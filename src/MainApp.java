import com.blixmark.utilites.OnAppCreate;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OnAppCreate();
        });
    }
}