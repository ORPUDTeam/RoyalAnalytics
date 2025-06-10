<template>
  <div class="player-profile">
    <template v-if="loading">
      <loader />
    </template>

    <template v-else>
      <!-- 1. Заголовок -->
      <div class="row align-items-center mb-4">
        <div class="col-md-8">
          <h1>{{ player.name }}</h1>
          <badge v-if="player.clan" type="default">
            <i class="ni ni-square-pin"></i> {{ player.clan.name }}
          </badge>
        </div>
        <div class="col-md-4 text-right">
          <base-button v-if="isRegistered" size="sm" @click="addToCompare">
            <i class="ni ni-chart-bar-32"></i> Сравнить
          </base-button>
        </div>
      </div>

      <!-- 2. Основная информация -->
      <card>
        <div class="row">
          <div class="col-md-6">
            <h4>Основное</h4>
            <table class="table table-sm">
              <tr>
                <td><strong>Кубки:</strong></td>
                <td>{{ player.trophies }}</td>
              </tr>
              <tr v-if="isRegistered">
                <td><strong>Уровень:</strong></td>
                <td>{{ player.level }}</td>
              </tr>
              <tr v-if="isRegistered">
                <td><strong>Дата регистрации:</strong></td>
                <td>{{ formatDate(player.registered_at) }}</td>
              </tr>
            </table>
          </div>
          <div class="col-md-6">
            <h4>Статистика</h4>
            <table class="table table-sm">
              <tr>
                <td><strong>Побед:</strong></td>
                <td>{{ player.wins }}</td>
              </tr>
              <tr>
                <td><strong>Лучший сезон:</strong></td>
                <td>{{ player.best_season }}</td>
              </tr>
            </table>
          </div>
        </div>
      </card>

      <!-- 3. Колода (разный формат для зарегистрированных) -->
      <card class="mt-4">
        <h3 slot="header">Текущая колода</h3>
        <template v-if="isRegistered">
          <deck-preview :cards="player.current_deck" />
          <div class="mt-3 text-muted">
            <small>Обновлено: {{ formatDateTime(player.cache_updated_at) }}</small>
          </div>
        </template>
        <template v-else>
          <div class="alert alert-info">
            Данные обновляются в реальном времени (без кеширования)
          </div>
          <deck-preview :cards="player.current_deck" :show-elixir="true" />
        </template>
      </card>

      <!-- 4. История трофеев (только для зарегистрированных) -->
      <card v-if="isRegistered" class="mt-4">
        <h3 slot="header">История трофеев</h3>
        <trophy-chart :data="player.rating_history" />
      </card>
    </template>
  </div>
</template>

<script>
import { fetchPlayerProfile } from '@/api/players';
import { checkIfRegistered } from '@/api/auth';

export default {
  data() {
    return {
      player: null,
      loading: true,
      isRegistered: false
    }
  },
  async mounted() {
    await this.loadPlayerData(this.$route.params.tag);
  },
  methods: {
    async loadPlayerData(tag) {
      try {
        this.loading = true;

        const [profile, registrationStatus] = await Promise.all([
          fetchPlayerProfile(tag),
          checkIfRegistered(tag)
        ]);

        this.player = profile;
        this.isRegistered = registrationStatus.is_registered;

      } catch (error) {
        console.error('Ошибка загрузки:', error);
      } finally {
        this.loading = false;
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU');
    },
    formatDateTime(datetime) {
      return new Date(datetime).toLocaleString('ru-RU');
    },
    addToCompare() {
      // Логика сравнения игроков
    }
  }
}
</script>