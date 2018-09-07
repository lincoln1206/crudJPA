package entidades;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long matricula;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="sobreNome")
	private String sobreNome;
	
	@Column(name="ativo")
	private boolean ativo;

	@OneToMany(mappedBy = "cliente")
	private List<Preferencia> preferencias = new ArrayList<Preferencia>();

	public void addPreferencia(Preferencia preferencia) {
		this.preferencias.add(preferencia);
		preferencia.setCliente(this);
	}

	public Cliente() {

	}

	public Cliente(String nome, String sobreNome, boolean ativo) {
		super();
		this.nome = nome;
		this.sobreNome = sobreNome;
		this.ativo = ativo;
	}
	
	public String toString() {
		return "Matricula :" + this.matricula + " Nome :"+ this.nome + " Sobrenome :" + this.sobreNome + " Ativo:"+ this.ativo;
	}
}
