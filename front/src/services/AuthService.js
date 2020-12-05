import ApiService from '@/services/ApiService';

const baseUrl = 'http://localhost:9070/api/auth';

const AuthService = {
	login: user =>
		ApiService.post(baseUrl + '/signin', { username: user.username, password: user.password }).then(
			response => {
				if (response.accessToken) {
					localStorage.setItem('user', JSON.stringify(response));
				}
				return response;
			}
		),

	logout: () => {
		localStorage.removeItem('user');
	},

	register: user =>
		ApiService.postComplete(baseUrl + '/signup', {
			username: user.username,
			email: user.email,
			password: user.password
		})
};

export default AuthService;
