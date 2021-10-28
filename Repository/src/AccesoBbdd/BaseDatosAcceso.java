package AccesoBbdd;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

/**@since clase Conexion
 * @since clase ControlAcceso
 * @version 1.0
 * @author Julián
 *
 */

public class BaseDatosAcceso extends JDialog {
	Conexion co = new Conexion();
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public static void main(String[] args) {
		try {
			BaseDatosAcceso dialog = new BaseDatosAcceso();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * creacion del Jdialog a traves del WindownsBuilder, arrastrando componentes, lo crea automático este código 
	 */
	public BaseDatosAcceso() {
		setBounds(100, 100, 481, 306);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);// posicion central
		getPreferredSize();// tamaño se vea todo el contenido
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			DefaultTableModel modelo = new DefaultTableModel();
			table = new JTable(modelo);
			table.setVisible(true);
			contentPanel.setLayout(new BorderLayout(0, 0));
			contentPanel.add(table);

		}
		{
			JLabel lblNewLabel = new JLabel(
					"Central Nuclear PINOS PARDOS S.A             Personal en las instalaciones");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBackground(SystemColor.textHighlight);
			lblNewLabel.setForeground(SystemColor.textHighlight);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel, BorderLayout.NORTH);
		}
		{
			JScrollPane scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane, BorderLayout.EAST);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnCargarBaseDatos = new JButton("Contabilizar Personal");
				btnCargarBaseDatos.setForeground(SystemColor.textHighlight);
				/**
				 * creamos un ActionListener, cuando pulsamos el boton llamamos al método RellenarTabla();
				 */
				btnCargarBaseDatos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						RellenarTabla();
					}
				});
				{
					JButton btnNewSalida = new JButton(" Salida Personal");
					btnNewSalida.setForeground(SystemColor.textHighlight);
					/**
					 * creamos un ActionListener, cuando pulsamos el boton llamamos al método ,pedimos un dato mediante
					 * un JoptionPane y se lo pasamos como parámetro al método Salida();
					 */
					btnNewSalida.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {							
							String nombre = JOptionPane.showInputDialog("Nombre del trabajador que abandona las instalaciones");
							Salida(nombre);
						}
					});
					buttonPane.add(btnNewSalida);
				}
				{
					
					JButton btnNewAnadirEntrada = new JButton("Entrada personal");
					btnNewAnadirEntrada.setForeground(SystemColor.textHighlight);
					/**
					 * pedimos tres parámetros, nombre,apellidosy edad para pasarselos al método entrada(),
					 * controlamos que los campos nombre y apellidos esten rellenos(por si pulsamos sin querer)
					 * de lo contrario nos sale un aviso.
					 */
					
					btnNewAnadirEntrada.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {					

							try {

								String nombre = JOptionPane.showInputDialog("Nombre Trabajador entra en las instalaciones");
								String apellidos = JOptionPane.showInputDialog("Apellidos Trabajador entra en las instalaciones");
								int edad = Integer.parseInt(JOptionPane.showInputDialog("Edad Trabajador entra en las instalaciones"));
								if ((nombre.equals("")) || (apellidos.equals(""))) {
									JOptionPane.showMessageDialog(null, "faltan datos, borre la entrada");

								} else {
									Entrada(nombre, apellidos, edad);
								}

							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, "Le edad debe ser un numero");

							}
						}

					});
					buttonPane.add(btnNewAnadirEntrada);
				}
				btnCargarBaseDatos.setActionCommand("OK");
				buttonPane.add(btnCargarBaseDatos);
				getRootPane().setDefaultButton(btnCargarBaseDatos);
			}
		}

	}
/**
 * Creamos el modelo de tabla
 * y según yo he diseñado la conexxion a Result le pasamos directamente la sentencia
 * Ponemos cabecera a modelo de la tabla
 * recorremos mientras quede algo
 * y enchufamos datos al modelo
 * Acabado pasamos el model a la tabla
 * Si hay problemas mostramos la excepcion 
 * 
 */
	@SuppressWarnings("static-access")
	private void RellenarTabla() {		

		DefaultTableModel modelo = new DefaultTableModel();
		ResultSet rs = co.getTabla("SELECT * FROM datospersona");
		modelo.setColumnIdentifiers(new Object[] { "Nombre", "Apellidos", "Edad" });
		try {
			while (rs.next()) {
				modelo.addRow(new Object[] { rs.getString("Nombre"), rs.getString("Apellidos"), rs.getInt("Edad") });				
			}
			table.setModel(modelo);
		} catch (Exception e) {
			System.out.println("DATOS NO CARGADOS"+e.getMessage());
			
		}

	}
	
	/**
	 * Igual que arriba pasamos directo la consulta al Resulset( Aqui haremos un executeQuery para comprobar si el parametro existe
	 * y si no un executeUpdate para borrarlo.Si esta el nombre eliminamos el registro
	 * @param nombre
	 */

	@SuppressWarnings("static-access")
	public void Salida(String nombre) {

		ResultSet rs = co.getTabla("SELECT * FROM datospersona Where Nombre = '" + nombre + "'");

		try {
			if (rs.next()) {
				rs = co.getTabla("DELETE FROM datospersona Where Nombre = '" + nombre + "'");
				JOptionPane.showMessageDialog(null, "Trabajador abandona la central");
			} else {
				JOptionPane.showMessageDialog(null, "Nombre del trabajador erroneo o no esta en las intalaciones");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
			
		}

	}
	
	/**Pasamos 3 parámetros en el ActionLIstener y se los enchufamos a la bbdd, no hay ID con lo cual
	 * se pueden repetir datos.
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param edad
	 */

	@SuppressWarnings({ "static-access", "unused" })
	public void Entrada(String nombre, String apellidos, int edad) {

		ResultSet rs = null;

		rs = co.getTabla("Insert into datospersona (Nombre,Apellidos,Edad) values('" + nombre + "'," + "'" + apellidos+ "','" + edad + "')");
		JOptionPane.showMessageDialog(null, "Trabajador entra la central");

	}

}
