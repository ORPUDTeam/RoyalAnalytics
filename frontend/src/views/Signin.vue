<template>
  <div class="auth-page">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <card class="mt-5">
            <h2 slot="header" class="text-center">Вход в систему</h2>

            <validation-observer v-slot="{ handleSubmit }">
              <form @submit.prevent="handleSubmit(login)">
                <!-- Логин -->
                <validation-provider name="Логин" rules="required" v-slot="{ errors }">
                  <base-input
                      label="Логин"
                      v-model="form.username"
                      :error="errors[0]"
                      placeholder="Введите ваш логин">
                  </base-input>
                </validation-provider>

                <!-- Пароль -->
                <validation-provider name="Пароль" rules="required" v-slot="{ errors }">
                  <base-input
                      label="Пароль"
                      type="password"
                      v-model="form.password"
                      :error="errors[0]"
                      placeholder="Введите пароль">
                  </base-input>
                </validation-provider>

                <!-- Кнопка отправки -->
                <div class="text-center mt-4">
                  <base-button type="primary" native-type="submit" :loading="loading">
                    Войти
                  </base-button>
                </div>
              </form>
            </validation-observer>

            <div class="text-center mt-3">
              <router-link to="/registration">Нет аккаунта? Зарегистрироваться</router-link>
            </div>
          </card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    async login() {
      try {
        this.loading = true;
        const response = await this.$axios.post('/login', {
          name: this.form.username,
          password: this.form.password
        });

        if (response.status === 302) {
          this.$router.push('/profile');
          this.$notify({
            type: 'success',
            message: 'Добро пожаловать!'
          });
        }
      } catch (error) {
        let message = 'Ошибка входа';
        if (error.response && error.response.status === 401) {
          message = 'Неверные учетные данные';
        }
        this.$notify({
          type: 'danger',
          message: message
        });
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>