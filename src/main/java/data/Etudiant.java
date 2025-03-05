package data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import data.*;

/**
 * POJO Etudiant
 * 
 * @author Valentin
 */

@Entity
@Table(name = "etudiant")
@NamedQueries({ @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
		@NamedQuery(name = "Etudiant.findByNumero", query = "SELECT e FROM Etudiant e WHERE e.numero = :numero"),
		@NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom"),
		@NamedQuery(name = "Etudiant.findByPrenom", query = "SELECT e FROM Etudiant e WHERE e.prenom = :prenom")})
	

public class Etudiant implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "numero")
	private Integer numero;

	@Column(name = "nom")
	private String nom;

	@Column(name = "prenom")
	private String prenom;

	@Column(name = "date_naissance")
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;

	@Column(name = "adresse")
	private String adresse;

	@JoinTable(name = "participe", joinColumns = {
			@JoinColumn(name = "numero", referencedColumnName = "numero") }, inverseJoinColumns = {
					@JoinColumn(name = "id_ue", referencedColumnName = "id_ue") })
	@ManyToMany
	private Set<UE> ues;

	/**
	 * Constructeur par défaut
	 */
	public Etudiant() {
		this.ues = new HashSet<>();
	}

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param numero
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @param adresse
	 * @param responsable
	 * @param groupe
	 */
	public Etudiant(Integer numero, String nom, String prenom, Date dateNaissance, String adresse) {
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;

	}

	// Getters
	public Integer getNumero() {
		return numero;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public Set<UE> getues() {
		return ues;
	}

	// Setters
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setues(Set<UE> ues) {
		this.ues = ues;
	}

	// Méthodes pour gérer les ues
	public void addParticipation(UE participation) {
		this.ues.add(participation);
	}

	public void removeParticipation(UE participation) {
		this.ues.remove(participation);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 29 * hash + Objects.hashCode(this.numero);
		hash = 29 * hash + Objects.hashCode(this.nom);
		hash = 29 * hash + Objects.hashCode(this.prenom);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Etudiant other = (Etudiant) obj;
		if (!Objects.equals(this.nom, other.nom)) {
			return false;
		}
		if (!Objects.equals(this.prenom, other.prenom)) {
			return false;
		}
		if (!Objects.equals(this.numero, other.numero)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Etudiant{" + "numero=" + numero + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
				+ dateNaissance + '}';
	}
}