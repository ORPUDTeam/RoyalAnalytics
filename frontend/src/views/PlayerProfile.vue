<template>
  <div class="main-content">
    <Sidebar />
    <main>
      <Navbar />
      <section class="py-6 px-4">
        <div v-if="loading" class="text-center">Загрузка...</div>
        <div v-else-if="error" class="text-red-600">{{ error }}</div>
        <div v-else>
          <h2 class="text-2xl font-semibold mb-4">{{ player.username || "Неизвестный игрок" }}</h2>
          <p class="text-gray-600 mb-2">Player Tag: {{ playerTag }}</p>
          <p class="mb-2">Дата регистрации: {{ player.registered_at || "N/A" }}</p>
          <p class="mb-2">Кубки: {{ player.trophies }}</p>

          <div class="mb-4">
            <h3 class="text-xl font-semibold mb-2">Колода</h3>
            <div class="grid grid-cols-4 gap-2">
              <div v-for="card in player.deck" :key="card.id" class="bg-gray-100 p-2 rounded shadow">
                <p>{{ card.name }}</p>
                <small>{{ card.elixir }} эликсир</small>
              </div>
            </div>
          </div>

          <div class="mb-4">
            <h3 class="text-xl font-semibold mb-2">Награды</h3>
            <ul>
              <li v-for="(reward, index) in player.rewards" :key="index" class="list-disc ml-6">{{ reward }}</li>
            </ul>
          </div>

          <div>
            <h3 class="text-xl font-semibold mb-2">История рейтинга</h3>
            <LineChart :data="ratingHistory" />
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import axios from "axios";
import Sidebar from "@/views/components/Sidebar.vue";
import Navbar from "@/views/components/Navbar.vue";
import LineChart from "@/views/components/LineChart.vue";

export default {
  components: {
    Sidebar,
    Navbar,
    LineChart,
  },
  data() {
    return {
      player: {},
      playerTag: this.$route.params.player_tag,
      ratingHistory: [],
      loading: true,
      error: null,
    };
  },
  async created() {
    try {
      const response = await axios.get(`/api/players/${this.playerTag}`);
      this.player = response.data.player;
      this.ratingHistory = response.data.rating_history || [];
    } catch (e) {
      try {
        const fallback = await axios.get(`/api/players/${this.playerTag}/external`);
        this.player = fallback.data.player;
        this.ratingHistory = [];
      } catch (err) {
        this.error = "Игрок не найден";
      }
    } finally {
      this.loading = false;
    }
  },
};
</script>
