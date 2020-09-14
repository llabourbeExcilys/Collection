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
				error =>
					new Promise(() => {
						throw error;
					})
			);
	},

	post: (resource, data, config) =>
		axios
			.post(resource, data, config)
			.then(response => response.data)
			.catch(
				error =>
					new Promise(() => {
						throw error;
					})
			),

	postComplete: (resource, data, config) => {
		return axios
			.post(resource, data, config)
			.then(response => response)
			.catch(
				error =>
					new Promise(() => {
						throw error;
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
				error =>
					new Promise(() => {
						throw error;
					})
			),

	putComplete: (resource, data, config) =>
		axios
			.put(resource, data, config)
			.then(response => response)
			.catch(
				error =>
					new Promise(() => {
						throw error;
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
				error =>
					new Promise(() => {
						throw error;
					})
			);
	}
};

export default ApiService;
