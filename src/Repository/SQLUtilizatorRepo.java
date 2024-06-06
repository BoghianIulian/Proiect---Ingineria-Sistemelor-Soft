package Repository;

import Domain.Utilizator;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLUtilizatorRepo extends Repository<Utilizator>{
    private Connection conn = null;
    private final String dbLocation;

    public SQLUtilizatorRepo(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {

        try{
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM utilizator"); ResultSet rs =statement.executeQuery();) {
                while (rs.next()){
                    String cnp = rs.getString("cnp");
                    String nume = rs.getString("nume");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    Utilizator u = new Utilizator(cnp,nume,isAdmin);
                    this.elems.add(u);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void createSchema() {
        try {
            try(final Statement stmt = conn.createStatement()){
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS utilizator(cnp varchar(15) , nume varchar(50) ,isAdmin boolean);" );
            }
        }catch (SQLException e){
            System.err.println("[ERROR] createSchema : "+ e.getMessage());
        }
    }

    private void closeConnection(){
        try{
            if(conn!=null)conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void openConnection(){
        try{
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(dbLocation);
            if(conn == null || conn.isClosed())
                conn=ds.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addElem(Utilizator u) throws DuplicateObjectException{
        super.addElem(u);
        String s="";
        try{
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO utilizator VALUES (?,?,?)")){
                stmt.setString(1,u.getId());
                stmt.setString(2,u.getName());
                stmt.setBoolean(3,u.isAdmin());
                stmt.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeElem(String id) throws RepositoryException{
        super.removeElem(id);
        try {
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM utilizator WHERE cnp=?")){
                stmt.setString(1,id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElem(Utilizator c1,Utilizator c2)throws RepositoryException{
        super.updateElem(c1,c2);
        removeElem(c1.getId());
        addElem(c2);
    }
}
