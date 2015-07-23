//console.log('entrees');
/* jshint multistr:true */

function renderSearchResults(results){
	document.getElementById('search-results').innerHTML = results.map (function(result) {
		var entree = result._source;
		//entree.save(flush:true);
		return '\
		<div class="media">\
		<div class="media-left">\
     <h3 class="media-heading"><u>'+ entree.nomFournisseur +'</u></h3>\
     <h5 class="media-heading">'+ entree.objet +'</h5>\
     <h5 class="media-heading">'+ entree.montant +'</h5>\
     <h5 class="media-heading">'+ entree.service +'</h5>\
     <h5 class="media-heading">'+ entree.dateSignature +'</h5>\
     <h5 class="media-heading">'+ entree.noDossier +'</h5>\
     <h5 class="media-heading">'+ entree.direction +'</h5>\
     <h5 class="media-heading">'+ entree.noDecision +'</h5>\
     <h5 class="media-heading">'+ entree.repartition +'</h5>\
     <hr>\
				</div>\
			</div>\
		';
	}).join('');
}

function doSearch(query) {
	var xhr = new XMLHttpRequest();
	xhr.open('get', 'search?q=' + query, true);
	xhr.send();
	xhr.onreadystatechange = function(){
		if (xhr.readyState == 4 && xhr.status == 200){
			var data = JSON.parse(xhr.responseText).hits.hits;
			console.log(data);

			renderSearchResults(data);
		}
	};
}
function bindSearchForm() {
	var form = document.getElementById('search-form');
	form.addEventListener('submit', function(e){
		e.preventDefault();
		var query = document.getElementById('search-input').value;
		doSearch(query);
    });
}
document.addEventListener('DOMContentLoaded', function(e) {
	e.preventDefault();
	bindSearchForm();

});