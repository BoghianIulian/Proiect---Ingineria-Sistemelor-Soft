package Repository;

import Domain.Loc;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class SQLocRepo extends Repository<Loc>{
    private Connection conn = null;
    private final String dbLocation;

    public SQLocRepo(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {
        try {
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM loc"); ResultSet rs =statement.executeQuery();){
                while (rs.next()){
                    int rand = rs.getInt("rand");
                    int numar = rs.getInt("numar");
                    float pret = rs.getFloat("pret");
                    boolean st = rs.getBoolean("stare");
                    String s = String.valueOf(rand)+","+String.valueOf(numar);
                    Loc l = new Loc(s,pret,st);
                    this.elems.add(l);
                }
            }

        }catch (SQLException e){
            e.printStackTrace();;
        }
    }

    private void createSchema() {
        try {
            try(final Statement stmt = conn.createStatement()){
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS loc(rand int ,numar int, pret float ,stare boolean);" );
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

    private void openConnection() {
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
    public void addElem(Loc l) throws DuplicateObjectException{
        super.addElem(l);
        String s="";
        try {
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO loc VALUES (?,?,?,?)")){
                stmt.setInt(1,l.getRand());
                stmt.setInt(2,l.getNumar());
                stmt.setFloat(3,l.getPret());
                stmt.setBoolean(4,l.isStare());
                stmt.executeUpdate();

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void removeElem(String id) throws RepositoryException{

        Loc l = getById(id);
        super.removeElem(id);

        try {
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM loc WHERE rand=? and numar =?")){


                stmt.setInt(1,l.getRand());
                stmt.setInt(2,l.getNumar());
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElem(Loc c1,Loc c2)throws RepositoryException{
        System.out.println("super");
        super.updateElem(c1,c2);
        System.out.println("aici e eroarea boss");
        removeElem(c1.getId());
        System.out.println("aici e eroarea boss2");
        addElem(c2);
        System.out.println("aici e eroarea bos3");
    }

}
