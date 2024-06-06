package Repository;

import Domain.Loc;
import Domain.Rezervare;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class SQLRezervareRepo extends Repository<Rezervare>{
    private Connection conn = null;
    private final String dbLocation;

    public SQLRezervareRepo(String dbLocation) {
        this.dbLocation = "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();
    }

    private void loadData() {

        try{
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM rezervare"); ResultSet rs =statement.executeQuery();) {
                while (rs.next()){
                    String id = rs.getString("id");
                    String s= rs.getString("lista");
                    ArrayList<Loc> locuri = new ArrayList<>();
                    String[] tokens = s.split(";");
                    for(String t:tokens){
                        String[] l = t.split(",");
                        String rand_nr = l[0]+","+l[1];
                        Loc loc = new Loc(rand_nr,Float.parseFloat(l[2]),Boolean.parseBoolean(l[3]));
                        locuri.add(loc);
                    }
                    Rezervare r =new Rezervare(id , locuri);
                    this.elems.add(r);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void createSchema() {
        try {
            try(final Statement stmt = conn.createStatement()){
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rezervare(id varchar(50) , lista varchar(300));" );
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
    public void addElem(Rezervare u) throws DuplicateObjectException{
        super.addElem(u);
        String s="";
        try{
            try(PreparedStatement stmt=conn.prepareStatement("INSERT INTO cont VALUES (?,?)")){
                stmt.setString(1,u.getId());
                ArrayList<Loc> locuri = u.getLista();
                for(Loc l:locuri)
                {
                    s+=l.getId()+","+l.getPret()+","+l.isStare();
                }
                stmt.setString(2,s);
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
            try(PreparedStatement stmt=conn.prepareStatement("DELETE FROM cont WHERE id=?")){
                stmt.setString(1,id);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateElem(Rezervare c1,Rezervare c2)throws RepositoryException{
        super.updateElem(c1,c2);
        removeElem(c1.getId());
        addElem(c2);
    }

}
