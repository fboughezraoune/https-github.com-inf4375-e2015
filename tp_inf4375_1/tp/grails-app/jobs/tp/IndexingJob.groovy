package tp
import org.apache.http.client.fluent.*
import org.apache.http.entity.*
import groovy.json.*
import java.io.DataInputStream
import java.net.MalformedURLException
import java.net.URL
import java.io.*



class IndexingJob {
    static triggers = {
      cron name:'cronTrigger', cronExpression: '0 0/30 * * * ?' //comment faire 30mn

    }

    def execute() {
        deleteIndex() //Supprimer l'index d'elasticSearch s'il existe
        createIndex() //Creer un nouveau index d'elasticSearch
        URL u 
        ArrayList tab = new ArrayList()
        u = new URL("http://donnees.ville.montreal.qc.ca/dataset/505f2f9e-8cec-43f9-a83a-465717ef73a5/resource/87a6e535-3a6e-4964-91f5-836cd31099f7/download/contratscomiteexecutif.csv" )
        BufferedReader dis =  new BufferedReader(new InputStreamReader(u.openStream()))
        String ligne = dis.readLine();
        ligne = dis.readLine();
        while(ligne != null){
        ligne =ligne.replaceFirst(",","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",","#");
        ligne =ligne.replaceFirst(",","#");
        tab = ligne.split("#");
if (tab.size() == 9){

            Entree e = new Entree(nomFournisseur: tab[0], 
                repartition: tab[1], 
                service: tab[2], 
                direction: tab[3], 
                noDossier: tab[4], 
                objet: tab[5],
                noDecision: tab[6], 
                dateSignature: tab[7],
                montant: tab[8])
            indexEntree(e)//pour chaque 'entree' on va l'indexer, ie: l'envoyer a l'elasticSearhc
            e.save(flush:true)
             }
                    ligne = dis.readLine();

           } 



        u = new URL("http://donnees.ville.montreal.qc.ca/dataset/6df93670-af44-492e-a644-72643bf58bc0/resource/a6869244-1a4d-4080-9577-b73e09d95ed5/download/contratsconseilmunicipal.csv" )
        //dis = new DataInputStream(u.openStream()) 
         dis =  new BufferedReader(new InputStreamReader(u.openStream()))

         ligne = dis.readLine();
        ligne = dis.readLine();
        while(ligne != null){
        ligne =ligne.replaceFirst(",","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",","#");
        ligne =ligne.replaceFirst(",\"","#");
        tab = ligne.split("#");
        if (tab.size() == 9){
            Entree e = new Entree(nomFournisseur: tab[0], 
                repartition: tab[1], 
                service: tab[2], 
                direction: tab[3], 
                noDossier: tab[4], 
                objet: tab[5],
                noDecision: tab[6], 
                dateSignature: tab[7],
                montant: tab[8])
            indexEntree(e)//pour chaque 'entree' on va l'indexer, ie: l'envoyer a l'elasticSearhc
            e.save(flush:true)
             }
            ligne = dis.readLine();

           } 
u = new URL("http://donnees.ville.montreal.qc.ca/dataset/6df93670-af44-492e-a644-72643bf58bc0/resource/35e636c1-9f99-4adf-8898-67c2ea4f8c47/download/contratsconseilagglomeration.csv" )
         dis =  new BufferedReader(new InputStreamReader(u.openStream()))

         ligne = dis.readLine();
        ligne = dis.readLine();
        //System.out.println("\"\"");
        while(ligne != null){
        ligne =ligne.replaceFirst(",","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",\"","#");
        ligne =ligne.replaceFirst("\",","#");
        ligne =ligne.replaceFirst(",\"","#");


        tab = ligne.split("#");
        if (tab.size() == 9){

            Entree e = new Entree(nomFournisseur: tab[0], 
                repartition: tab[1], 
                service: tab[2], 
                direction: tab[3], 
                noDossier: tab[4], 
                objet: tab[5],
                noDecision: tab[6], 
                dateSignature: tab[7],
                montant: tab[8])
            indexEntree(e)//pour chaque 'entree' on va l'indexer, ie: l'envoyer a l'elasticSearhc
            e.save(flush:true)
             }
                    ligne = dis.readLine();

           } 
    }
       
    def deleteIndex(){
    	Request.Delete("http://localhost:9200/tp").execute().discardContent()
    }                  

    def createIndex(){
    	Request.Post("http://localhost:9200/tp").execute().discardContent()
    }

    def indexEntree(entree){
    	Request.Post("http://localhost:9200/tp/entree")
    	.bodyString(new JsonBuilder([nomFournisseur: entree.nomFournisseur,
		    repartition: entree.repartition,
		    service: entree.service,
		    direction: entree.direction,
		    noDossier: entree.noDossier,
		    objet: entree.objet,
		    noDecision: entree.noDecision,
		    dateSignature: entree.dateSignature,
		    montant: entree.montant]).toString(), ContentType.APPLICATION_JSON).execute().returnContent().asString()

    }

    def indexEntree2(fonctionnaire){
        Request.Post("http://localhost:9200/tp/fonctionnaire")
        .bodyString(new JsonBuilder([nomFournisseur: fonctionnaire.nomFournisseur,
            numero: fonctionnaire.numero,
            dateApprobation: fonctionnaire.dateApprobation,
            approbateur: fonctionnaire.approbateur,
            description: fonctionnaire.description,
            service: fonctionnaire.service,
            activite: fonctionnaire.activite,
            montant: fonctionnaire.montant,
            portion: fonctionnaire.portion]).toString(), ContentType.APPLICATION_JSON).execute().returnContent().asString()

    }

}