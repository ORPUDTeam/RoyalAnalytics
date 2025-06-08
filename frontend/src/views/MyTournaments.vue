<template>
  <div class="my-tournaments">
    <h1 class="mb-4">Мои турниры</h1>

    <div class="row">
      <div class="col-md-6 mb-4" v-for="tournament in registeredTournaments" :key="tournament.id">
        <card>
          <div class="row align-items-center">
            <div class="col">
              <h4>{{ tournament.name }}</h4>
              <p class="mb-0">
                <small>Начало: {{ formatDate(tournament.start_time) }}</small>
              </p>
            </div>
            <div class="col-auto">
              <base-button
                  @click="$router.push(`/tournaments/${tournament.id}`)"
                  size="sm">
                Подробнее
              </base-button>
            </div>
          </div>
          <div class="mt-3">
            <tournament-progress
                :current="tournament.participants.length"
                :max="tournament.max_capacity" />
          </div>
        </card>
      </div>

      <div v-if="registeredTournaments.length === 0" class="col-12">
        <card class="text-center py-5">
          <i class="ni ni-trophy text-muted" style="font-size: 3rem"></i>
          <h3 class="mt-3">Вы не зарегистрированы ни на один турнир</h3>
          <base-button @click="$router.push('/tournaments')" type="primary" class="mt-3">
            Найти турниры
          </base-button>
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchRegisteredTournaments } from '@/api/tournaments';
import TournamentProgress from '@/components/Tournaments/Progress';

export default {
  components: { TournamentProgress },
  data() {
    return {
      registeredTournaments: []
    }
  },
  async mounted() {
    await this.loadRegisteredTournaments();
  },
  methods: {
    async loadRegisteredTournaments() {
      try {
        this.registeredTournaments = await fetchRegisteredTournaments();
      } catch (error) {
        console.error('Ошибка загрузки турниров:', error);
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    }
  }
}
</script>