package cajero_automatico;
import javax.swing.JFrame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class CajeroGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cajero Automático");
        Inicio_Cajero panel = new Inicio_Cajero();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setUndecorated(true); // Ocultar la barra de título y bordes de la ventana

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(frame); // Establecer la ventana en modo de pantalla completa

        frame.setVisible(true);
        panel.IC();
        panel.thread.start();
    }
}
