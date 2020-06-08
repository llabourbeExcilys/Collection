import axios from 'axios';

/**
 * Base service.
 * Wraps HTTP library.
 */
const ApiService = {
	get: (resource, config) => {
		return axios
			.get(resource, config)
			.then(response => response.data)
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			);
	},

	post: (resource, data, config) =>
		axios
			.post(resource, data, config)
			.then(response => response.data)
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			),

	postComplete: (resource, data, config) => {
		return axios
			.post(resource, data, config)
			.then(response => response)
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			);
	},
	put: (resource, data, config) =>
		axios
			.put(resource, data, config)
			.then(response => {
				return JSON.parse(response.config.data);
			})
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			),

	putComplete: (resource, data, config) =>
		axios
			.put(resource, data, config)
			.then(response => response)
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			),

	delete: (resource, payload) => {
		let params;
		if (payload !== undefined) {
			params = payload;
		}
		return axios
			.delete(resource, params)
			.then(response => response.data)
			.catch(
				ex =>
					new Promise(() => {
						throw ex.response.data;
					})
			);
	}
};

export default ApiService;
