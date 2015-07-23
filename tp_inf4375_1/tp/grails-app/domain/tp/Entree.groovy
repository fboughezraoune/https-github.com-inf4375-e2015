package tp

class Entree {
	 String nomFournisseur
	 String repartition
	 String service
	 String direction
	 String noDossier
	 String objet
	 String noDecision
	 String dateSignature
	 String montant

	 static mapping = {
	 	repartition type:'text'
	  	service type:'text'
	  	direction type:'text'
	  	noDossier type:'text'
	  	objet type:'text'
	  	noDecision type:'text'
	  	dateSignature type:'text'
    }
	    

    static constraints = {
    }
}
