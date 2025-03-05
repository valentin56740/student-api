package data;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import data.Etudiant;
import data.Formation;

/**
 * POJO UE (Unité d'Enseignement)
 * 
 * @author valentin56740
 * @date 2025-03-05
 */

@Entity
@Table(name = "ue")
@NamedQueries({ @NamedQuery(name = "UE.findAll", query = "SELECT u FROM UE u"),
		@NamedQuery(name = "UE.findByIdUe", query = "SELECT u FROM UE u WHERE u.idUe = :idUe"),
		@NamedQuery(name = "UE.findByNom", query = "SELECT u FROM UE u WHERE u.nom = :nom"),
		@NamedQuery(name = "UE.findByObligatoire", query = "SELECT u FROM UE u WHERE u.obligatoire = :obligatoire"),
		@NamedQuery(name = "UE.findByCapacite", query = "SELECT u FROM UE u WHERE u.capacite = :capacite") })

public class UE implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_ue")
	private Integer idUe;

	@Column(name = "nom")
	private String nom;

	@Column(name = "obligatoire")
	private Boolean obligatoire;

	@Column(name = "capacite")
	private Integer capacite;

	@JoinTable(name = "participe", joinColumns = {
			@JoinColumn(name = "id_ue", referencedColumnName = "id_ue") }, inverseJoinColumns = {
					@JoinColumn(name = "numero", referencedColumnName = "numero") })
	@ManyToMany
	private Set<Etudiant> etudiants;

	@JoinTable(name = "contient", joinColumns = {
			@JoinColumn(name = "id_ue", referencedColumnName = "id_ue") }, inverseJoinColumns = {
					@JoinColumn(name = "id_formation", referencedColumnName = "id_formation") })

	@ManyToMany
	private Set<Formation> formations;

	/**
	 * Constructeur par défaut
	 */
	public UE() {
		this.etudiants = new HashSet<>();
		this.formations = new HashSet<>();
	}

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param idUe
	 * @param nom
	 * @param obligatoire
	 * @param capacite
	 */
	public UE(Integer idUe, String nom, Boolean obligatoire, Integer capacite) {
		this.idUe = idUe;
		this.nom = nom;
		this.obligatoire = obligatoire;
		this.capacite = capacite;
		this.etudiants = new HashSet<>();
		this.formations = new HashSet<>();
	}

	// Getters
	public Integer getIdUe() {
		return idUe;
	}

	public String getNom() {
		return nom;
	}

	public Boolean getObligatoire() {
		return obligatoire;
	}

	public Integer getCapacite() {
		return capacite;
	}

	public Set<Etudiant> getParticipations() {
		return etudiants;
	}

	public Set<Formation> getFormations() {
		return formations;
	}

	// Setters
	public void setIdUe(Integer idUe) {
		this.idUe = idUe;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setObligatoire(Boolean obligatoire) {
		this.obligatoire = obligatoire;
	}

	public void setCapacite(Integer capacite) {
		this.capacite = capacite;
	}

	public void setParticipations(Set<Etudiant> participations) {
		this.etudiants = participations;
	}

	public void setFormations(Set<Formation> formations) {
		this.formations = formations;
	}

	// Méthodes pour gérer les participations
	public void addParticipation(Etudiant participation) {
		this.etudiants.add(participation);
	}

	public void removeParticipation(Etudiant participation) {
		this.etudiants.remove(participation);
	}

	// Méthodes pour gérer les formations
	public void addFormation(Formation formation) {
		this.formations.add(formation);
	}

	public void removeFormation(Formation formation) {
		this.formations.remove(formation);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 43 * hash + Objects.hashCode(this.idUe);
		hash = 43 * hash + Objects.hashCode(this.nom);
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
		final UE other = (UE) obj;
		if (!Objects.equals(this.nom, other.nom)) {
			return false;
		}
		if (!Objects.equals(this.idUe, other.idUe)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UE{" + "idUe=" + idUe + ", nom=" + nom + ", obligatoire=" + (obligatoire ? "Oui" : "Non")
				+ ", capacite=" + capacite + '}';
	}
}