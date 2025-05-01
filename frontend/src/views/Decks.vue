<template>
  <div>
    <Navbar />
    <Sidebar />
    <div class="p-6 ml-64">
      <h1 class="text-2xl font-bold mb-4">Мои колоды</h1>

      <section>
        <h2 class="text-xl font-semibold mb-2">Пользовательские</h2>
        <div class="grid grid-cols-2 gap-4 mb-6">
          <div v-for="deck in userDecks" :key="deck.id" class="p-4 bg-white shadow rounded">
            <h3 class="font-bold mb-2">{{ deck.name }}</h3>
            <div class="grid grid-cols-4 gap-2">
              <router-link
                  v-for="card in deck.cards"
                  :key="card.id"
                  :to="`/cards/${card.id}`"
                  class="p-2 bg-gray-100 rounded text-center text-sm hover:bg-gray-200"
              >
                {{ card.name }}
              </router-link>
            </div>
            <div class="mt-2 flex gap-2">
              <router-link :to="`/decks/${deck.id}/edit`" class="text-blue-500">Редактировать</router-link>
            </div>
          </div>
        </div>
      </section>

      <section>
        <h2 class="text-xl font-semibold mb-2">Сгенерированные</h2>
        <div class="grid grid-cols-2 gap-4">
          <div v-for="deck in generatedDecks" :key="deck.id" class="p-4 bg-white shadow rounded">
            <p class="italic text-gray-600 mb-2">{{ deck.description }}</p>
            <div class="grid grid-cols-4 gap-2">
              <router-link
                  v-for="card in deck.cards"
                  :key="card.id"
                  :to="`/cards/${card.id}`"
                  class="p-2 bg-gray-100 rounded text-center text-sm hover:bg-gray-200"
              >
                {{ card.name }}
              </router-link>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Navbar from "@/views/components/Sidebar.vue";
import Sidebar from "@/views/components/Navbar.vue";

export default {
  components: {Sidebar, Navbar},
  data() {
    return {
      userDecks: [],
      generatedDecks: [],
    };
  },
  mounted() {
    axios.get("/api/decks/user").then((res) => {
      this.userDecks = res.data;
    });
    axios.get("/api/decks/generated").then((res) => {
      this.generatedDecks = res.data;
    });
  },
};
</script>
