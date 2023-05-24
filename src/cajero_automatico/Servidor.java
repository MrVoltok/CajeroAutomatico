package cajero_automatico;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    private static final String URL = "jdbc:mysql://localhost:3306/cajero";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "contraseña";
    private static int clienteCounter = 1; // Contador de clientes conectados

    public static void main(String[] args) {
        int puerto = 5000;

        try {
            // Creamos un socket de servidor y lo vinculamos al puerto especificado
            ServerSocket serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (true) {
                // Esperamos a que un cliente se conecte
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());

                // Creamos un hilo para manejar la conexión con el cliente
                Thread clientThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // Asignar un número de cliente
                            int numeroCliente = asignarNumeroCliente();
                            System.out.println("Cliente conectado. Número: " + numeroCliente);

                            // Creamos los objetos para enviar y recibir datos
                            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                            // Leemos el número de cuenta enviado por el cliente
                            String numeroCuenta = in.readLine();
                            System.out.println("Número de cuenta recibido: " + numeroCuenta);

                            // Leemos el PIN enviado por el cliente
                            String pin = in.readLine();
                            System.out.println("PIN recibido: " + pin);

                            // Verificamos la existencia del número de cuenta y su PIN correspondiente
                            boolean cuentaValida = verificarCuenta(numeroCuenta, pin);

                            if (!cuentaValida) {
                                out.println("Cuenta o PIN incorrectos");
                                in.close();
                                out.close();
                                clientSocket.close();
                                return;
                            }

                            // Leemos el mensaje enviado por el cliente
                            String mensaje = in.readLine();
                            System.out.println("Mensaje recibido: " + mensaje);

                            // Procesamos el mensaje y generamos una respuesta
                            String respuesta = procesarMensaje(numeroCuenta, mensaje);

                            // Enviamos la respuesta al cliente
                            out.println(respuesta);

                            // Cerramos los flujos y el socket del cliente
                            in.close();
                            out.close();
                            clientSocket.close();

                            // Guardar información en el archivo de registro
                            guardarRegistro(numeroCliente, numeroCuenta, mensaje);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int asignarNumeroCliente() {
        synchronized (Servidor.class) {
            int numeroCliente = clienteCounter;
            clienteCounter = (clienteCounter % 10) + 1;
            return numeroCliente;
        }
    }

    private static void guardarRegistro(int numeroCliente, String numeroCuenta, String accion) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = now.format(formatter);

        String registro = "[" + fechaHora + "] Cliente " + numeroCliente +
                " - Cuenta: " + numeroCuenta +
                " - Acción: " + accion;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registro_cajeros.txt", true))) {
            writer.write(registro);
            writer.newLine();
        }
    }

    private static boolean verificarCuenta(String numeroCuenta, String pin) {
        try {
            // Establecemos la conexión con la base de datos
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Creamos la consulta preparada para verificar la cuenta y el PIN
            String query = "SELECT COUNT(*) FROM cuentas WHERE numero_cuenta = ? AND pin = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, numeroCuenta);
            statement.setString(2, pin);

            // Ejecutamos la consulta y obtenemos el resultado
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            // Cerramos los recursos
            resultSet.close();
            statement.close();
            connection.close();

            // Verificamos si la cuenta y el PIN son válidos
            return count == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static String procesarMensaje(String numeroCuenta, String mensaje) {
        // Procesamos el mensaje y generamos una respuesta adecuada
        // Aquí puedes agregar la lógica para procesar diferentes tipos de mensajes
        // y realizar operaciones en la cuenta correspondiente

        return "Respuesta del servidor";
    }
}

