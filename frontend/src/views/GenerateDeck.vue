<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64 max-w-md">
      <h1 class="text-2xl font-bold mb-4">Сгенерировать колоду</h1>

      <form @submit.prevent="generateDeck">
        <label class="block mb-2 font-medium">Выберите стратегию:</label>
        <select v-model="strategy" class="w-full border p-2 rounded mb-4">
          <option value="balanced">Сбалансированная</option>
          <option value="offensive">Атакующая</option>
          <option value="defensive">Оборонительная</option>
        </select>

        <label class="block mb-2 font-medium">Описание (необязательно):</label>
        <input v-model="description" type="text" class="w-full border p-2 rounded mb-4" />

        <button
            type="submit"
            class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Сгенерировать
        </button>
      </form>
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
      strategy: "balanced",
      description: "",
    };
  },
  methods: {
    generateDeck() {
      axios
          .post("/api/decks/generate", {
            strategy: this.strategy,
            description: this.description,
          })
          .then(() => {
            this.$router.push("/decks");
          });
    },
  },
};
</script>
