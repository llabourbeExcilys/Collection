<template>
	<div>
		<v-container>
			<v-row align="center" justify="center">
				<v-col :cols="8">
					<v-data-iterator
						hide-default-footer
						row
						wrap
						:items-per-page="5"
						:items="mangaSeries"
						:search="search"
					>
						<template v-slot:header>
							<v-card>
								<v-toolbar dark color="blue darken-3" :extended="showFilters">
									<v-container>
										<v-row align="center">
											<v-col cols="8">
												<v-text-field
													v-model="search"
													clearable
													flat
													solo-inverted
													hide-details
													append-icon="mdi-magnify"
													label="Chercher une oeuvre, un éditeur, un genre..."
												/>
											</v-col>
											<v-spacer />
											<v-col cols="2" align="right">
												<v-btn
													large
													depressed
													color="blue"
													@click="showFilters = !showFilters"
													>Filtrer +</v-btn
												>
											</v-col>
										</v-row>
										<v-row>
											<!-- <v-col :cols="5">
												<v-expand-transition>
													<div v-show="showFilters">this is filters</div>
												</v-expand-transition>
											</v-col> -->
										</v-row>
									</v-container>
								</v-toolbar>
							</v-card>
						</template>

						<template v-slot:default="props">
							<v-row>
								<v-col v-for="item in props.items" :key="item.title" cols="12">
									<MangaSerie :item="item" />
								</v-col>
							</v-row>
						</template>
						<template v-slot:footer="{ options, pagination, updateOptions }">
							<v-data-footer
								:class="'d-flex justify-center'"
								:options="options"
								:pagination="pagination"
								@update:options="updateOptions"
							>
							</v-data-footer>
						</template> </v-data-iterator
				></v-col>
			</v-row>
		</v-container>
	</div>
</template>

<script>
import MangaSerie from '@/components/MangaSerie';

export default {
	name: 'manage',
	components: {
		MangaSerie
	},
	data: function() {
		return {
			books: [],
			mangaSeries: [
				{
					title: '20th Century Boys',
					edition: 'Deluxe',
					editeur: 'Panini manga',
					owned: 11,
					published: 11,
					type: 'Manga',
					genre: 'science fiction'
				},
				{
					title: 'Akira',
					edition: 'Edition définitive',
					editeur: 'GLENAT MANGA',
					owned: 6,
					published: 6,
					type: 'Manga',
					genre: 'Science Fiction'
				},
				{
					title: 'Amer Beton',
					edition: 'Intégrale',
					editeur: 'TONKAM',
					owned: 1,
					published: 1,
					type: 'Manga',
					genre: 'drame'
				},
				{
					title: 'Berserk',
					edition: 'RÉÉDITION FRANÇAISE',
					editeur: 'GLENAT MANGA',
					owned: 40,
					published: 40,
					type: 'Manga',
					genre: 'Fantasy'
				},
				{
					title: 'Jojo',
					edition: 'Parties 1 -5',
					editeur: 'TONKAM',
					owned: 68,
					published: 68,
					type: 'Manga',
					genre: 'Aventure'
				},
				{
					title: 'Vinland Saga',
					edition: 'Simple',
					editeur: 'KUROKAWA',
					owned: 16,
					published: 23,
					type: 'Manga',
					genre: 'Action'
				}
			],
			search: '',
			showFilters: false
		};
	},
	methods: {}
};
</script>
<style scoped>
#canvas {
	border: 1px solid black;
	display: inline-block;
	/* position: static; 
    top: 100px;
    left: 100px; */
}

/* .v-data-footer {
	display: flex;
	justify-content: center;
} */
</style>
