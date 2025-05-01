<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64">
      <h1 class="text-2xl font-bold mb-4">Мои турниры</h1>

      <div v-if="loading" class="text-gray-600">Загрузка...</div>
      <div v-else-if="tournaments.length === 0" class="text-gray-500">Вы не зарегистрированы ни на один турнир</div>

      <div class="space-y-4">
        <div
            v-for="tournament in tournaments"
            :key="tournament.id"
            class="p-4 border rounded bg-white shadow"
        >
          <h2 class="text-xl font-semibold">{{ tournament.name }}</h2>
          <p class="text-sm text-gray-600">Начало: {{ formatTime(tournament.start_time) }}</p>

          <div class="text-sm mt-2 text-gray-700">
            {{ tournament.info.description || "Описание отсутствует" }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";
import Navbar from "@/views/components/Navbar.vue";
import Sidebar from "@/views/components/Sidebar.vue";

export default {
  components: {Sidebar, Navbar},
  data() {
    return {
      tournaments: [],
      loading: true,
    };
  },
  mounted() {
    axios.get("/api/my-tournaments").then((res) => {
      this.tournaments = res.data;
      this.loading = false;
    });
  },
  methods: {
    formatTime(timestamp) {
      return new Date(timestamp).toLocaleString("ru-RU");
    },
  },
};
</script>
