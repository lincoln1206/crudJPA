import dao.ClienteDAO;
import entidades.Cliente;
import entidades.Preferencia;

/*Crud --OK*/
public class Aplicacao {

	public static void main(String[] args) {

		ClienteDAO c = new ClienteDAO();

		/* Adicionar clientes -- OK */
		Cliente cliente = new Cliente("Lincoln", "Max", true);
		Cliente cliente2 = new Cliente("George", "Max", true);
		Preferencia preferencia = new Preferencia("Estudar");
		Preferencia preferencia2 = new Preferencia("Dormir");

		cliente.addPreferencia(preferencia);
		cliente2.addPreferencia(preferencia2);

		c.adicionarCliente(cliente);
		c.adicionarCliente(cliente2);

		/* Remover cliente */
		c.RemoverCliente(2);

		/* Alterar clientes -- OK */
		c.AtualizarCliente(1, "Lincoln - Atualizado", "Max", false);

		/* Ativar clientes -- OK */
		c.ativarCliente(1);

		/* Desativar Cliente --OK */
		c.desativarCliente(1);

		/* Listar clientes -- OK */
		c.listarClientes();

		/* Listar preferencias -- OK */
		c.listarPreferencias(1);

	}

}
