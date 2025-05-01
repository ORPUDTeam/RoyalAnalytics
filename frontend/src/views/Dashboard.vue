<template>
  <div class="py-4 px-6">
    <div class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6 mb-6">
      <CardStat title="Кубки" :value="userCache.trophies" icon="trophy" />
      <CardStat title="Наград получено" :value="userCache.rewards.length" icon="gift" />
      <CardStat title="Колод создано" :value="deckStats.totalDecks" icon="layers" />
      <CardStat title="Турниров пройдено" :value="tournamentStats.total" icon="flag" />
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
      <Card>
        <template #header>
          <h2 class="text-lg font-semibold">История рейтинга</h2>
        </template>
        <LineChart :data="ratingHistory" />
      </Card>

      <Card>
        <template #header>
          <h2 class="text-lg font-semibold">Последние турниры</h2>
        </template>
        <ul>
          <li v-for="t in tournamentStats.recent" :key="t.id" class="py-2 border-b">
            {{ t.name }} — {{ formatDate(t.date) }}
          </li>
        </ul>
      </Card>
    </div>
  </div>
</template>

<script>
import Card from "@/views/components/Card.vue";
import CardStat from "@/components/CardStat.vue";
import LineChart from "@/components/LineChart.vue";
import axios from "axios";

export default {
  name: "Dashboard",
  components: { Card, CardStat, LineChart },
  data() {
    return {
      userCache: {
        trophies: 0,
        rewards: [],
      },
      deckStats: {
        totalDecks: 0,
      },
      tournamentStats: {
        total: 0,
        recent: [],
      },
      ratingHistory: [],
    };
  },
  mounted() {
    this.fetchDashboardData();
  },
  methods: {
    async fetchDashboardData() {
      try {
        const userRes = await axios.get("/api/user/cache");
        this.userCache = userRes.data;

        const decksRes = await axios.get("/api/user/decks/stats");
        this.deckStats = decksRes.data;

        const tournamentsRes = await axios.get("/api/user/tournaments/stats");
        this.tournamentStats = tournamentsRes.data;

        const ratingRes = await axios.get("/api/user/rating-history");
        this.ratingHistory = ratingRes.data;
      } catch (error) {
        console.error("Ошибка загрузки данных для дашборда:", error);
      }
    },
    formatDate(dateStr) {
      const d = new Date(dateStr);
      return d.toLocaleDateString();
    },
  },
};
</script>

<style scoped>
</style>