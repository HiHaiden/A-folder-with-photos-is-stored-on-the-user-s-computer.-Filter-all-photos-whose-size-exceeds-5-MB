// Файл FilterTable
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilterTable extends TableRef{
    public FilterTable(String url, String login, String pass) {
        super(url, login, pass);
    }

    @Override
    public int getRowCount() {
// Получаем количество строк в таблице
        int count = 0;
        try {
            ResultSet rs =
                    stmnt.executeQuery("SELECT count(*) AS count FROM фоточки WHERE Размер > 5");
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return count;
    }

    protected void loadIdList() {
        try {
            ResultSet rs = stmnt.executeQuery("SELECT ID FROM фоточки WHERE Размер > 5");
            idList.clear();
            int count = 0;
            while (rs.next()) {
                idList.add(count++,rs.getInt("ID"));
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}