<template>
	<v-container>
		<v-row align="center" justify="center">
			<v-col :cols="3">
				<v-form v-model="formValid">
					<v-card>
						<v-card-title>Créer un nouveau compte</v-card-title>
						<v-card-text>
							<v-text-field
								v-model="user.username"
								:rules="[rules.requiredField]"
								height="40"
								label="Login"
							></v-text-field>
							<v-text-field
								v-model="user.email"
								:rules="[rules.requiredField]"
								height="40"
								label="Email"
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
								<router-link :to="{ name: 'Login' }">Compte déjà existant</router-link></v-row
							>
						</v-card-text>
						<v-card-actions class="d-flex justify-center">
							<v-btn :loading="loading" :disabled="!formValid" color="success" @click="register">
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
	name: 'signup',
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
			user: { username: '', email: '', password: '' }
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
		register() {
			if (this.user.username && this.user.email && this.user.password) {
				this.loading = true;
				this.$store.dispatch('auth/register', this.user).then(
					data => {
						this.message = data.message;
						this.$router.push({ name: 'Serie' });
					},
					error => {
						this.loading = false;
						this.message =
							(error.response && error.response.data && error.response.data.message) ||
							error.response.data.message ||
							error.toString();
					}
				);
			}
		}
	}
};
</script>
