import ApiService from '@/services/ApiService';
import authHeader from '@/services/AuthHeader';

const baseUrl = 'http://localhost:9070/api/test';

const UserService = {
	getPublicContent: () => ApiService.get(baseUrl + 'all'),
	getUserBoard: () => ApiService.get(baseUrl + 'user', { headers: authHeader() }),
	getModeratorBoard: () => ApiService.get(baseUrl + 'mod', { headers: authHeader() }),
	getAdminBoard: () => ApiService.get(baseUrl + 'admin', { headers: authHeader() })
};

export default UserService;
