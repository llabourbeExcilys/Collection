import ApiService from '@/services/ApiService';

const baseUrl = 'http://localhost:3000';

const MangaService = {
	addSerie: serie => ApiService.post(baseUrl + '/series', serie),

	getSeries: () => ApiService.get(baseUrl + '/series'),
	getGenres: () => ApiService.get(baseUrl + '/genres'),
	getTypes: () => ApiService.get(baseUrl + '/types'),
	getAuthors: () => ApiService.get(baseUrl + '/authors'),
	getEditors: () => ApiService.get(baseUrl + '/editors'),

	updateSerie: serie => ApiService.put(baseUrl + '/series/' + serie.id, serie),

	removeSerie: id => ApiService.delete(baseUrl + '/series/' + id)
};

export default MangaService;
