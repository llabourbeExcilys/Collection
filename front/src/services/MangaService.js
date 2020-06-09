import ApiService from '@/services/ApiService';
import axios from 'axios';

const baseUrl = 'http://localhost:3000';

const MangaService = {
	getSeries: () => ApiService.get(baseUrl + '/series'),
	getGenres: () => ApiService.get(baseUrl + '/genres'),
	getTypes: () => ApiService.get(baseUrl + '/types'),
	getAuthors: () => ApiService.get(baseUrl + '/authors'),
	getEditors: () => ApiService.get(baseUrl + '/editors'),

	updateSerie: serie => ApiService.put(baseUrl + '/series/' + serie.id, serie)
};

export default MangaService;
