package com.iit.dtos;

public class NoteDTO {

	   private Long id;         // ID de la note
	    private Double valeur;   // valeur de la note
	    private Long coursId;    // ID du cours
	    private String coursNom; // nom du cours
	    private Long etudiantId; // ID de l'étudiant

	    public NoteDTO() {}

		public NoteDTO(Double valeur, Long coursId, String coursNom, Long etudiantId) {
			super();
			this.valeur = valeur;
			this.coursId = coursId;
			this.coursNom = coursNom;
			this.etudiantId = etudiantId;
		}
		
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Double getValeur() {
			return valeur;
		}

		public void setValeur(Double valeur) {
			this.valeur = valeur;
		}

		public Long getCoursId() {
			return coursId;
		}

		public void setCoursId(Long coursId) {
			this.coursId = coursId;
		}

		public String getCoursNom() {
			return coursNom;
		}

		public void setCoursNom(String coursNom) {
			this.coursNom = coursNom;
		}

		public Long getEtudiantId() {
			return etudiantId;
		}

		public void setEtudiantId(Long etudiantId) {
			this.etudiantId = etudiantId;
		}

   
}
