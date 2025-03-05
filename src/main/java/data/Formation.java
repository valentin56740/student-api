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
 * 
 * 
 * /** POJO Formation
 * 
 * @author valentin56740
 * @date 2025-03-05 16:35:36
 */

@Entity
@Table(name = "formation")
@NamedQueries({ @NamedQuery(name = "Formation.findAll", query = "SELECT f FROM Formation f"),
		@NamedQuery(name = "Formation.findByIdFormation", query = "SELECT f FROM Formation f WHERE f.idFormation = :idFormation"),
		@NamedQuery(name = "Formation.findByNom", query = "SELECT f FROM Formation f WHERE f.nom = :nom"),
		@NamedQuery(name = "Formation.findByAnnee", query = "SELECT f FROM Formation f WHERE f.annee = :annee") })

public class Formation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_formation")
	private Integer idFormation;

	@Column(name = "annee")
	private Integer annee;

	@Column(name = "nom")
	private String nom;

	@JoinTable(name = "contient", joinColumns = {
			@JoinColumn(name = "id_formation", referencedColumnName = "id_formation") }, inverseJoinColumns = {
					@JoinColumn(name = "id_ue", referencedColumnName = "id_ue") })
	@ManyToMany
	private Set<UE> ues;

	/**
	 * Constructeur par défaut
	 */
	public Formation() {
		this.ues = new HashSet<>();
	}

	/**
	 * Constructeur avec paramètres
	 * 
	 * @param idFormation
	 * @param annee
	 * @param nom
	 */
	public Formation(Integer idFormation, Integer annee, String nom) {
		this.idFormation = idFormation;
		this.annee = annee;
		this.nom = nom;
		this.ues = new HashSet<>();
	}

	// Getters
	public Integer getIdFormation() {
		return idFormation;
	}

	public Integer getAnnee() {
		return annee;
	}

	public String getNom() {
		return nom;
	}
	
	public Set<UE> getUes() {
		return ues;
	}

	// Setters
	public void setIdFormation(Integer idFormation) {
		this.idFormation = idFormation;
	}

	public void setAnnee(Integer annee) {
		this.annee = annee;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setUes(Set<UE> ues) {
		this.ues = ues;
	}


	// Méthodes pour gérer les UEs
	public void addUE(UE ue) {
		this.ues.add(ue);
		ue.getFormations().add(this);
	}

	public void removeUE(UE ue) {
		this.ues.remove(ue);
		ue.getFormations().remove(this);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Objects.hashCode(this.idFormation);
		hash = 53 * hash + Objects.hashCode(this.nom);
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
		final Formation other = (Formation) obj;
		if (!Objects.equals(this.nom, other.nom)) {
			return false;
		}
		if (!Objects.equals(this.idFormation, other.idFormation)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Formation{" + "idFormation=" + idFormation + ", annee=" + annee + ", nom=" + nom + '}';
	}
}