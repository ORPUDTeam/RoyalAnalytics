<template>
  <div class="signin-page">
    <Sidebar />
    <div class="main-content">
      <Navbar />
      <div class="container mx-auto mt-10 max-w-md">
        <h1 class="text-2xl font-semibold mb-4 text-center">Вход</h1>
        <form @submit.prevent="handleLogin" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
          <div class="mb-4">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="username">
              Логин
            </label>
            <input
                v-model="form.username"
                id="username"
                type="text"
                placeholder="Введите логин"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700"
                required
            />
          </div>

          <div class="mb-6">
            <label class="block text-gray-700 text-sm font-bold mb-2" for="password">
              Пароль
            </label>
            <input
                v-model="form.password"
                id="password"
                type="password"
                placeholder="Введите пароль"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700"
                required
            />
          </div>

          <div class="flex items-center justify-between">
            <button
                class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                type="submit"
            >
              Войти
            </button>
          </div>

          <p v-if="errorMessage" class="text-red-500 text-xs italic mt-4">{{ errorMessage }}</p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Sidebar from "@/views/components/Sidebar.vue";
import Navbar from "@/views/components/Navbar.vue";

export default {
  name: "Signin",
  components: {
    Sidebar,
    Navbar,
  },
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      errorMessage: "",
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await axios.post("/api/login", this.form);
        // Можно сохранить токен, если используется JWT:
        // localStorage.setItem("token", response.data.token);

        this.$router.push("/profile");
      } catch (error) {
        this.errorMessage =
            error.response?.data?.message || "Ошибка входа. Проверьте данные.";
      }
    },
  },
};
</script>

<style scoped>
.container {
  background-color: #f9fafb;
}
</style>