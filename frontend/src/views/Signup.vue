<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-900 to-blue-700">
    <div class="w-full max-w-md p-8 bg-white rounded-2xl shadow-xl">
      <h2 class="text-2xl font-bold mb-6 text-center">Регистрация</h2>
      <form @submit.prevent="register">
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Логин</label>
          <input v-model="form.username" type="text" required class="mt-1 w-full rounded border px-3 py-2 shadow" />
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Email</label>
          <input v-model="form.email" type="email" required class="mt-1 w-full rounded border px-3 py-2 shadow" />
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Пароль</label>
          <input v-model="form.password" type="password" required class="mt-1 w-full rounded border px-3 py-2 shadow" />
        </div>
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Повторите пароль</label>
          <input v-model="form.confirmPassword" type="password" required class="mt-1 w-full rounded border px-3 py-2 shadow" />
        </div>
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700">Player Tag</label>
          <input v-model="form.playerTag" type="text" required class="mt-1 w-full rounded border px-3 py-2 shadow" />
        </div>
        <div v-if="error" class="text-red-600 mb-4 text-sm text-center">
          {{ error }}
        </div>
        <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">Зарегистрироваться</button>
      </form>
      <p class="text-sm text-center mt-4">
        Уже есть аккаунт? <router-link to="/login" class="text-blue-600 hover:underline">Войти</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Signup",
  data() {
    return {
      form: {
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        playerTag: "",
      },
      error: "",
    };
  },
  methods: {
    async register() {
      if (this.form.password !== this.form.confirmPassword) {
        this.error = "Пароли не совпадают.";
        return;
      }
      try {
        const response = await axios.post("/api/register", {
          username: this.form.username,
          email: this.form.email,
          password: this.form.password,
          player_tag: this.form.playerTag,
        });
        if (response.status === 201 || response.status === 200) {
          this.$router.push("/login");
        }
      } catch (err) {
        this.error = err.response?.data?.detail || "Ошибка регистрации.";
      }
    },
  },
};
</script>

<style scoped>
input {
  border: 1px solid #d1d5db;
}
</style>