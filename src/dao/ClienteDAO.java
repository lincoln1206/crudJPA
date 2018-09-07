package dao;

import entidades.Cliente;
import entidades.Preferencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

	private static ClienteDAO instance;
	protected EntityManager entityManager;

	public static ClienteDAO getInstance() {
		if (instance == null) {
			instance = new ClienteDAO();
		}

		return instance;
	}

	public ClienteDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudJPA");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	/* Adicionar */

	public Cliente adicionarCliente(Cliente cliente) {
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		for (Preferencia preferencias : cliente.getPreferencias()) {
			entityManager.persist(preferencias);
		}
		entityManager.getTransaction().commit();
		return cliente;
	}

	/* Listar Clientes */

	@SuppressWarnings("unchecked")
	public List<Cliente> findAllClientes() {
		return entityManager.createQuery("FROM " + Cliente.class.getName()).getResultList();
	}

	public void listarClientes() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes = findAllClientes();
		System.out.println(clientes.toString());
	}

	/* Listar Preferencias */
	
	public List<Preferencia> findAllPreferencias(final long matricula) {
		String query = "Select p FROM Preferencia p where p.cliente.matricula=:matricula";
		return entityManager.createQuery(query , Preferencia.class)
				.setParameter("matricula", matricula)
				.getResultList();
	}

	public void listarPreferencias(final long matricula) {
		List<Preferencia> preferencias = new ArrayList<Preferencia>();
		preferencias = findAllPreferencias(matricula);
		System.out.println(preferencias.toString());
	}

	/* Buscar Eager */

	public Cliente BuscarClientePorIdEager(final long matricula) {
		return entityManager.find(Cliente.class, matricula);
	}

	/* Buscar Lazy */

	public Cliente BuscarClientePorIdLazy(final long matricula) {
		return entityManager.getReference(Cliente.class, matricula);
	}

	/* Atualizar */

	public void AtualizarCliente(long matricula, String nome, String sobreNome, boolean ativo) {

		try {
			Cliente cliente = BuscarClientePorIdEager(matricula);
			entityManager.getTransaction().begin();
			cliente.setNome(nome);
			cliente.setSobreNome(sobreNome);
			cliente.setAtivo(ativo);
			entityManager.merge(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}

	}

	/* Desvincular */

	public void DesvincularCliente(Cliente cliente) {
		entityManager.detach(cliente);
	}

	/* Remover */

	private void remove(Cliente cliente) {
		try {
			entityManager.getTransaction().begin();
			cliente = entityManager.find(Cliente.class, cliente.getMatricula());
			entityManager.remove(cliente);
			for (Preferencia preferencias : cliente.getPreferencias()) {
				entityManager.remove(preferencias);
			}
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void RemoverCliente(final long matricula) {
		try {
			Cliente cliente = BuscarClientePorIdEager(matricula);
			remove(cliente);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* Ativar Cliente */
	public void ativarCliente(long matricula) {

		try {
			Cliente cliente = BuscarClientePorIdEager(matricula);
			entityManager.getTransaction().begin();
			cliente.setAtivo(true);
			entityManager.merge(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}

	}
	
	/* Desativar Cliente */
	public void desativarCliente(long matricula) {

		try {
			Cliente cliente = BuscarClientePorIdEager(matricula);
			entityManager.getTransaction().begin();
			cliente.setAtivo(false);
			entityManager.merge(cliente);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}

	}
}
