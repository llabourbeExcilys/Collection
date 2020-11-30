import ApiService from '@/services/ApiService';

const baseUrl = 'http://localhost:9070';

const MangaService = {
	getGenres: () => ApiService.get(baseUrl + '/genre'),
	addGenre: genre => ApiService.post(baseUrl + '/genre', genre),
	updateGenre: genre => ApiService.put(baseUrl + '/genre/' + genre.id, genre),
	deleteGenre: genre => ApiService.delete(baseUrl + '/genre/' + genre.id),

	getTypes: () => ApiService.get(baseUrl + '/book/type'),
	getEditors: () => ApiService.get(baseUrl + '/editor'),

	getAuthors: () => ApiService.get(baseUrl + '/author'),
	addAuthor: author => ApiService.post(baseUrl + '/author', author),
	updateAuthor: author => ApiService.put(baseUrl + '/author/' + author.id, author),
	deleteAuthor: author => ApiService.delete(baseUrl + '/author/' + author.id),

	getSeries: () => ApiService.get(baseUrl + '/serie'),
	addSerie: serie => ApiService.post(baseUrl + '/serie', serie),
	updateSerie: serie => ApiService.put(baseUrl + '/serie/' + serie.id, serie),
	deleteSerie: id => ApiService.delete(baseUrl + '/serie/' + id)
};

export default MangaService;
