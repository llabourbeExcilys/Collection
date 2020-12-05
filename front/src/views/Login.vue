<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col :cols="3">
				<v-form v-model="formValid">
					<v-card>
						<v-card-title>Identifiez-vous</v-card-title>
						<v-card-text>
							<v-text-field
								v-model="user.username"
								:rules="[rules.requiredField]"
								height="40"
								label="Login"
							></v-text-field>
							<v-text-field
								v-model="user.password"
								:append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
								:type="showPassword ? 'text' : 'password'"
								:rules="[rules.requiredField]"
								height="40"
								label="Mot de passe"
								@click:append="showPassword = !showPassword"
							></v-text-field>
							<v-row no-gutters justify="end">
								<router-link :to="{ name: 'Signup' }">Créer un nouveau compte</router-link>
							</v-row>
						</v-card-text>
						<v-card-actions class="d-flex justify-center">
							<!-- <v-progress-circular v-if="loading" indeterminate color="primary"></v-progress-circular> -->
							<v-btn :loading="loading" :disabled="!formValid" color="success" @click="login">
								Valider
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-form>
				<v-alert v-if="message != ''" type="error">
					{{ message }}
				</v-alert>
			</v-col>
		</v-row>
	</v-container>
</template>
<script>
import { isEmpty } from 'lodash';

export default {
	name: 'login',
	components: {},

	data() {
		return {
			loading: false,
			formValid: false,
			message: '',
			rules: {
				requiredField: value => (!isEmpty(value) && !!value) || 'Champs requis'
			},
			showPassword: false,
			user: { username: '', password: '' }
		};
	},
	computed: {
		loggedIn() {
			return this.$store.state.auth.status.loggedIn;
		}
	},
	created() {
		if (this.loggedIn) {
			this.$router.push({ name: 'Serie' });
		}
	},
	methods: {
		login() {
			if (this.user.username && this.user.password) {
				this.loading = true;
				this.$store.dispatch('auth/login', this.user).then(
					data => {
						this.message = data.message;
						this.$router.push({ name: 'Serie' });
					},
					error => {
						this.loading = false;
						this.message =
							(error.response && error.response.data && error.response.data.message) ||
							error.message ||
							error.toString();
					}
				);
			}
		}
	}
};
</script>
