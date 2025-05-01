<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64">
      <h1 class="text-2xl font-bold mb-4">{{ card.name }}</h1>

      <div v-if="loading" class="text-gray-500">Загрузка...</div>
      <div v-else class="p-4 bg-white shadow rounded">
        <p><strong>Эликсир:</strong> {{ card.elixir }}</p>
        <p><strong>Редкость:</strong> {{ card.rarity }}</p>
        <p><strong>Арена:</strong> {{ card.arena }}</p>

        <div class="mt-4">
          <h2 class="font-semibold mb-2">Дополнительные данные:</h2>
          <pre class="bg-gray-100 p-2 rounded text-sm overflow-auto">{{ card.data }}</pre>
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
      card: {},
      loading: true,
    };
  },
  mounted() {
    const id = this.$route.params.card_id;
    axios.get(`/api/cards/${id}`).then((res) => {
      this.card = res.data;
      this.loading = false;
    });
  },
};
</script>
