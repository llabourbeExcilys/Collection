import ApiService from '@/services/ApiService';

const baseUrl = 'http://localhost:9080';

const MangaService = {
	addSerie: serie => ApiService.post(baseUrl + '/series', serie),

	getGenres: () => ApiService.get(baseUrl + '/genre'),
	getTypes: () => ApiService.get(baseUrl + '/book/type'),
	getAuthors: () => ApiService.get(baseUrl + '/author'),
	getEditors: () => ApiService.get(baseUrl + '/editor'),

	getSeries: () => ApiService.get(baseUrl + '/serie'),
	updateSerie: serie => ApiService.put(baseUrl + '/serie/' + serie.id, serie),
	removeSerie: id => ApiService.delete(baseUrl + '/serie/' + id)
};

export default MangaService;
