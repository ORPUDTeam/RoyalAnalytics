<template>
  <div class="tournament-detail">
    <template v-if="loading">
      <loader />
    </template>

    <template v-else>
      <!-- Основная информация -->
      <card>
        <div class="row">
          <div class="col-md-8">
            <h2>{{ tournament.name }}</h2>
            <p class="lead">{{ tournament.description }}</p>

            <div class="tournament-meta">
              <div class="meta-item">
                <i class="ni ni-calendar-grid-58"></i>
                <span>Начало: {{ formatDate(tournament.start_time) }}</span>
              </div>
              <div class="meta-item">
                <i class="ni ni-trophy"></i>
                <span>Награды: {{ formatRewards(tournament.rewards) }}</span>
              </div>
              <div class="meta-item">
                <i class="ni ni-single-02"></i>
                <span>Участников: {{ tournament.participants.length }}/{{ tournament.max_capacity }}</span>
              </div>
            </div>
          </div>
          <div class="col-md-4 text-right">
            <tournament-status-badge :status="tournament.status" />
            <base-button
                v-if="!isRegistered && tournament.status === 'upcoming'"
                @click="register"
                type="primary"
                class="mt-3">
              Зарегистрироваться
            </base-button>
            <base-button
                v-else-if="isRegistered"
                @click="unregister"
                type="danger"
                class="mt-3">
              Отменить регистрацию
            </base-button>
          </div>
        </div>
      </card>

      <!-- Участники -->
      <card class="mt-4">
        <h3 slot="header">Участники</h3>
        <div class="participants-grid">
          <div v-for="participant in tournament.participants" :key="participant.tag" class="participant-card">
            <img :src="participant.avatar" class="avatar">
            <div class="participant-info">
              <h5>{{ participant.name }}</h5>
              <p class="text-muted">{{ participant.tag }}</p>
              <badge type="default">{{ participant.trophies }} кубков</badge>
            </div>
          </div>
          <div v-if="tournament.participants.length === 0" class="empty-participants">
            <i class="ni ni-single-02"></i>
            <p>Пока нет участников</p>
          </div>
        </div>
      </card>

      <!-- Расписание -->
      <card class="mt-4" v-if="tournament.schedule">
        <h3 slot="header">Расписание</h3>
        <div class="schedule">
          <div v-for="(stage, idx) in tournament.schedule" :key="idx" class="schedule-stage">
            <h5>{{ stage.name }}</h5>
            <p>{{ formatDateTime(stage.start_time) }} - {{ formatDateTime(stage.end_time) }}</p>
          </div>
        </div>
      </card>
    </template>
  </div>
</template>

<script>
import { fetchTournamentDetails } from '@/api/tournaments';
import TournamentStatusBadge from '@/components/Tournaments/StatusBadge';

export default {
  components: { TournamentStatusBadge },
  data() {
    return {
      tournament: null,
      loading: true,
      isRegistered: false
    }
  },
  async mounted() {
    await this.loadTournament(this.$route.params.id);
  },
  methods: {
    async loadTournament(id) {
      try {
        this.loading = true;
        this.tournament = await fetchTournamentDetails(id);
        this.checkRegistration();
      } catch (error) {
        console.error('Ошибка загрузки турнира:', error);
      } finally {
        this.loading = false;
      }
    },
    checkRegistration() {
      const registered = JSON.parse(localStorage.getItem('registered_tournaments') || '[]');
      this.isRegistered = registered.includes(this.tournament.id);
    },
    async register() {
      try {
        await this.$axios.post(`/tournaments/${this.tournament.id}/register`, {
          playerTag: this.$store.state.user.player_tag
        });
        this.isRegistered = true;
        this.tournament.participants.push({
          name: this.$store.state.user.username,
          tag: this.$store.state.user.player_tag,
          avatar: this.$store.state.user.avatarUrl,
          trophies: this.$store.state.profile.trophies
        });
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
    async unregister() {
      try {
        await this.$axios.delete(`/tournaments/${this.tournament.id}/unregister`, {
          params: { playerTag: this.$store.state.user.player_tag }
        });
        this.isRegistered = false;
        this.tournament.participants = this.tournament.participants.filter(
            p => p.tag !== this.$store.state.user.player_tag
        );
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
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    },
    formatDateTime(datetime) {
      return new Date(datetime).toLocaleString('ru-RU');
    },
    formatRewards(rewards) {
      return rewards.card_prize ? `${rewards.card_prize} карт` : 'Нет наград';
    }
  }
}
</script>

<style scoped>
.tournament-meta {
  display: flex;
  gap: 1.5rem;
  margin-top: 1rem;
}
.meta-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.participants-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 1rem;
}
.participant-card {
  display: flex;
  align-items: center;
  padding: 0.5rem;
  border-radius: 8px;
  background: #f8f9fe;
}
.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 1rem;
}
.empty-participants {
  text-align: center;
  padding: 2rem;
  color: #adb5bd;
}
.schedule-stage {
  padding: 1rem;
  border-bottom: 1px solid #eee;
}
.schedule-stage:last-child {
  border-bottom: none;
}
</style>