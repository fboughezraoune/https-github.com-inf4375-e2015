package tp
import org.apache.http.client.fluent.*
import org.apache.http.client.utils.*

class EntreesController {

	def index(){
		//render(Entree.list() as grails.converters.JSON)
    }

    def search(){
    	def searchResult = Request.Get(new URIBuilder("http://localhost:9200/tp/_search")
		    .addParameter('q', params.q ?: '*').build())
    	.execute().returnContent().asString()

    	render(text:searchResult, contentType: 'application/json', encoding: 'utf-8')
	}
}







