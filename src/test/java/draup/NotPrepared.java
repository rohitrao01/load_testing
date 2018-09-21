/*package draup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.testng.annotations.Test;
@Test
public class NotPrepared{
public void test() throws ClassNotFoundException

{

    
Connection con = null;
Statement st = null;
	ResultSet postResult;
        String url = "jdbc:postgresql://draup-server-qa.cmfutgtnbkyw.us-east-2.rds.amazonaws.com:5432/draup";
        //String user = "draup";
        String password ="t3st1ng123";
        String user = "draup";
        
        try {
        	Class.forName("org.postgresql.Driver");
        	System.out.println("connecting to database...");
        	
            con = DriverManager.getConnection(url, user, password);
            if(con!=null) {
            	System.out.println("Postgres Connected to the database");
            }
            st = con.createStatement();

           // for (int i=1; i<=1000; i++) {
            		
            		
	               String query = "select (financial_head,amount) from account_financials where account_id = 85324 and concerned_year = 2016";
	               postResult = st.executeQuery(query);
	               
	               ArrayList <String> postgresAccounts= new ArrayList<String>();
	             
	              while (postResult.next())
	             {
	            	 // for(int i=0;i<value.siz;)
	                postgresAccounts.add(postResult.getString(1));
	                 
	              }
	              System.out.println(postgresAccounts);
            		
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(NotPrepared.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(NotPrepared.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
}}*/