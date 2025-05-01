<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="main-content p-6">
      <h1 class="text-3xl font-bold mb-4">Мой профиль</h1>

      <div v-if="profile">
        <p><strong>Имя на сайте:</strong> {{ profile.username }}</p>
        <p><strong>Дата регистрации:</strong> {{ formattedDate(profile.registered_at) }}</p>

        <h2 class="text-xl font-semibold mt-6 mb-2">История изменения рейтинга</h2>
        <ul class="list-disc ml-5">
          <li v-for="change in ratingChanges" :key="change.id">
            {{ formatDate(change.changed_at) }} — {{ change.rating }} трофеев
          </li>
        </ul>
      </div>
      <div v-else>
        <p>Загрузка профиля...</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Sidebar from "@/views/components/Sidebar.vue";
import Navbar from "@/views/components/Navbar.vue";

export default {
  name: "Profile",
  components: { Sidebar, Navbar },
  data() {
    return {
      profile: null,
      ratingChanges: [],
    };
  },
  methods: {
    async fetchProfile() {
      try {
        const profileRes = await axios.get("/api/profile");
        const ratingRes = await axios.get("/api/profile/rating-changes");
        this.profile = profileRes.data;
        this.ratingChanges = ratingRes.data;
      } catch (err) {
        console.error("Ошибка при загрузке профиля:", err);
      }
    },
    formattedDate(date) {
      return new Date(date).toLocaleDateString("ru-RU");
    },
    formatDate(date) {
      return new Date(date).toLocaleString("ru-RU");
    },
  },
  mounted() {
    this.fetchProfile();
  },
};
</script>

<style scoped>
.main-content {
  margin-left: 250px; /* под размер сайдбара */
}
</style>