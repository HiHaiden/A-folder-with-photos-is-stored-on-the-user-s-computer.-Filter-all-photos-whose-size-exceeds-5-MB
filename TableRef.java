// Файл TableRef
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;

public class TableRef extends AbstractTableModel {
    Statement stmnt;
    ArrayList<Integer> idList = new ArrayList<>();

    public TableRef(String url, String login, String pass){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, login, pass);
            stmnt = connect.createStatement();
            loadIDList();
        } catch(Exception ex){System.out.println("Connection lost..." + ex.getMessage());}
    }

    @Override
    public int getRowCount(){
        int count = 0;

        try{
            ResultSet rs = stmnt.executeQuery("SELECT COUNT(*) AS count FROM фоточки");
            rs.next();
            count = rs.getInt("count");
        } catch(SQLException ex){System.out.println("Error: " + ex.getMessage());}

        return count;
    }

    @Override
    public int getColumnCount(){
        return 7;
    }

    public Object getValueAt(int i, int j){
        Object cell = null;
        try{
            ResultSet rs = stmnt.executeQuery("SELECT * FROM фоточки WHERE ID = " + idList.get(i));
            rs.next();
            cell = rs.getObject(j + 1);
        } catch(SQLException ex){System.out.println("Error: " + ex.getMessage());}
        return cell;
    }

    @Override
    public void setValueAt(Object o, int i, int j) {
        try {
            switch (j) {
                case 1 -> stmnt.execute(
                        "UPDATE фоточки SET Имя = '" + o +
                                "' WHERE ID = " + idList.get(i));
                case 2 -> stmnt.execute(
                        "UPDATE фоточки SET Дата = '" + o +
                                "' WHERE ID = " + idList.get(i));
                case 3 -> stmnt.execute(
                        "UPDATE фоточки SET Место = '" + o +
                                "' WHERE ID = " + idList.get(i));
                case 4 -> stmnt.execute(
                        "UPDATE фоточки SET Разрешение = '" + o +
                                "' WHERE ID = " + idList.get(i));
                case 5 -> stmnt.execute(
                        "UPDATE фоточки SET Тип_Файла = '" + o +
                                "' WHERE ID = " + idList.get(i));
                case 6 -> stmnt.execute(
                        "UPDATE фоточки SET Размер = '" + o +
                                "' WHERE ID = " + idList.get(i));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    @Override
    public boolean isCellEditable(int i, int j) {
        return j != 0;
    }

    protected void loadIDList() {
        try {
            ResultSet rs = stmnt.executeQuery("SELECT ID FROM фоточки");
            idList.clear();
            int count = 0;
            while (rs.next()) {
                idList.add(count++,rs.getInt("ID"));
            }
        } catch (SQLException ex) {System.out.println("Error: " + ex.getMessage());}
    }

    public String getColumnName(int i) {
// Задаем имена колонок
        if (i == 1)
            return "Имя";
        if (i == 2)
            return "Дата";
        if (i == 3)
            return "Место";
        if (i == 4)
            return "Разрешение";
        if (i == 5)
            return "Тип_Файла";
        if (i == 6)
            return "Размер";
        return "ID";
    }

    public void insert() {
        try {
            stmnt.execute(
                    "INSERT INTO фоточки(Имя, Дата, Место, Разрешение, Тип_Файла, Размер) " +
                            "VALUES (' ', ' ', ' ', ' ', ' ', 0);");
            fireTableDataChanged();
            loadIDList();
        } catch (SQLException ex) {System.out.println("Error: " + ex.getMessage());}
    }

    public void delete(int selectedRow) {
        try {
            stmnt.execute(
                    "DELETE FROM фоточки " +
                            "WHERE ID = '" + idList.get(selectedRow) +"';");
            fireTableDataChanged();
            loadIDList();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}