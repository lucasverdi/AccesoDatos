package AccesoBbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Creamos la clase conexion, la cual uilizaremos en BaseDatosAcceso y ControlAcceso
 * @author Julián
 *@serial 1.0
 */
public class Conexion {
/**
 * deben de ser static ya que el método lo es
 */
	private static final String CONTROLADOR = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://remotemysql.com:3306/IpMoLNiazp";	
	private static final String USUARIO = "IpMoLNiazp";
	private static final String CLAVE = "KNGPjOputg";
/**
 * nos devuelve la conexion
 * @return
 */

	public static Connection getConection() {
		Connection cn = null;
		/**
		 * Comprobamos el controlador
		 * y conectamos a los valores de nuestra base de datos
		 * doble catch uno para el controlador(obligatorio), y otro pra la conexión
		 * devolvemos los datos de la conexión
		 */
		try {
			Class.forName(CONTROLADOR);			
			cn=DriverManager.getConnection(URL, USUARIO, CLAVE);			
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
			
		} catch (SQLException e) {
			System.out.println("Error en la conexion"+e.getMessage());
			e.printStackTrace();
			return cn;//confirmar dos returns
		}
		return cn;
	}
	
	public static ResultSet getTabla(String  consulta) {
		
		/**
		 * yo este al final lo he hecho asi , mas complicado,le paso parámetros y en función 
		 * de la condsulta hace un executeQuery(Select) o un executeUpdate (Las demas)
		 * devolvemos los datos a manipular.
		 */
		Connection cn = getConection();
		Statement st;
		ResultSet datos=null;
		
		/**
		 * .startsWith en principio nos indica si el principio de la cadena o secuencia tiene ese valor
		 * yo en mi caso si lo tiene ejecuto executeQuery y si no executeUpdate.
		 * 
		 *  La verdad es que me he complicado , pero he aprendido bastante sobre las conexiones.Y muchas horas , jajajjaja
		 */
		try {
			st=cn.createStatement();			
			if(consulta.startsWith("SELECT")) {//para segun que consulta hagamos selct por aki los demas por aki
			     datos=st.executeQuery(consulta);
			}else {
				 st.executeUpdate(consulta);
			}

		} catch (SQLException e) {
			System.out.println("Error en la conexion"+e.getMessage());
			e.printStackTrace();
		
		}
		return datos;
		
		
	}
	
}
