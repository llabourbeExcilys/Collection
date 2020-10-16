import ApiService from '@/services/ApiService';

const baseUrl = 'http://localhost:9070';

const MangaService = {
	getGenres: () => ApiService.get(baseUrl + '/genre'),
	getTypes: () => ApiService.get(baseUrl + '/book/type'),
	getAuthors: () => ApiService.get(baseUrl + '/author'),
	getEditors: () => ApiService.get(baseUrl + '/editor'),
	getSeries: () => ApiService.get(baseUrl + '/serie'),

	addSerie: serie => ApiService.post(baseUrl + '/serie', serie),
	updateSerie: serie => ApiService.put(baseUrl + '/serie/' + serie.id, serie),
	removeSerie: id => ApiService.delete(baseUrl + '/serie/' + id)
};

export default MangaService;
