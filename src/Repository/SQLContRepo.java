package Repository;

import Domain.Cont;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLContRepo extends Repository<Cont>{
    private Connection conn = null;
    private final String dbLocation;

    public SQLContRepo(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {

        try{
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM cont"); ResultSet rs =statement.executeQuery();) {
                while (rs.next()){
                    String username = rs.getString("username");
                    String parola = rs.getString("parola");
                    String cnp = rs.getString("cnp");
                    Cont u = new Cont(username,parola,cnp);
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS cont(username varchar(30) , parola varchar(30) , cnp varchar(15));" );
            }
        }catch (SQLException e){
            System.err.println("[ERROR] createSchema : "+ e.getMessage());
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
    private void closeConnection(){
        try{
            if(conn!=null)conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addElem(Cont u) throws DuplicateObjectException{
        super.addElem(u);
        try{
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO cont VALUES (?,?,?)")){
                stmt.setString(1,u.getId());
                stmt.setString(2,u.getParola());
                stmt.setString(3,u.getCnp());
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
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM cont WHERE username=?")){
                stmt.setString(1,id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElem(Cont c1,Cont c2)throws RepositoryException{
        super.updateElem(c1,c2);
        removeElem(c1.getId());
        addElem(c2);
    }
}
