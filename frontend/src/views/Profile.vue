<template>
  <div class="profile">
    <!-- 1. Основная информация -->
    <card>
      <div class="row align-items-center">
        <div class="col-md-3 text-center">
          <img :src="user.avatarUrl || defaultAvatar" class="rounded-circle" width="150">
        </div>
        <div class="col-md-9">
          <h2>{{ user.username }}</h2>
          <p class="text-muted">
            <i class="ni ni-calendar-grid-58"></i> Зарегистрирован: {{ formatDate(user.registered_at) }}
          </p>
          <badge type="primary">
            <i class="ni ni-tag"></i> {{ user.player_tag }}
          </badge>
        </div>
      </div>
    </card>

    <!-- 2. Статистика -->
    <div class="row mt-4">
      <div class="col-md-6">
        <card>
          <h3 slot="header">Кубки</h3>
          <div class="text-center py-4">
            <h1 class="display-3">{{ stats.trophies }}</h1>
            <trend-indicator :value="stats.trophy_change_7d" />
            <p class="text-muted">за 7 дней: {{ Math.abs(stats.trophy_change_7d) }}</p>
          </div>
        </card>
      </div>
      <div class="col-md-6">
        <card>
          <h3 slot="header">Награды</h3>
          <div class="rewards-grid">
            <div v-for="(reward, idx) in stats.rewards.slice(0, 6)" :key="idx" class="reward-item">
              <img :src="rewardIcons[reward.type]" width="40">
              <small>{{ reward.name }}</small>
            </div>
          </div>
          <router-link to="/rewards" class="btn btn-link btn-sm">
            Все награды ({{ stats.rewards.length }})
          </router-link>
        </card>
      </div>
    </div>

    <!-- 3. Текущая колода -->
    <card class="mt-4">
      <div slot="header" class="row align-items-center">
        <div class="col">
          <h3 class="mb-0">Текущая колода</h3>
        </div>
        <div class="col text-right">
          <base-button size="sm" @click="$router.push('/decks')">
            Сменить колоду
          </base-button>
        </div>
      </div>
      <deck-preview :cards="currentDeck" />
    </card>

    <!-- 4. История трофеев -->
    <card class="mt-4">
      <h3 slot="header">История трофеев</h3>
      <trophy-chart :data="ratingHistory" />
    </card>
  </div>
</template>

<script>
import { fetchUserProfile, fetchRatingHistory } from '@/api/profile';
import { trophyIcons, rewardIcons } from '@/utils/icons';

export default {
  data() {
    return {
      user: {
        username: '',
        player_tag: '',
        registered_at: '',
        avatarUrl: ''
      },
      stats: {
        trophies: 0,
        trophy_change_7d: 0,
        rewards: []
      },
      currentDeck: [],
      ratingHistory: [],
      rewardIcons,
      defaultAvatar: require('@/assets/img/default-avatar.png')
    }
  },
  async mounted() {
    await this.loadData();
  },
  methods: {
    async loadData() {
      try {
        const [profile, history] = await Promise.all([
          fetchUserProfile(),
          fetchRatingHistory()
        ]);

        this.user = {
          username: profile.name,
          player_tag: profile.player_tag,
          registered_at: profile.registered_at,
          avatarUrl: profile.avatar_url
        };

        this.stats = {
          trophies: profile.trophies,
          trophy_change_7d: profile.trophy_change_7d,
          rewards: profile.rewards
        };

        this.currentDeck = profile.current_deck;
        this.ratingHistory = history;
      } catch (error) {
        console.error('Ошибка загрузки профиля:', error);
      }
    },
    formatDate(date) {
      return new Date(date).toLocaleDateString('ru-RU', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    }
  }
}
</script>

<style scoped>
.rewards-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
  text-align: center;
}
.reward-item {
  padding: 0.5rem;
  border-radius: 8px;
  background: #f8f9fe;
}
</style>