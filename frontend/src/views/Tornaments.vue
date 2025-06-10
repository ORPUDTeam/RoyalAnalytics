<template>
  <div class="tournaments-page">
    <!-- Фильтры -->
    <card class="mb-4">
      <div class="row">
        <div class="col-md-3">
          <base-select
              label="Статус"
              :options="statusOptions"
              v-model="filters.status">
          </base-select>
        </div>
        <div class="col-md-3">
          <base-input
              label="Дата начала (от)"
              type="date"
              v-model="filters.startDate">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input
              label="Минимальные награды"
              type="number"
              v-model="filters.minReward">
          </base-input>
        </div>
        <div class="col-md-3 d-flex align-items-end">
          <base-button @click="applyFilters" type="primary">
            Применить
          </base-button>
        </div>
      </div>
    </card>

    <!-- Список турниров -->
    <div class="row">
      <div class="col-md-6 mb-4" v-for="tournament in filteredTournaments" :key="tournament.id">
        <tournament-card
            :tournament="tournament"
            :registered="isRegistered(tournament.id)"
            @register="handleRegister(tournament.id)"
            @unregister="handleUnregister(tournament.id)">
        </tournament-card>
      </div>
    </div>

    <!-- Пагинация -->
    <div class="row mt-4">
      <div class="col">
        <base-pagination
            v-model="currentPage"
            :per-page="perPage"
            :total="totalTournaments">
        </base-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { fetchTournaments, registerForTournament } from '@/api/tournaments';
import TournamentCard from '@/components/Tournaments/Card';

export default {
  components: { TournamentCard },
  data() {
    return {
      tournaments: [],
      filters: {
        status: null,
        startDate: null,
        minReward: null
      },
      statusOptions: [
        { value: null, label: 'Все' },
        { value: 'upcoming', label: 'Предстоящие' },
        { value: 'active', label: 'Активные' },
        { value: 'completed', label: 'Завершенные' }
      ],
      currentPage: 1,
      perPage: 10,
      registeredTournaments: []
    }
  },
  async mounted() {
    await this.loadTournaments();
    this.loadRegisteredTournaments();
  },
  computed: {
    filteredTournaments() {
      return this.tournaments.filter(t => {
        const matchesStatus = this.checkStatus(t);
        const matchesDate = this.checkDate(t);
        const matchesReward = this.checkReward(t);
        return matchesStatus && matchesDate && matchesReward;
      }).slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
    },
    totalTournaments() {
      return this.tournaments.length;
    }
  },
  methods: {
    async loadTournaments() {
      try {
        this.tournaments = await fetchTournaments();
      } catch (error) {
        console.error('Ошибка загрузки турниров:', error);
      }
    },
    loadRegisteredTournaments() {
      this.registeredTournaments = JSON.parse(localStorage.getItem('registered_tournaments') || '[]');
    },
    checkStatus(tournament) {
      if (!this.filters.status) return true;
      const now = new Date();
      const start = new Date(tournament.start_time);
      const end = new Date(tournament.end_time);

      if (this.filters.status === 'upcoming') return start > now;
      if (this.filters.status === 'active') return start <= now && end >= now;
      if (this.filters.status === 'completed') return end < now;
      return true;
    },
    checkDate(tournament) {
      if (!this.filters.startDate) return true;
      return new Date(tournament.start_time) >= new Date(this.filters.startDate);
    },
    checkReward(tournament) {
      if (!this.filters.minReward) return true;
      return tournament.rewards.card_prize >= this.filters.minReward;
    },
    isRegistered(tournamentId) {
      return this.registeredTournaments.includes(tournamentId);
    },
    async handleRegister(tournamentId) {
      try {
        await registerForTournament(tournamentId);
        this.registeredTournaments.push(tournamentId);
        localStorage.setItem('registered_tournaments', JSON.stringify(this.registeredTournaments));
        this.$notify({
          type: 'success',
          message: 'Вы успешно зарегистрированы!'
        });
      } catch (error) {
        this.$notify({
          type: 'danger',
          message: 'Ошибка регистрации'
        });
      }
    },
    async handleUnregister(tournamentId) {
      try {
        await this.$axios.delete(`/tournaments/${tournamentId}/unregister`);
        this.registeredTournaments = this.registeredTournaments.filter(id => id !== tournamentId);
        localStorage.setItem('registered_tournaments', JSON.stringify(this.registeredTournaments));
        this.$notify({
          type: 'success',
          message: 'Регистрация отменена'
        });
      } catch (error) {
        this.$notify({
          type: 'danger',
          message: 'Ошибка отмены регистрации'
        });
      }
    },
    applyFilters() {
      this.currentPage = 1; // Сброс пагинации при изменении фильтров
    }
  }
}
</script>