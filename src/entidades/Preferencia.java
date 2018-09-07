package entidades;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "preferencias")
public class Preferencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_preferencia;
	
	@Column(name="preferencia")
	private String preferencia;
		
	@ManyToOne
	@JoinColumn(name = "cliente_matricula", referencedColumnName = "matricula")
	private Cliente cliente;
	
	public Preferencia() {

	}

	public Preferencia(String preferencia) {
		super();
		this.preferencia = preferencia;
	}

}