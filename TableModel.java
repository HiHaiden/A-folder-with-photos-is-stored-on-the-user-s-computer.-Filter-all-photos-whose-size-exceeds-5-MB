// Файл TableModel
import javax.swing.*;
import java.awt.*;

public class TableModel extends JFrame{
    public TableModel() {

        super("База Фотографий");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

// добавим элементы для подключения к базе данных
        JLabel url = new JLabel("Ссылка к базе данных");
        JLabel login = new JLabel("Логин");
        JLabel pass = new JLabel("Пароль");
        JTextField urlField = new JTextField("jdbc:mysql://127.0.0.1:3306/fotokartochka?serverTimezone=UTC");
        JTextField loginField = new JTextField("mysql");
        JPasswordField passField = new JPasswordField("mysql");

        urlField.setMaximumSize(new Dimension(500,20));
        loginField.setMaximumSize(new Dimension(500,20));
        passField.setMaximumSize(new Dimension(500,20));

        url.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        pass.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(url);
        mainPanel.add(urlField);
        mainPanel.add(login);
        mainPanel.add(loginField);
        mainPanel.add(pass);
        mainPanel.add(passField);

        JButton connectDB = new JButton("Подключиться");
        connectDB.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(connectDB);

        JButton disconnectDB = new JButton("Отключиться");
        disconnectDB.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(disconnectDB);
        disconnectDB.setVisible(false);

        connectDB.addActionListener(listener -> {
            connectDB.setVisible(false);
            disconnectDB.setVisible(true);
            JTable table = new JTable(new TableRef(urlField.getText(), loginField.getText(), new String(passField.getPassword())));
            this.getContentPane().add(new JScrollPane(table));
        });

        disconnectDB.addActionListener(listener -> {
            connectDB.setVisible(true);
            disconnectDB.setVisible(false);
            this.getContentPane().remove(1);
            this.repaint();
        });

        JLabel manage = new JLabel("Кнопки управления");
        JButton addFoto = new JButton("Добавить Фото");
        JButton removeFoto = new JButton("Удалить Фото");
        JButton filterFoto = new JButton("Фильтр Фото");

        manage.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeFoto.setAlignmentX(Component.CENTER_ALIGNMENT);
        filterFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        addFoto.addActionListener(listener -> {
            JScrollPane scrollPane = (JScrollPane) this.getContentPane().getComponent(1);
            JViewport viewport = (JViewport) scrollPane.getComponent(0);
            JTable table = (JTable) viewport.getComponent(0);
            TableRef model = (TableRef) table.getModel();
            model.insert();
        });

        removeFoto.addActionListener(listener -> {
            JScrollPane scrollPane = (JScrollPane) this.getContentPane().getComponent(1);
            JViewport viewport = (JViewport) scrollPane.getComponent(0);
            JTable table = (JTable) viewport.getComponent(0);
            TableRef model = (TableRef) table.getModel();
            model.delete(table.getSelectedRow());
        });

        filterFoto.addActionListener(listener -> {
            JScrollPane scrollPane = (JScrollPane) this.getContentPane().getComponent(1);
            JViewport viewport = (JViewport) scrollPane.getComponent(0);
            JTable table = (JTable) viewport.getComponent(0);

            if (filterFoto.getText().equals("Фильтр Фото")) {
                table.setModel(new FilterTable(urlField.getText(), loginField.getText(), new String(passField.getPassword())));
                filterFoto.setText("Все Фото");
            } else {
                table.setModel(new TableRef(urlField.getText(), loginField.getText(), new String(passField.getPassword())));
                filterFoto.setText("Фильтр Фото");
            }

        });

        mainPanel.add(manage);
        mainPanel.add(addFoto);
        mainPanel.add(removeFoto);
        mainPanel.add(filterFoto);

        this.getContentPane().add(mainPanel, BorderLayout.EAST);
        this.setPreferredSize(new Dimension(1080, 600));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            new TableModel();
        });
    }
}