package AccesoBbdd;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Pequeña ventana de password
 * 
 * @author Julián
 * @serial 1.0
 * @since BaseDatosAcceso
 */
public class ControlAcceso extends JFrame {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private JPanel contentPane;
	static ControlAcceso ca = new ControlAcceso();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ca.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Creamos este Jframe desde WindonsBuilder Arrastrando
	 * objetos, código automático Modificamos los valores y nombres que nos
	 * interesan Lineas 41-42 contraseñas para acceder
	 */
	private final String usuario = "practica";
	private final String clave = "1234";
	private int cont = 0;
	JLabel lblNewClave = new JLabel("Password:");
	JButton btnConfirmarAcceso = new JButton("Confirmar Acceso");
	JButton btnAcceso = new JButton("Acceder a Control");
	JTextField textFieldUsuario = new JTextField();
	JTextField textFieldclave = new JTextField();
	JLabel lblControlAcceso = new JLabel("Control Acceso Central Nuclear");
	JLabel lblUsuario = new JLabel("Usuario");

	/**
	 * 
	 */

	public ControlAcceso() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		textFieldUsuario.setBackground(SystemColor.control);
		textFieldUsuario.setBounds(131, 74, 204, 20);
		getContentPane().add(textFieldUsuario);
		textFieldUsuario.setColumns(10);
		textFieldclave.setBackground(SystemColor.control);
		textFieldclave.setBounds(131, 120, 204, 20);
		getContentPane().add(textFieldclave);
		textFieldclave.setColumns(10);
		lblNewClave.setBounds(36, 123, 85, 14);
		getContentPane().add(lblNewClave);
		/**
		 * ActionListener, cuando pulsamos creamos un objeto BaseDatosAcceso (JDialog),
		 * lo hacemos visible al mismo tiempo hacemos no visible este frame
		 */
		btnAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BaseDatosAcceso ba = new BaseDatosAcceso();
				ba.setVisible(true);
				ca.setVisible(false);
			}
		});
		btnAcceso.setForeground(SystemColor.activeCaption);
		btnAcceso.setFont(new Font("Yu Gothic", Font.BOLD, 13));
		btnAcceso.setBounds(154, 178, 152, 42);
		btnAcceso.setVisible(false);
		getContentPane().add(btnAcceso);
		/**
		 * ActionListener, cuando pulsamos comprobamos que usuario y contaseña son
		 * correctos si es correcta nos dara un mensaje de bienvenida y pondra visible
		 * en boton de acceso De lo contrario nos lo indica y llama al método
		 * VaciarPantalla() Nos permite tres intentos, si fallamos llama al método
		 * limpiar()
		 */
		btnConfirmarAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if ((usuario.equals(textFieldUsuario.getText())) && (clave.equals(textFieldclave.getText()))) {
					JOptionPane.showMessageDialog(null, "Bienvenido Sñr Fran Perez , pulse el boton Acceder a Control");
					btnAcceso.setVisible(true);

				} else {
					JOptionPane.showMessageDialog(null, "Clave incorrecta");
					cont++;
					VaciarPantalla();
				}
				if (cont == 3) {
					limpiarPantalla();
					JOptionPane.showMessageDialog(null, "Usted no esta autorizado !!!!!sonando alarma silenciosa!!!!!");

				}

			}
		});
		btnConfirmarAcceso.setBounds(289, 29, 135, 34);
		getContentPane().add(btnConfirmarAcceso);

		lblControlAcceso.setBounds(28, 29, 234, 34);
		getContentPane().add(lblControlAcceso);

		lblUsuario.setBounds(36, 77, 85, 17);
		getContentPane().add(lblUsuario);

	}

	/**
	 * Dejamos el Frame visible , pero ocultamos todos sus componentes.
	 */

	public void limpiarPantalla() {

		lblUsuario.setVisible(false);
		lblControlAcceso.setVisible(false);
		btnConfirmarAcceso.setVisible(false);
		btnAcceso.setVisible(false);
		textFieldUsuario.setVisible(false);
		textFieldclave.setVisible(false);
		lblNewClave.setVisible(false);

	}

	/**
	 * Vaciamos los componentes de datos
	 */
	void VaciarPantalla() {
		textFieldUsuario.setText(null);
		textFieldclave.setText(null);

	}

}
