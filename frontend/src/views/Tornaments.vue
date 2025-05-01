<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64">
      <h1 class="text-2xl font-bold mb-4">Список турниров</h1>

      <div v-if="loading" class="text-gray-600">Загрузка...</div>
      <div v-else-if="tournaments.length === 0" class="text-gray-500">Турниров пока нет</div>

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

          <button
              @click="register(tournament.id)"
              class="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
              :disabled="registeredIds.includes(tournament.id)"
          >
            {{ registeredIds.includes(tournament.id) ? "Уже зарегистрирован" : "Зарегистрироваться" }}
          </button>
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
      registeredIds: [],
      loading: true,
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios.get("/api/tournaments").then((res) => {
        this.tournaments = res.data.tournaments;
        this.registeredIds = res.data.registered_ids;
        this.loading = false;
      });
    },
    formatTime(timestamp) {
      return new Date(timestamp).toLocaleString("ru-RU");
    },
    register(tournamentId) {
      axios.post(`/api/tournaments/${tournamentId}/register`).then(() => {
        this.registeredIds.push(tournamentId);
      });
    },
  },
};
</script>
