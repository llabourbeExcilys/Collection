import ApiService from '@/services/ApiService';
import authHeader from '@/services/AuthHeader';

const baseUrl = 'http://localhost:9070';

const MangaService = {
	getGenres: () => ApiService.get(baseUrl + '/genre', { headers: authHeader() }),
	addGenre: genre => ApiService.post(baseUrl + '/genre', genre, { headers: authHeader() }),
	updateGenre: genre => ApiService.put(baseUrl + '/genre/' + genre.id, genre, { headers: authHeader() }),
	deleteGenre: genre => ApiService.delete(baseUrl + '/genre/' + genre.id, { headers: authHeader() }),

	getTypes: () => ApiService.get(baseUrl + '/book/type', { headers: authHeader() }),
	getEditors: () => ApiService.get(baseUrl + '/editor', { headers: authHeader() }),

	getAuthors: () => ApiService.get(baseUrl + '/author', { headers: authHeader() }),
	addAuthor: author => ApiService.post(baseUrl + '/author', author, { headers: authHeader() }),
	updateAuthor: author => ApiService.put(baseUrl + '/author/' + author.id, author, { headers: authHeader() }),
	deleteAuthor: author => ApiService.delete(baseUrl + '/author/' + author.id, { headers: authHeader() }),

	getSeries: () => ApiService.get(baseUrl + '/serie', { headers: authHeader() }),
	addSerie: serie => ApiService.post(baseUrl + '/serie', serie, { headers: authHeader() }),
	updateSerie: serie => ApiService.put(baseUrl + '/serie/' + serie.id, serie, { headers: authHeader() }),
	deleteSerie: id => ApiService.delete(baseUrl + '/serie/' + id, { headers: authHeader() })
};

export default MangaService;
